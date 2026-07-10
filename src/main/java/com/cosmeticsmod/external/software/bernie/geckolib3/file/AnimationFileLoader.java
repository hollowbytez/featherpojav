/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.molang.MolangParser
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.builder.Animation
 *  com.cosmeticsmod.external.software.bernie.geckolib3.file.AnimationFile
 *  com.cosmeticsmod.external.software.bernie.geckolib3.file.AnimationFileLoader
 *  com.cosmeticsmod.external.software.bernie.geckolib3.util.json.JsonAnimationUtils
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.file;

import com.cosmeticsmod.external.com.eliotlash.molang.MolangParser;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.builder.Animation;
import com.cosmeticsmod.external.software.bernie.geckolib3.file.AnimationFile;
import com.cosmeticsmod.external.software.bernie.geckolib3.util.json.JsonAnimationUtils;
import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.Map;
import java.util.Set;

public class AnimationFileLoader {
    public AnimationFile loadAllAnimations(MolangParser parser, JsonObject jsonRepresentation) {
        AnimationFile animationFile = new AnimationFile();
        Set entrySet = JsonAnimationUtils.getAnimations((JsonObject)jsonRepresentation);
        for (Object entryObj : entrySet) {
            Map.Entry entry = (Map.Entry)entryObj;
            String animationName = (String)entry.getKey();
            try {
                Animation animation = JsonAnimationUtils.deserializeJsonToAnimation((Map.Entry)JsonAnimationUtils.getAnimation((JsonObject)jsonRepresentation, (String)animationName), (MolangParser)parser);
                animationFile.putAnimation(animationName, animation);
            }
            catch (JsonParseException e) {
                MoreCosmetics.LOGGER.error("Could not load animation: {}", new Object[]{animationName, e});
                throw new RuntimeException(e);
            }
        }
        return animationFile;
    }
}

