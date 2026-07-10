/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.cosmeticsmod.morecosmetics.gui.EditGui
 *  com.cosmeticsmod.morecosmetics.gui.ModelGui
 *  com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler
 *  com.cosmeticsmod.morecosmetics.models.ModelLoader
 *  com.cosmeticsmod.morecosmetics.models.config.ModelData
 *  com.cosmeticsmod.morecosmetics.models.editor.EditRequest
 *  com.cosmeticsmod.morecosmetics.models.model.CosmeticModel
 *  com.cosmeticsmod.morecosmetics.user.UserHandler
 *  com.google.gson.JsonObject
 *  org.apache.commons.io.FileUtils
 */
package com.cosmeticsmod.morecosmetics.models.editor;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.cosmeticsmod.morecosmetics.gui.EditGui;
import com.cosmeticsmod.morecosmetics.gui.ModelGui;
import com.cosmeticsmod.morecosmetics.gui.core.notification.NotificationHandler;
import com.cosmeticsmod.morecosmetics.models.ModelLoader;
import com.cosmeticsmod.morecosmetics.models.config.ModelData;
import com.cosmeticsmod.morecosmetics.models.model.CosmeticModel;
import com.cosmeticsmod.morecosmetics.user.UserHandler;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;

public class EditRequest
implements Runnable {
    public static final int VERSION = 1;
    private final Socket requestSocket;
    private boolean receiving;

    public EditRequest(Socket socket) {
        this.requestSocket = socket;
    }

    @Override
    public void run() {
        MoreCosmetics.debug((String)("[EDITOR] Received edit request from " + this.requestSocket.getInetAddress() + ":" + this.requestSocket.getPort()));
        try (BufferedReader in = new BufferedReader(new InputStreamReader(this.requestSocket.getInputStream(), StandardCharsets.UTF_8));){
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = in.readLine()) != null) {
                if (line.startsWith("data:")) {
                    line = line.substring(5);
                    this.receiving = true;
                }
                if (line.startsWith("debug:")) {
                    MoreCosmetics.log((String)("[DEBUG] " + line));
                }
                if (!this.receiving) continue;
                sb.append(line);
            }
            if (!this.receiving) {
                return;
            }
            String rawContent = sb.toString();
            MoreCosmetics.debug((String)("[EDITOR] Received " + rawContent.length() + " characters"));
            MoreCosmetics.debug((String)("[EDITOR] " + rawContent));
            JsonObject cosObj = MoreCosmetics.PARSER.parse(rawContent).getAsJsonObject();
            String name = cosObj.get("name").getAsString();
            JsonObject info = cosObj.get("info").getAsJsonObject();
            int version = info.get("version").getAsInt();
            if (version != 1) {
                MoreCosmetics.debug((String)("[EDITOR] Received different exported model version: " + version));
            }
            boolean keepData = info.has("keepData") && info.get("keepData").getAsBoolean();
            cosObj.remove("info");
            int modelId = name.hashCode();
            ModelLoader modelLoader = MoreCosmetics.getInstance().getModelLoader();
            CosmeticModel preModel = modelLoader.getModel(Integer.valueOf(modelId));
            File target = new File(MoreCosmetics.ROOT_DIR, "cosmetics/" + name + ".json");
            FileUtils.writeStringToFile((File)target, (String)cosObj.toString(), (String)"UTF-8");
            if (keepData && preModel != null) {
                if (cosObj.get("models").getAsJsonArray().size() == preModel.getSubModels().length) {
                    modelLoader.updateModelFile(preModel);
                } else {
                    MoreCosmetics.debug((String)"[EDITOR] Can't keep data: SubModel count changed!");
                }
            }
            UserHandler userHandler = MoreCosmetics.getInstance().getUserHandler();
            userHandler.getCurrentUser().getCosmetics().remove(modelId);
            userHandler.clearConfig(modelId);
            modelLoader.loadModel(target, true);
            CosmeticModel newModel = modelLoader.getModel(Integer.valueOf(modelId));
            if (newModel != null) {
                ModelData data = userHandler.loadData(newModel, false);
                data.setActive(true);
                userHandler.getCurrentUser().getCosmetics().put(modelId, data);
            }
            ModelGui.refreshGui();
            EditGui.updateModel((CosmeticModel)newModel);
            EditGui.refreshGui();
            NotificationHandler.sendSuccess((String)"ModelEditor", (String)("Update Model: " + name));
        }
        catch (Exception e) {
            MoreCosmetics.log((String)"[EDITOR] Couldn't parse and format received data");
            MoreCosmetics.catchThrowable((Throwable)e);
        }
        try {
            this.requestSocket.close();
        }
        catch (IOException e) {
            MoreCosmetics.catchThrowable((Throwable)e);
        }
    }
}

