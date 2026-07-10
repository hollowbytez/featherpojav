package com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.OffsetDateTime;

public class TimeDeserializer implements JsonDeserializer<OffsetDateTime> {
    @Override
    public OffsetDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return OffsetDateTime.parse(json.getAsString());
    }
}
