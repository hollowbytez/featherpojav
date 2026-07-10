/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.file.AnimationFile
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoModel
 *  com.cosmeticsmod.external.software.bernie.geckolib3.model.AnimatedGeoModel
 *  com.cosmeticsmod.morecosmetics.models.animated.AnimatedCosmeticModel
 *  com.cosmeticsmod.morecosmetics.models.animated.CosmeticAnimatable
 *  com.cosmeticsmod.morecosmetics.models.config.ModelConfig
 *  com.cosmeticsmod.morecosmetics.models.model.CosmeticModel
 *  com.cosmeticsmod.morecosmetics.models.model.ItemModel
 *  com.cosmeticsmod.morecosmetics.models.model.PositionModel
 *  com.cosmeticsmod.morecosmetics.models.model.SubModel
 *  com.cosmeticsmod.morecosmetics.models.model.TextureModel
 *  com.cosmeticsmod.morecosmetics.models.model.util.ModelPosition
 */
package com.cosmeticsmod.morecosmetics.models.model;

import com.cosmeticsmod.external.software.bernie.geckolib3.file.AnimationFile;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoModel;
import com.cosmeticsmod.external.software.bernie.geckolib3.model.AnimatedGeoModel;
import com.cosmeticsmod.morecosmetics.models.animated.AnimatedCosmeticModel;
import com.cosmeticsmod.morecosmetics.models.animated.CosmeticAnimatable;
import com.cosmeticsmod.morecosmetics.models.config.ModelConfig;
import com.cosmeticsmod.morecosmetics.models.model.ItemModel;
import com.cosmeticsmod.morecosmetics.models.model.PositionModel;
import com.cosmeticsmod.morecosmetics.models.model.SubModel;
import com.cosmeticsmod.morecosmetics.models.model.TextureModel;
import com.cosmeticsmod.morecosmetics.models.model.util.ModelPosition;
import java.io.File;
import java.util.ArrayList;

public class CosmeticModel
extends PositionModel {
    private final String name;
    private final Integer id;
    private final boolean custom;
    private final String textureName;
    private final SubModel[] subModels;
    private final File file;
    private final int version;
    private int category;
    private ModelConfig[] config;
    private float height;
    private float resizeVal;
    private String author;
    private float previewScale = 1.0f;
    private float previewY;
    private float[] previewRot;
    private float[] adjustment;
    private float[] sideTranform;
    private transient GeoModel model;
    private transient CosmeticAnimatable animatedModel;
    private ArrayList<ItemModel> itemModels;
    private ArrayList<TextureModel> textureModels;

    public CosmeticModel(String name, Integer id, int category, int version, boolean custom, String textureName, GeoModel model, SubModel[] subModels, ModelConfig[] config, File file) {
        super(ModelPosition.BODY);
        this.model = model;
        this.name = name;
        this.id = id;
        this.category = category;
        this.version = version;
        this.custom = custom;
        this.textureName = textureName;
        this.subModels = subModels;
        this.config = config;
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public String getName() {
        return this.name;
    }

    public String getAuthor() {
        return this.author;
    }

    public int getCategory() {
        return this.category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getVersion() {
        return this.version;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getResizeVal() {
        return this.resizeVal;
    }

    public void setResizeVal(float resizeVal) {
        this.resizeVal = resizeVal;
    }

    public void setPreviewScale(float previewScale) {
        this.previewScale = previewScale;
    }

    public void setPreviewY(float previewY) {
        this.previewY = previewY;
    }

    public Integer getId() {
        return this.id;
    }

    public SubModel[] getSubModels() {
        return this.subModels;
    }

    public ModelConfig[] getConfig() {
        return this.config;
    }

    public void setConfig(ModelConfig[] config) {
        this.config = config;
    }

    public boolean hasConfig() {
        return this.config != null && this.config.length > 0;
    }

    public boolean hasTextureModels() {
        return this.textureModels != null;
    }

    public boolean hasItemModels() {
        return this.itemModels != null;
    }

    public ArrayList<ItemModel> getItemModels() {
        if (this.itemModels == null) {
            this.itemModels = new ArrayList();
        }
        return this.itemModels;
    }

    public ArrayList<TextureModel> getTextureModels() {
        if (this.textureModels == null) {
            this.textureModels = new ArrayList();
        }
        return this.textureModels;
    }

    public boolean isCustom() {
        return this.custom;
    }

    public boolean hasIllumination() {
        return true;
    }

    public String getTextureName() {
        return this.textureName;
    }

    public float getPreviewScale() {
        return this.previewScale;
    }

    public float getPreviewY() {
        return this.previewY;
    }

    public float[] getPreviewRot() {
        return this.previewRot;
    }

    public void setPreviewRot(float[] previewRot) {
        this.previewRot = previewRot;
    }

    public float[] getAdjustment() {
        return this.adjustment;
    }

    public void setAdjustment(float[] adjustment) {
        this.adjustment = adjustment;
    }

    public float[] getSideTranform() {
        return this.sideTranform;
    }

    public void setSideTranform(float[] sideTranform) {
        this.sideTranform = sideTranform;
    }

    public GeoModel getModel() {
        return this.model;
    }

    public void setAnimationFile(AnimationFile animationFile) {
        this.animatedModel = new CosmeticAnimatable((AnimatedGeoModel)new AnimatedCosmeticModel(this.model, animationFile));
    }

    public boolean hasAnimatedModel() {
        return this.animatedModel != null;
    }

    public CosmeticAnimatable getAnimatedModel() {
        return this.animatedModel;
    }

    public boolean hasSubModels() {
        return this.subModels != null && this.subModels.length > 0;
    }
}

