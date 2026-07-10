/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.ModelProperties
 *  com.google.gson.annotations.SerializedName
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo;

import com.google.gson.annotations.SerializedName;

public class ModelProperties {
    private Boolean animationArmsDown;
    private Boolean animationArmsOutFront;
    private Boolean animationDontShowArmor;
    private Boolean animationInvertedCrouch;
    private Boolean animationNoHeadBob;
    private Boolean animationSingleArmAnimation;
    private Boolean animationSingleLegAnimation;
    private Boolean animationStationaryLegs;
    private Boolean animationStatueOfLibertyArms;
    private Boolean animationUpsideDown;
    private String identifier;
    @SerializedName(value="preserve_model_pose")
    private Boolean preserveModelPose;
    @SerializedName(value="texture_height")
    private Double textureHeight;
    @SerializedName(value="texture_width")
    private Double textureWidth;
    @SerializedName(value="visible_bounds_height")
    private Double visibleBoundsHeight;
    @SerializedName(value="visible_bounds_offset")
    private double[] visibleBoundsOffset;
    @SerializedName(value="visible_bounds_width")
    private Double visibleBoundsWidth;

    public Boolean getAnimationArmsDown() {
        return this.animationArmsDown;
    }

    public void setAnimationArmsDown(Boolean value) {
        this.animationArmsDown = value;
    }

    public Boolean getAnimationArmsOutFront() {
        return this.animationArmsOutFront;
    }

    public void setAnimationArmsOutFront(Boolean value) {
        this.animationArmsOutFront = value;
    }

    public Boolean getAnimationDontShowArmor() {
        return this.animationDontShowArmor;
    }

    public void setAnimationDontShowArmor(Boolean value) {
        this.animationDontShowArmor = value;
    }

    public Boolean getAnimationInvertedCrouch() {
        return this.animationInvertedCrouch;
    }

    public void setAnimationInvertedCrouch(Boolean value) {
        this.animationInvertedCrouch = value;
    }

    public Boolean getAnimationNoHeadBob() {
        return this.animationNoHeadBob;
    }

    public void setAnimationNoHeadBob(Boolean value) {
        this.animationNoHeadBob = value;
    }

    public Boolean getAnimationSingleArmAnimation() {
        return this.animationSingleArmAnimation;
    }

    public void setAnimationSingleArmAnimation(Boolean value) {
        this.animationSingleArmAnimation = value;
    }

    public Boolean getAnimationSingleLegAnimation() {
        return this.animationSingleLegAnimation;
    }

    public void setAnimationSingleLegAnimation(Boolean value) {
        this.animationSingleLegAnimation = value;
    }

    public Boolean getAnimationStationaryLegs() {
        return this.animationStationaryLegs;
    }

    public void setAnimationStationaryLegs(Boolean value) {
        this.animationStationaryLegs = value;
    }

    public Boolean getAnimationStatueOfLibertyArms() {
        return this.animationStatueOfLibertyArms;
    }

    public void setAnimationStatueOfLibertyArms(Boolean value) {
        this.animationStatueOfLibertyArms = value;
    }

    public Boolean getAnimationUpsideDown() {
        return this.animationUpsideDown;
    }

    public void setAnimationUpsideDown(Boolean value) {
        this.animationUpsideDown = value;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String value) {
        this.identifier = value;
    }

    public Boolean getPreserveModelPose() {
        return this.preserveModelPose;
    }

    public void setPreserveModelPose(Boolean value) {
        this.preserveModelPose = value;
    }

    public Double getTextureHeight() {
        return this.textureHeight;
    }

    public void setTextureHeight(Double value) {
        this.textureHeight = value;
    }

    public Double getTextureWidth() {
        return this.textureWidth;
    }

    public void setTextureWidth(Double value) {
        this.textureWidth = value;
    }

    public Double getVisibleBoundsHeight() {
        return this.visibleBoundsHeight;
    }

    public void setVisibleBoundsHeight(Double value) {
        this.visibleBoundsHeight = value;
    }

    public double[] getVisibleBoundsOffset() {
        return this.visibleBoundsOffset;
    }

    public void setVisibleBoundsOffset(double[] value) {
        this.visibleBoundsOffset = value;
    }

    public Double getVisibleBoundsWidth() {
        return this.visibleBoundsWidth;
    }

    public void setVisibleBoundsWidth(Double value) {
        this.visibleBoundsWidth = value;
    }
}

