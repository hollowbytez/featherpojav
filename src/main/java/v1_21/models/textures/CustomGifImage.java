/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  v1_21.morecosmetics.models.textures.CustomGifImage
 *  v1_21.morecosmetics.models.textures.CustomImage
 */
package v1_21.morecosmetics.models.textures;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import v1_21.morecosmetics.models.textures.CustomImage;

@Environment(value=EnvType.CLIENT)
public class CustomGifImage {
    private int[] delays;
    private CustomImage[] locations;
    private int current;
    private int frames;
    private long last;

    public CustomGifImage(CustomImage[] locations, int[] delays) {
        this.locations = locations;
        this.delays = delays;
        this.frames = locations.length;
    }

    public CustomImage getFrame() {
        if (System.currentTimeMillis() - this.last >= (long)this.delays[this.current]) {
            this.last = System.currentTimeMillis();
            this.current = this.current < this.frames - 1 ? ++this.current : 0;
        }
        return this.locations[this.current];
    }

    public int getDelay() {
        return this.delays[this.current];
    }

    public void delete() {
        for (CustomImage img : this.locations) {
            img.delete();
        }
    }
}

