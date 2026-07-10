/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.utils.MathUtils
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 *  v1_21.morecosmetics.models.renderer.QuaternionHelper
 */
package v1_21.morecosmetics.models.renderer;

import com.cosmeticsmod.morecosmetics.utils.MathUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/*
 * Exception performing whole class analysis ignored.
 */
@Environment(value=EnvType.CLIENT)
public class QuaternionHelper {
    public static Vector3f NEGATIVE_X = new Vector3f(-1.0f, 0.0f, 0.0f);
    public static Vector3f POSITIVE_X = new Vector3f(1.0f, 0.0f, 0.0f);
    public static Vector3f NEGATIVE_Y = new Vector3f(0.0f, -1.0f, 0.0f);
    public static Vector3f POSITIVE_Y = new Vector3f(0.0f, 1.0f, 0.0f);
    public static Vector3f NEGATIVE_Z = new Vector3f(0.0f, 0.0f, -1.0f);
    public static Vector3f POSITIVE_Z = new Vector3f(0.0f, 0.0f, 1.0f);

    public static Quaternionf getDegreesQuaternion(Vector3f axis, float angle) {
        return QuaternionHelper.getQuaternion((Vector3f)axis, (float)angle, (boolean)true);
    }

    public static Quaternionf getRadialQuaternion(Vector3f axis, float angle) {
        return QuaternionHelper.getQuaternion((Vector3f)axis, (float)angle, (boolean)false);
    }

    public static Quaternionf getQuaternion(Vector3f axis, float angle, boolean degrees) {
        if (degrees) {
            angle *= (float)Math.PI / 180;
        }
        float f = MathUtils.sin((float)(angle / 2.0f));
        float x = axis.x() * f;
        float y = axis.y() * f;
        float z = axis.z() * f;
        float w = MathUtils.cos((float)(angle / 2.0f));
        return new Quaternionf(x, y, z, w);
    }

    public static Quaternionf getQuaternion(float x, float y, float z, boolean degrees) {
        if (degrees) {
            x *= (float)Math.PI / 180;
            y *= (float)Math.PI / 180;
            z *= (float)Math.PI / 180;
        }
        float f = MathUtils.sin((float)(0.5f * x));
        float g = MathUtils.cos((float)(0.5f * x));
        float h = MathUtils.sin((float)(0.5f * y));
        float i = MathUtils.cos((float)(0.5f * y));
        float j = MathUtils.sin((float)(0.5f * z));
        float k = MathUtils.cos((float)(0.5f * z));
        x = f * i * k + g * h * j;
        y = g * h * k - f * i * j;
        z = f * h * k + g * i * j;
        float w = g * i * k - f * h * j;
        return new Quaternionf(x, y, z, w);
    }
}

