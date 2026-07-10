/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.Converter
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.Converter$TimeDeserializer
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.LocatorValue
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.LocatorValue$Deserializer
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.PolysUnion
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.PolysUnion$Deserializer
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.RawGeoModel
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.UvUnion
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.UvUnion$Deserializer
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.Converter;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.LocatorValue;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.PolysUnion;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.RawGeoModel;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.UvUnion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class Converter {
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(UvUnion.class, (Object)new UvUnion.Deserializer()).registerTypeAdapter(LocatorValue.class, (Object)new LocatorValue.Deserializer()).registerTypeAdapter(PolysUnion.class, (Object)new PolysUnion.Deserializer()).registerTypeAdapter(OffsetDateTime.class, (Object)new TimeDeserializer()).create();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder().appendOptional(DateTimeFormatter.ISO_DATE_TIME).appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME).appendOptional(DateTimeFormatter.ISO_INSTANT).appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SX")).appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX")).appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toFormatter().withZone(ZoneOffset.UTC);
    private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder().appendOptional(DateTimeFormatter.ISO_TIME).appendOptional(DateTimeFormatter.ISO_OFFSET_TIME).parseDefaulting(ChronoField.YEAR, 2020L).parseDefaulting(ChronoField.MONTH_OF_YEAR, 1L).parseDefaulting(ChronoField.DAY_OF_MONTH, 1L).toFormatter().withZone(ZoneOffset.UTC);

    public static OffsetDateTime parseDateTimeString(String str) {
        return ZonedDateTime.from(DATE_TIME_FORMATTER.parse(str)).toOffsetDateTime();
    }

    public static OffsetTime parseTimeString(String str) {
        return ZonedDateTime.from(TIME_FORMATTER.parse(str)).toOffsetDateTime().toOffsetTime();
    }

    public static RawGeoModel fromJsonString(String json) throws IOException {
        return (RawGeoModel)GSON.fromJson(json, RawGeoModel.class);
    }
}

