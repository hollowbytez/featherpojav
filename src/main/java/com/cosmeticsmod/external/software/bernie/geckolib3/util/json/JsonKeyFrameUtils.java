/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 *  com.cosmeticsmod.external.com.eliotlash.molang.MolangException
 *  com.cosmeticsmod.external.com.eliotlash.molang.MolangParser
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.ConstantValue
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.easing.EasingType
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.KeyFrame
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.VectorKeyFrameList
 *  com.cosmeticsmod.external.software.bernie.geckolib3.util.AnimationUtils
 *  com.cosmeticsmod.external.software.bernie.geckolib3.util.json.JsonAnimationUtils
 *  com.cosmeticsmod.external.software.bernie.geckolib3.util.json.JsonKeyFrameUtils
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  org.apache.commons.lang3.math.NumberUtils
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.util.json;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;
import com.cosmeticsmod.external.com.eliotlash.molang.MolangException;
import com.cosmeticsmod.external.com.eliotlash.molang.MolangParser;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.ConstantValue;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.easing.EasingType;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.KeyFrame;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.VectorKeyFrameList;
import com.cosmeticsmod.external.software.bernie.geckolib3.util.AnimationUtils;
import com.cosmeticsmod.external.software.bernie.geckolib3.util.json.JsonAnimationUtils;
import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;

/*
 * Exception performing whole class analysis ignored.
 */
public class JsonKeyFrameUtils {
    private static VectorKeyFrameList<KeyFrame<IValue>> convertJson(List<Map.Entry<String, JsonElement>> element, boolean isRotation, MolangParser parser) throws NumberFormatException, MolangException {
        IValue previousXValue = null;
        IValue previousYValue = null;
        IValue previousZValue = null;
        ArrayList<KeyFrame> xKeyFrames = new ArrayList<KeyFrame>();
        ArrayList<KeyFrame> yKeyFrames = new ArrayList<KeyFrame>();
        ArrayList<KeyFrame> zKeyFrames = new ArrayList<KeyFrame>();
        for (int i = 0; i < element.size(); ++i) {
            KeyFrame zKeyFrame;
            KeyFrame yKeyFrame;
            KeyFrame xKeyFrame;
            IValue currentZValue;
            Map.Entry<String, JsonElement> keyframe = element.get(i);
            Map.Entry<String, JsonElement> previousKeyFrame = i == 0 ? null : element.get(i - 1);
            Double previousKeyFrameLocation = previousKeyFrame == null ? 0.0 : Double.parseDouble(previousKeyFrame.getKey());
            Double currentKeyFrameLocation = NumberUtils.isNumber((String)keyframe.getKey()) ? Double.parseDouble(keyframe.getKey()) : 0.0;
            Double animationTimeDifference = currentKeyFrameLocation - previousKeyFrameLocation;
            JsonArray vectorJsonArray = JsonKeyFrameUtils.getKeyFrameVector((JsonElement)keyframe.getValue());
            IValue xValue = JsonKeyFrameUtils.parseExpression((MolangParser)parser, (JsonElement)vectorJsonArray.get(0));
            IValue yValue = JsonKeyFrameUtils.parseExpression((MolangParser)parser, (JsonElement)vectorJsonArray.get(1));
            IValue zValue = JsonKeyFrameUtils.parseExpression((MolangParser)parser, (JsonElement)vectorJsonArray.get(2));
            IValue currentXValue = isRotation && xValue instanceof ConstantValue ? ConstantValue.fromDouble((double)Math.toRadians(-xValue.get())) : xValue;
            IValue currentYValue = isRotation && yValue instanceof ConstantValue ? ConstantValue.fromDouble((double)Math.toRadians(-yValue.get())) : yValue;
            Object object = currentZValue = isRotation && zValue instanceof ConstantValue ? ConstantValue.fromDouble((double)Math.toRadians(zValue.get())) : zValue;
            if (keyframe.getValue().isJsonObject() && JsonKeyFrameUtils.hasEasingType((JsonElement)keyframe.getValue())) {
                EasingType easingType = JsonKeyFrameUtils.getEasingType((JsonElement)keyframe.getValue());
                if (JsonKeyFrameUtils.hasEasingArgs((JsonElement)keyframe.getValue())) {
                    List easingArgs = JsonKeyFrameUtils.getEasingArgs((JsonElement)keyframe.getValue());
                    xKeyFrame = new KeyFrame(Double.valueOf(AnimationUtils.convertSecondsToTicks((double)animationTimeDifference)), (Object)(i == 0 ? currentXValue : previousXValue), (Object)currentXValue, easingType, easingArgs);
                    yKeyFrame = new KeyFrame(Double.valueOf(AnimationUtils.convertSecondsToTicks((double)animationTimeDifference)), (Object)(i == 0 ? currentYValue : previousYValue), (Object)currentYValue, easingType, easingArgs);
                    zKeyFrame = new KeyFrame(Double.valueOf(AnimationUtils.convertSecondsToTicks((double)animationTimeDifference)), (Object)(i == 0 ? currentZValue : previousZValue), (Object)currentZValue, easingType, easingArgs);
                } else {
                    xKeyFrame = new KeyFrame(Double.valueOf(AnimationUtils.convertSecondsToTicks((double)animationTimeDifference)), (Object)(i == 0 ? currentXValue : previousXValue), (Object)currentXValue, easingType);
                    yKeyFrame = new KeyFrame(Double.valueOf(AnimationUtils.convertSecondsToTicks((double)animationTimeDifference)), (Object)(i == 0 ? currentYValue : previousYValue), (Object)currentYValue, easingType);
                    zKeyFrame = new KeyFrame(Double.valueOf(AnimationUtils.convertSecondsToTicks((double)animationTimeDifference)), (Object)(i == 0 ? currentZValue : previousZValue), (Object)currentZValue, easingType);
                }
            } else {
                xKeyFrame = new KeyFrame(Double.valueOf(AnimationUtils.convertSecondsToTicks((double)animationTimeDifference)), (Object)(i == 0 ? currentXValue : previousXValue), (Object)currentXValue);
                yKeyFrame = new KeyFrame(Double.valueOf(AnimationUtils.convertSecondsToTicks((double)animationTimeDifference)), (Object)(i == 0 ? currentYValue : previousYValue), (Object)currentYValue);
                zKeyFrame = new KeyFrame(Double.valueOf(AnimationUtils.convertSecondsToTicks((double)animationTimeDifference)), (Object)(i == 0 ? currentZValue : previousZValue), (Object)currentZValue);
            }
            previousXValue = currentXValue;
            previousYValue = currentYValue;
            previousZValue = currentZValue;
            xKeyFrames.add(xKeyFrame);
            yKeyFrames.add(yKeyFrame);
            zKeyFrames.add(zKeyFrame);
        }
        return new VectorKeyFrameList(xKeyFrames, yKeyFrames, zKeyFrames);
    }

    private static JsonArray getKeyFrameVector(JsonElement element) {
        if (element.isJsonArray()) {
            return element.getAsJsonArray();
        }
        return element.getAsJsonObject().get("vector").getAsJsonArray();
    }

    private static boolean hasEasingType(JsonElement element) {
        return element.getAsJsonObject().has("easing");
    }

    private static boolean hasEasingArgs(JsonElement element) {
        return element.getAsJsonObject().has("easingArgs");
    }

    private static EasingType getEasingType(JsonElement element) {
        String easingString = element.getAsJsonObject().get("easing").getAsString();
        try {
            String uppercaseEasingString = Character.toUpperCase(easingString.charAt(0)) + easingString.substring(1);
            EasingType easing = EasingType.valueOf((String)uppercaseEasingString);
            return easing;
        }
        catch (Exception e) {
            MoreCosmetics.LOGGER.fatal("Unknown easing type: {}", new Object[]{easingString});
            throw new RuntimeException(e);
        }
    }

    private static List<IValue> getEasingArgs(JsonElement element) {
        JsonObject asJsonObject = element.getAsJsonObject();
        JsonElement easingArgs = asJsonObject.get("easingArgs");
        JsonArray asJsonArray = easingArgs.getAsJsonArray();
        return JsonAnimationUtils.convertJsonArrayToList((JsonArray)asJsonArray);
    }

    public static VectorKeyFrameList<KeyFrame<IValue>> convertJsonToKeyFrames(List<Map.Entry<String, JsonElement>> element, MolangParser parser) throws NumberFormatException, MolangException {
        return JsonKeyFrameUtils.convertJson(element, (boolean)false, (MolangParser)parser);
    }

    public static VectorKeyFrameList<KeyFrame<IValue>> convertJsonToRotationKeyFrames(List<Map.Entry<String, JsonElement>> element, MolangParser parser) throws NumberFormatException, MolangException {
        VectorKeyFrameList frameList = JsonKeyFrameUtils.convertJson(element, (boolean)true, (MolangParser)parser);
        return new VectorKeyFrameList(frameList.xKeyFrames, frameList.yKeyFrames, frameList.zKeyFrames);
    }

    public static IValue parseExpression(MolangParser parser, JsonElement element) throws MolangException {
        if (element.getAsJsonPrimitive().isString()) {
            return parser.parseJson(element);
        }
        return ConstantValue.fromDouble((double)element.getAsDouble());
    }
}

