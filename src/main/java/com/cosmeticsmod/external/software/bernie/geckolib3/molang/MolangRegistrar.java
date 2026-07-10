/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.Variable
 *  com.cosmeticsmod.external.com.eliotlash.molang.MolangParser
 *  com.cosmeticsmod.external.software.bernie.geckolib3.molang.MolangRegistrar
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.molang;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.Variable;
import com.cosmeticsmod.external.com.eliotlash.molang.MolangParser;

public class MolangRegistrar {
    public static void registerVars(MolangParser parser) {
        parser.register(new Variable("query.anim_time", 0.0));
        parser.register(new Variable("query.actor_count", 0.0));
        parser.register(new Variable("query.health", 0.0));
        parser.register(new Variable("query.max_health", 0.0));
        parser.register(new Variable("query.distance_from_camera", 0.0));
        parser.register(new Variable("query.yaw_speed", 0.0));
        parser.register(new Variable("query.is_in_water_or_rain", 0.0));
        parser.register(new Variable("query.is_in_water", 0.0));
        parser.register(new Variable("query.is_on_ground", 0.0));
        parser.register(new Variable("query.time_of_day", 0.0));
        parser.register(new Variable("query.is_on_fire", 0.0));
        parser.register(new Variable("query.ground_speed", 0.0));
    }
}

