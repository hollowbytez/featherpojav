/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built.Vec3F
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.render.built;

public class Vec3F {
    public static final Vec3F NULL_VECTOR = new Vec3F(0.0f, 0.0f, 0.0f);
    public float x;
    public float y;
    public float z;

    public Vec3F(float xIn, float yIn, float zIn) {
        this.x = xIn;
        this.y = yIn;
        this.z = zIn;
    }

    public void set(float xIn, float yIn, float zIn) {
        this.x = xIn;
        this.y = yIn;
        this.z = zIn;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return this.z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public Vec3F crossProduct(Vec3F vec) {
        return new Vec3F(this.getY() * vec.getZ() - this.getZ() * vec.getY(), this.getZ() * vec.getX() - this.getX() * vec.getZ(), this.getX() * vec.getY() - this.getY() * vec.getX());
    }

    public double distanceSq(double toX, double toY, double toZ) {
        double d0 = (double)this.getX() - toX;
        double d1 = (double)this.getY() - toY;
        double d2 = (double)this.getZ() - toZ;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double distanceSqToCenter(double xIn, double yIn, double zIn) {
        double d0 = (double)this.getX() + 0.5 - xIn;
        double d1 = (double)this.getY() + 0.5 - yIn;
        double d2 = (double)this.getZ() + 0.5 - zIn;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double distanceSq(Vec3F to) {
        return this.distanceSq((double)to.getX(), (double)to.getY(), (double)to.getZ());
    }

    public String toString() {
        return this.getX() + "," + this.getY() + "," + this.getZ();
    }
}

