/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.Bone
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.Cube
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.LocatorValue
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.PolyMesh
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.TextureMesh
 *  com.google.gson.annotations.SerializedName
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.Cube;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.LocatorValue;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.PolyMesh;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.TextureMesh;
import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class Bone {
    @SerializedName(value="bind_pose_rotation")
    private double[] bindPoseRotation;
    private Cube[] cubes;
    private Boolean debug;
    private Double inflate;
    private Map<String, LocatorValue> locators;
    private Boolean mirror;
    private String name;
    private Boolean neverRender;
    private String parent;
    private double[] pivot = new double[]{0.0, 0.0, 0.0};
    @SerializedName(value="poly_mesh")
    private PolyMesh polyMesh;
    @SerializedName(value="render_group_id")
    private Long renderGroupID;
    private Boolean reset;
    private double[] rotation = new double[]{0.0, 0.0, 0.0};
    @SerializedName(value="texture_meshes")
    private TextureMesh[] textureMeshes;

    public double[] getBindPoseRotation() {
        return this.bindPoseRotation;
    }

    public void setBindPoseRotation(double[] value) {
        this.bindPoseRotation = value;
    }

    public Cube[] getCubes() {
        return this.cubes;
    }

    public void setCubes(Cube[] value) {
        this.cubes = value;
    }

    public Boolean getDebug() {
        return this.debug;
    }

    public void setDebug(Boolean value) {
        this.debug = value;
    }

    public Double getInflate() {
        return this.inflate;
    }

    public void setInflate(Double value) {
        this.inflate = value;
    }

    public Map<String, LocatorValue> getLocators() {
        return this.locators;
    }

    public void setLocators(Map<String, LocatorValue> value) {
        this.locators = value;
    }

    public Boolean getMirror() {
        return this.mirror;
    }

    public void setMirror(Boolean value) {
        this.mirror = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public Boolean getNeverRender() {
        return this.neverRender;
    }

    public void setNeverRender(Boolean value) {
        this.neverRender = value;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String value) {
        this.parent = value;
    }

    public double[] getPivot() {
        return this.pivot;
    }

    public void setPivot(double[] value) {
        this.pivot = value;
    }

    public PolyMesh getPolyMesh() {
        return this.polyMesh;
    }

    public void setPolyMesh(PolyMesh value) {
        this.polyMesh = value;
    }

    public Long getRenderGroupID() {
        return this.renderGroupID;
    }

    public void setRenderGroupID(Long value) {
        this.renderGroupID = value;
    }

    public Boolean getReset() {
        return this.reset;
    }

    public void setReset(Boolean value) {
        this.reset = value;
    }

    public double[] getRotation() {
        return this.rotation;
    }

    public void setRotation(double[] value) {
        this.rotation = value;
    }

    public TextureMesh[] getTextureMeshes() {
        return this.textureMeshes;
    }

    public void setTextureMeshes(TextureMesh[] value) {
        this.textureMeshes = value;
    }
}

