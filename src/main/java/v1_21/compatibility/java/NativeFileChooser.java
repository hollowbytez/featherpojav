/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  org.lwjgl.PointerBuffer
 *  org.lwjgl.system.MemoryStack
 *  org.lwjgl.util.tinyfd.TinyFileDialogs
 *  v1_21.morecosmetics.compatibility.java.NativeFileChooser
 */
package v1_21.morecosmetics.compatibility.java;

import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import java.io.File;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

@Environment(value=EnvType.CLIENT)
public class NativeFileChooser {
    public static void openNativeFileChooser(String title, File path, Consumer<File> callback, String filterDescription, String ... extensions) {
        MoreCosmetics.EXECUTOR.execute(() -> {
            MemoryStack stack = MemoryStack.stackPush();
            PointerBuffer filters = stack.mallocPointer(extensions.length);
            for (String filter : extensions) {
                filters.put(stack.UTF8((CharSequence)("*." + filter)));
            }
            filters.flip();
            Object folder = path.getAbsolutePath();
            if (!((String)folder).endsWith(File.separator)) {
                folder = (String)folder + File.separator;
            }
            String result = TinyFileDialogs.tinyfd_openFileDialog((CharSequence)title, (CharSequence)folder, (PointerBuffer)filters, (CharSequence)filterDescription, (boolean)false);
            stack.pop();
            if (result != null) {
                callback.accept(new File(result));
            }
        });
    }
}

