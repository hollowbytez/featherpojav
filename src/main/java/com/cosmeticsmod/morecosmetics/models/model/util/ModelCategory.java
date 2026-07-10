/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.models.model.util.ModelCategory
 */
package com.cosmeticsmod.morecosmetics.models.model.util;

/*
 * Exception performing whole class analysis ignored.
 */
public enum ModelCategory {
    LOCAL(0, "Local"),
    HAT(1, "Hat"),
    HEAD(2, "Head"),
    BODY(3, "Body"),
    SHIELD(4, "Shield"),
    WINGS(5, "Wings"),
    PETS(6, "Pets");

    public static final ModelCategory[] VALUES;
    private int id;
    private String title;

    private ModelCategory(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getLocationName() {
        return this.title.toLowerCase();
    }

    public static ModelCategory getById(int id) {
        for (ModelCategory cat : VALUES) {
            if (cat.getId() != id) continue;
            return cat;
        }
        return LOCAL;
    }

    static {
        VALUES = ModelCategory.values();
    }
}

