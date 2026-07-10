/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.EnumFacing
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.EnumFacing$1
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.EnumFacing$Axis
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.EnumFacing$AxisDirection
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3I
 *  com.cosmeticsmod.morecosmetics.utils.MathUtils
 *  com.google.common.collect.Maps
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.EnumFacing;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3I;
import com.cosmeticsmod.morecosmetics.utils.MathUtils;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;

/*
 * Exception performing whole class analysis ignored.
 */
public enum EnumFacing {
    DOWN(0, 1, -1, "down", AxisDirection.NEGATIVE, Axis.Y, new Vec3I(0, -1, 0)),
    UP(1, 0, -1, "up", AxisDirection.POSITIVE, Axis.Y, new Vec3I(0, 1, 0)),
    NORTH(2, 3, 2, "north", AxisDirection.NEGATIVE, Axis.Z, new Vec3I(0, 0, -1)),
    SOUTH(3, 2, 0, "south", AxisDirection.POSITIVE, Axis.Z, new Vec3I(0, 0, 1)),
    WEST(4, 5, 1, "west", AxisDirection.NEGATIVE, Axis.X, new Vec3I(-1, 0, 0)),
    EAST(5, 4, 3, "east", AxisDirection.POSITIVE, Axis.X, new Vec3I(1, 0, 0));

    private final int index;
    private final int opposite;
    private final int horizontalIndex;
    private final String name;
    private final Axis axis;
    private final AxisDirection axisDirection;
    private final Vec3I directionVec;
    public static final EnumFacing[] VALUES;
    public static final EnumFacing[] HORIZONTALS;
    private static final Map<String, EnumFacing> NAME_LOOKUP;

    private EnumFacing(int indexIn, int oppositeIn, int horizontalIndexIn, String nameIn, AxisDirection axisDirectionIn, Axis axisIn, Vec3I directionVecIn) {
        this.index = indexIn;
        this.horizontalIndex = horizontalIndexIn;
        this.opposite = oppositeIn;
        this.name = nameIn;
        this.axis = axisIn;
        this.axisDirection = axisDirectionIn;
        this.directionVec = directionVecIn;
    }

    public int getIndex() {
        return this.index;
    }

    public int getHorizontalIndex() {
        return this.horizontalIndex;
    }

    public AxisDirection getAxisDirection() {
        return this.axisDirection;
    }

    public EnumFacing getOpposite() {
        return EnumFacing.getFront((int)this.opposite);
    }

    public EnumFacing rotateAround(Axis axis) {
        switch (axis) {
            case X:
                if (this != WEST && this != EAST) return this.rotateX();
                return this;
            case Y:
                if (this != UP && this != DOWN) return this.rotateY();
                return this;
            case Z:
                if (this != NORTH && this != SOUTH) return this.rotateZ();
                return this;
            default:
                throw new IllegalStateException("Unable to get CW facing for axis " + axis);
        }
    }

    public EnumFacing rotateY() {
        switch (this) {
            case NORTH: return EAST;
            case EAST: return SOUTH;
            case SOUTH: return WEST;
            case WEST: return NORTH;
            default: throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
        }
    }

    private EnumFacing rotateX() {
        switch (this) {
            case NORTH: return DOWN;
            case SOUTH: return UP;
            case UP: return NORTH;
            case DOWN: return SOUTH;
            default: throw new IllegalStateException("Unable to get X-rotated facing of " + this);
        }
    }

    private EnumFacing rotateZ() {
        switch (this) {
            case EAST: return DOWN;
            case WEST: return UP;
            case UP: return EAST;
            case DOWN: return WEST;
            default: throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
        }
    }

    public EnumFacing rotateYCCW() {
        switch (this) {
            case NORTH: return WEST;
            case EAST: return NORTH;
            case SOUTH: return EAST;
            case WEST: return SOUTH;
            default: throw new IllegalStateException("Unable to get CCW facing of " + this);
        }
    }

    public int getFrontOffsetX() {
        return this.axis == Axis.X ? this.axisDirection.getOffset() : 0;
    }

    public int getFrontOffsetY() {
        return this.axis == Axis.Y ? this.axisDirection.getOffset() : 0;
    }

    public int getFrontOffsetZ() {
        return this.axis == Axis.Z ? this.axisDirection.getOffset() : 0;
    }

    public String getName2() {
        return this.name;
    }

    public Axis getAxis() {
        return this.axis;
    }

    public static EnumFacing byName(String name) {
        return name == null ? null : (EnumFacing)NAME_LOOKUP.get(name.toLowerCase());
    }

    public static EnumFacing getFront(int index) {
        return VALUES[MathUtils.absInt((int)(index % VALUES.length))];
    }

    public static EnumFacing getHorizontal(int p_176731_0_) {
        return HORIZONTALS[MathUtils.absInt((int)(p_176731_0_ % HORIZONTALS.length))];
    }

    public static EnumFacing fromAngle(double angle) {
        return EnumFacing.getHorizontal((int)(MathUtils.floorDouble((double)(angle / 90.0 + 0.5)) & 3));
    }

    public static EnumFacing random(Random rand) {
        return EnumFacing.values()[rand.nextInt(EnumFacing.values().length)];
    }

    public static EnumFacing getFacingFromVector(float p_176737_0_, float p_176737_1_, float p_176737_2_) {
        EnumFacing enumfacing = NORTH;
        float f = Float.MIN_VALUE;
        for (EnumFacing enumfacing1 : EnumFacing.values()) {
            float f1 = p_176737_0_ * (float)enumfacing1.directionVec.getX() + p_176737_1_ * (float)enumfacing1.directionVec.getY() + p_176737_2_ * (float)enumfacing1.directionVec.getZ();
            if (!(f1 > f)) continue;
            f = f1;
            enumfacing = enumfacing1;
        }
        return enumfacing;
    }

    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public static EnumFacing func_181076_a(AxisDirection p_181076_0_, Axis p_181076_1_) {
        for (EnumFacing enumfacing : EnumFacing.values()) {
            if (enumfacing.getAxisDirection() != p_181076_0_ || enumfacing.getAxis() != p_181076_1_) continue;
            return enumfacing;
        }
        throw new IllegalArgumentException("No such direction: " + p_181076_0_ + " " + p_181076_1_);
    }

    public Vec3I getDirectionVec() {
        return this.directionVec;
    }

    static {
        VALUES = new EnumFacing[6];
        HORIZONTALS = new EnumFacing[4];
        NAME_LOOKUP = Maps.newHashMap();
        EnumFacing[] enumFacingArray = EnumFacing.values();
        int n = enumFacingArray.length;
        for (int i = 0; i < n; ++i) {
            EnumFacing enumfacing = enumFacingArray[i];
            EnumFacing.VALUES[enumfacing.index] = enumfacing;
            if (enumfacing.getAxis().isHorizontal()) {
                EnumFacing.HORIZONTALS[enumfacing.horizontalIndex] = enumfacing;
            }
            NAME_LOOKUP.put(enumfacing.getName2().toLowerCase(), enumfacing);
        }
    }

    public enum Axis {
        X, Y, Z;
        public boolean isHorizontal() {
            return this == X || this == Z;
        }
        public boolean isVertical() {
            return this == Y;
        }
    }
    public enum AxisDirection {
        POSITIVE(1), NEGATIVE(-1);
        private final int offset;
        AxisDirection(int offset) { this.offset = offset; }
        public int getOffset() { return this.offset; }
    }
}

