/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3D
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built;

public class Vec3D {
    public static final Vec3D NULL_VECTOR = new Vec3D(0.0, 0.0, 0.0);
    public double x;
    public double y;
    public double z;

    public Vec3D(double xIn, double yIn, double zIn) {
        this.x = xIn;
        this.y = yIn;
        this.z = zIn;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public Vec3D crossProduct(Vec3D vec) {
        return new Vec3D(this.getY() * vec.getZ() - this.getZ() * vec.getY(), this.getZ() * vec.getX() - this.getX() * vec.getZ(), this.getX() * vec.getY() - this.getY() * vec.getX());
    }

    public double distanceSq(double toX, double toY, double toZ) {
        double d0 = this.getX() - toX;
        double d1 = this.getY() - toY;
        double d2 = this.getZ() - toZ;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double distanceSqToCenter(double xIn, double yIn, double zIn) {
        double d0 = this.getX() + 0.5 - xIn;
        double d1 = this.getY() + 0.5 - yIn;
        double d2 = this.getZ() + 0.5 - zIn;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double distanceSq(Vec3D to) {
        return this.distanceSq(to.getX(), to.getY(), to.getZ());
    }

    public String toString() {
        return this.getX() + "," + this.getY() + "," + this.getZ();
    }
}

