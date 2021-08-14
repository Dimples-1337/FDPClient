package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.utils.GLUtils;

public final class Vec2f {
    private float x;
    private float y;

    public Vec2f() {
        this(0.0f, 0.0f);
    }

    public Vec2f(Vec2f vec) {
        this(vec.x, vec.y);
    }

    public Vec2f(double x, double y) {
        this((float)x, (float)y);
    }

    public Vec2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public final Vec2f setX(float x) {
        this.x = x;
        return this;
    }

    public final Vec2f setY(float y) {
        this.y = y;
        return this;
    }

    public final float getX() {
        return this.x;
    }

    public final float getY() {
        return this.y;
    }

    public final Vec2f add(Vec2f vec) {
        return new Vec2f(this.x + vec.x, this.y + vec.y);
    }

    public final Vec2f add(double x, double y) {
        return this.add(new Vec2f(x, y));
    }

    public final Vec2f add(float x, float y) {
        return this.add(new Vec2f(x, y));
    }

    public final Vec2f sub(Vec2f vec) {
        return new Vec2f(this.x - vec.x, this.y - vec.y);
    }

    public final Vec2f sub(double x, double y) {
        return this.sub(new Vec2f(x, y));
    }

    public final Vec2f sub(float x, float y) {
        return this.sub(new Vec2f(x, y));
    }

    public final Vec2f scale(float scale) {
        return new Vec2f(this.x * scale, this.y * scale);
    }

    public final Vec3f toVec3() {
        return new Vec3f(this.x, this.y, 0.0);
    }

    public final Vec2f copy() {
        return new Vec2f(this);
    }

    public final Vec2f transfer(Vec2f vec) {
        this.x = vec.x;
        this.y = vec.y;
        return this;
    }

    public final float distanceTo(Vec2f vec) {
        double dx = this.x - vec.x;
        double dy = this.y - vec.y;
        return (float)Math.sqrt(dx * dx + dy * dy);
    }

    public final Vec3f toScreen() {
        return GLUtils.toWorld(this.toVec3());
    }

    public String toString() {
        return "Vec2{x=" + this.x + ", y=" + this.y + '}';
    }
}
