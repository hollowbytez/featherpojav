/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.file.GeoModelLoader
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.exception.GeoModelException
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.Converter
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.FormatVersion
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.RawGeoModel
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.tree.RawGeometryTree
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.GeoBuilder
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoModel
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.file;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.exception.GeoModelException;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.Converter;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.FormatVersion;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.RawGeoModel;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.tree.RawGeometryTree;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.GeoBuilder;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.GeoModel;
import com.cosmeticsmod.morecosmetics.MoreCosmetics;

public class GeoModelLoader {
    public GeoModel loadModel(String geoBuilder, String json) {
        try {
            RawGeoModel rawModel = Converter.fromJsonString((String)json);
            if (rawModel.getFormatVersion() != FormatVersion.VERSION_1_12_0 && rawModel.getFormatVersion() != FormatVersion.VERSION_1_14_0) {
                throw new GeoModelException(geoBuilder, "Wrong geometry json version, expected 1.12.0 or 1.14.0");
            }
            RawGeometryTree rawGeometryTree = RawGeometryTree.parseHierarchy((RawGeoModel)rawModel, (String)"");
            return GeoBuilder.getGeoBuilder((String)geoBuilder).constructGeoModel(rawGeometryTree);
        }
        catch (Exception e) {
            MoreCosmetics.LOGGER.error(String.format("Error parsing %S", geoBuilder), (Throwable)e);
            throw new RuntimeException(e);
        }
    }
}

