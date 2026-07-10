/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.gui.core.box.BoxElementBuilder
 *  com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxComponent
 *  com.cosmeticsmod.morecosmetics.models.model.CosmeticModel
 *  com.cosmeticsmod.morecosmetics.user.CosmeticUser
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  v1_21.morecosmetics.gui.builder.BoxElementContainer
 *  v1_21.morecosmetics.gui.elements.box.CosmeticElement
 */
package v1_21.morecosmetics.gui.builder;

import com.cosmeticsmod.morecosmetics.gui.core.box.BoxElementBuilder;
import com.cosmeticsmod.morecosmetics.gui.core.box.utils.BoxComponent;
import com.cosmeticsmod.morecosmetics.models.model.CosmeticModel;
import com.cosmeticsmod.morecosmetics.user.CosmeticUser;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import v1_21.morecosmetics.gui.elements.box.CosmeticElement;

@Environment(value=EnvType.CLIENT)
public class BoxElementContainer
extends BoxElementBuilder {
    public BoxComponent getCosmeticElement(CosmeticModel model, CosmeticUser user, Consumer<Boolean> toggleCallback) {
        return new CosmeticElement(model, user, toggleCallback);
    }
}

