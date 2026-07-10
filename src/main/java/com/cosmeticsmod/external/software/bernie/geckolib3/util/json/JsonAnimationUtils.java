/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 *  com.cosmeticsmod.external.com.eliotlash.molang.MolangParser
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.builder.Animation
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.BoneAnimation
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.EventKeyFrame
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.ParticleEventKeyFrame
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.VectorKeyFrameList
 *  com.cosmeticsmod.external.software.bernie.geckolib3.util.AnimationUtils
 *  com.cosmeticsmod.external.software.bernie.geckolib3.util.json.JsonAnimationUtils
 *  com.cosmeticsmod.external.software.bernie.geckolib3.util.json.JsonKeyFrameUtils
 *  com.google.common.collect.ImmutableSet
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonPrimitive
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.util.json;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;
import com.cosmeticsmod.external.com.eliotlash.molang.MolangParser;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.builder.Animation;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.BoneAnimation;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.EventKeyFrame;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.ParticleEventKeyFrame;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.VectorKeyFrameList;
import com.cosmeticsmod.external.software.bernie.geckolib3.util.AnimationUtils;
import com.cosmeticsmod.external.software.bernie.geckolib3.util.json.JsonKeyFrameUtils;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * Exception performing whole class analysis ignored.
 */
public class JsonAnimationUtils {
    public static Set<Map.Entry<String, JsonElement>> getAnimations(JsonObject json) {
        return JsonAnimationUtils.getObjectListAsArray((JsonObject)json.getAsJsonObject("animations"));
    }

    public static List<Map.Entry<String, JsonElement>> getBones(JsonObject json) {
        JsonObject bones = json.getAsJsonObject("bones");
        return bones == null ? new ArrayList<Map.Entry<String, JsonElement>>() : new ArrayList(JsonAnimationUtils.getObjectListAsArray((JsonObject)bones));
    }

    public static Set<Map.Entry<String, JsonElement>> getRotationKeyFrames(JsonObject json) {
        JsonElement rotationObject = json.get("rotation");
        if (rotationObject.isJsonArray()) {
            return ImmutableSet.<Map.Entry<String, JsonElement>>of(new AbstractMap.SimpleEntry<String, JsonElement>("0", rotationObject.getAsJsonArray()));
        }
        if (rotationObject.isJsonPrimitive()) {
            JsonPrimitive primitive = rotationObject.getAsJsonPrimitive();
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(Arrays.asList(primitive, primitive, primitive));
            return ImmutableSet.of(new AbstractMap.SimpleEntry<String, JsonElement>("0", jsonElement));
        }
        return JsonAnimationUtils.getObjectListAsArray((JsonObject)rotationObject.getAsJsonObject());
    }

    public static Set<Map.Entry<String, JsonElement>> getPositionKeyFrames(JsonObject json) {
        JsonElement positionObject = json.get("position");
        if (positionObject.isJsonArray()) {
            return ImmutableSet.<Map.Entry<String, JsonElement>>of(new AbstractMap.SimpleEntry<String, JsonElement>("0", positionObject.getAsJsonArray()));
        }
        if (positionObject.isJsonPrimitive()) {
            JsonPrimitive primitive = positionObject.getAsJsonPrimitive();
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(Arrays.asList(primitive, primitive, primitive));
            return ImmutableSet.of(new AbstractMap.SimpleEntry<String, JsonElement>("0", jsonElement));
        }
        return JsonAnimationUtils.getObjectListAsArray((JsonObject)positionObject.getAsJsonObject());
    }

    public static Set<Map.Entry<String, JsonElement>> getScaleKeyFrames(JsonObject json) {
        JsonElement scaleObject = json.get("scale");
        if (scaleObject.isJsonArray()) {
            return ImmutableSet.<Map.Entry<String, JsonElement>>of(new AbstractMap.SimpleEntry<String, JsonElement>("0", scaleObject.getAsJsonArray()));
        }
        if (scaleObject.isJsonPrimitive()) {
            JsonPrimitive primitive = scaleObject.getAsJsonPrimitive();
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(Arrays.asList(primitive, primitive, primitive));
            return ImmutableSet.of(new AbstractMap.SimpleEntry<String, JsonElement>("0", jsonElement));
        }
        return JsonAnimationUtils.getObjectListAsArray((JsonObject)scaleObject.getAsJsonObject());
    }

    public static ArrayList<Map.Entry<String, JsonElement>> getSoundEffectFrames(JsonObject json) {
        JsonObject sound_effects = json.getAsJsonObject("sound_effects");
        return sound_effects == null ? new ArrayList<Map.Entry<String, JsonElement>>() : new ArrayList(JsonAnimationUtils.getObjectListAsArray((JsonObject)sound_effects));
    }

    public static ArrayList<Map.Entry<String, JsonElement>> getParticleEffectFrames(JsonObject json) {
        JsonObject particle_effects = json.getAsJsonObject("particle_effects");
        return particle_effects == null ? new ArrayList<Map.Entry<String, JsonElement>>() : new ArrayList(JsonAnimationUtils.getObjectListAsArray((JsonObject)particle_effects));
    }

    public static ArrayList<Map.Entry<String, JsonElement>> getCustomInstructionKeyFrames(JsonObject json) {
        JsonObject custom_instructions = json.getAsJsonObject("timeline");
        return custom_instructions == null ? new ArrayList<Map.Entry<String, JsonElement>>() : new ArrayList(JsonAnimationUtils.getObjectListAsArray((JsonObject)custom_instructions));
    }

    private static JsonElement getObjectByKey(Set<Map.Entry<String, JsonElement>> json, String key) throws JsonParseException {
        return (JsonElement)json.stream().filter(x -> ((String)x.getKey()).equals(key)).findFirst().orElseThrow(() -> new JsonParseException("Could not find key: " + key)).getValue();
    }

    public static Map.Entry<String, JsonElement> getAnimation(JsonObject animationFile, String animationName) throws JsonParseException {
        return new AbstractMap.SimpleEntry<String, JsonElement>(animationName, JsonAnimationUtils.getObjectByKey((Set)JsonAnimationUtils.getAnimations((JsonObject)animationFile), (String)animationName));
    }

    public static Set<Map.Entry<String, JsonElement>> getObjectListAsArray(JsonObject json) {
        return json.entrySet();
    }

    public static Animation deserializeJsonToAnimation(Map.Entry<String, JsonElement> element, MolangParser parser) throws ClassCastException, IllegalStateException {
        ArrayList customInstructionKeyFrames;
        ArrayList particleKeyFrames;
        Animation animation = new Animation();
        JsonObject animationJsonObject = element.getValue().getAsJsonObject();
        animation.animationName = element.getKey();
        JsonElement animation_length = animationJsonObject.get("animation_length");
        animation.animationLength = animation_length == null ? null : Double.valueOf(AnimationUtils.convertSecondsToTicks((double)animation_length.getAsDouble()));
        animation.boneAnimations = new ArrayList();
        JsonElement loop = animationJsonObject.get("loop");
        animation.loop = loop != null && loop.getAsBoolean();
        ArrayList soundEffectFrames = JsonAnimationUtils.getSoundEffectFrames((JsonObject)animationJsonObject);
        if (soundEffectFrames != null) {
            for (Object keyFrame : soundEffectFrames) {
                animation.soundKeyFrames.add(new EventKeyFrame(Double.valueOf(Double.parseDouble((String)((Map.Entry)keyFrame).getKey()) * 20.0), (Object)((JsonElement)((Map.Entry)keyFrame).getValue()).getAsJsonObject().get("effect").getAsString()));
            }
        }
        if ((particleKeyFrames = JsonAnimationUtils.getParticleEffectFrames((JsonObject)animationJsonObject)) != null) {
            for (Object keyFrame : particleKeyFrames) {
                JsonObject object = ((JsonElement)((Map.Entry)keyFrame).getValue()).getAsJsonObject();
                JsonElement effect = object.get("effect");
                JsonElement locator = object.get("locator");
                JsonElement pre_effect_script = object.get("pre_effect_script");
                animation.particleKeyFrames.add(new ParticleEventKeyFrame(Double.valueOf(Double.parseDouble((String)((Map.Entry)keyFrame).getKey()) * 20.0), effect == null ? "" : effect.getAsString(), locator == null ? "" : locator.getAsString(), pre_effect_script == null ? "" : pre_effect_script.getAsString()));
            }
        }
        if ((customInstructionKeyFrames = JsonAnimationUtils.getCustomInstructionKeyFrames((JsonObject)animationJsonObject)) != null) {
            for (Object keyFrame : customInstructionKeyFrames) {
                animation.customInstructionKeyframes.add(new EventKeyFrame(Double.valueOf(Double.parseDouble((String)((Map.Entry)keyFrame).getKey()) * 20.0), (Object)JsonAnimationUtils.convertJsonArrayToList((JsonArray)((JsonElement)((Map.Entry)keyFrame).getValue()).getAsJsonArray())));
            }
        }
        List bones = JsonAnimationUtils.getBones((JsonObject)animationJsonObject);
        for (Object bone : bones) {
            BoneAnimation boneAnimation = new BoneAnimation();
            boneAnimation.boneName = (String)((java.util.Map.Entry)bone).getKey();
            JsonObject boneJsonObj = ((JsonElement)((java.util.Map.Entry)bone).getValue()).getAsJsonObject();
            try {
                Set scaleKeyFramesJson = JsonAnimationUtils.getScaleKeyFrames((JsonObject)boneJsonObj);
                boneAnimation.scaleKeyFrames = JsonKeyFrameUtils.convertJsonToKeyFrames(new ArrayList(scaleKeyFramesJson), (MolangParser)parser);
            }
            catch (Exception e) {
                boneAnimation.scaleKeyFrames = new VectorKeyFrameList();
            }
            try {
                Set positionKeyFramesJson = JsonAnimationUtils.getPositionKeyFrames((JsonObject)boneJsonObj);
                boneAnimation.positionKeyFrames = JsonKeyFrameUtils.convertJsonToKeyFrames(new ArrayList(positionKeyFramesJson), (MolangParser)parser);
            }
            catch (Exception e) {
                boneAnimation.positionKeyFrames = new VectorKeyFrameList();
            }
            try {
                Set rotationKeyFramesJson = JsonAnimationUtils.getRotationKeyFrames((JsonObject)boneJsonObj);
                boneAnimation.rotationKeyFrames = JsonKeyFrameUtils.convertJsonToRotationKeyFrames(new ArrayList(rotationKeyFramesJson), (MolangParser)parser);
            }
            catch (Exception e) {
                boneAnimation.rotationKeyFrames = new VectorKeyFrameList();
            }
            animation.boneAnimations.add(boneAnimation);
        }
        if (animation.animationLength == null) {
            animation.animationLength = JsonAnimationUtils.calculateLength((List)animation.boneAnimations);
        }
        return animation;
    }

    private static double calculateLength(List<BoneAnimation> boneAnimations) {
        double longestLength = 0.0;
        for (BoneAnimation animation : boneAnimations) {
            double xKeyframeTime = animation.rotationKeyFrames.getLastKeyframeTime();
            double yKeyframeTime = animation.positionKeyFrames.getLastKeyframeTime();
            double zKeyframeTime = animation.scaleKeyFrames.getLastKeyframeTime();
            longestLength = JsonAnimationUtils.maxAll((double[])new double[]{longestLength, xKeyframeTime, yKeyframeTime, zKeyframeTime});
        }
        return longestLength == 0.0 ? Double.MAX_VALUE : longestLength;
    }

    static List<IValue> convertJsonArrayToList(JsonArray array) {
        return (List)new Gson().fromJson((JsonElement)array, ArrayList.class);
    }

    public static double maxAll(double ... values) {
        double max = 0.0;
        for (double value : values) {
            max = Math.max(value, max);
        }
        return max;
    }
}

