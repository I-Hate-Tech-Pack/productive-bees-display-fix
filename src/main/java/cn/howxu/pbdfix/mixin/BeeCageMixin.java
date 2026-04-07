package cn.howxu.pbdfix.mixin;

import cn.howxu.pbdfix.PBDFix;
import cy.jdkdigital.productivebees.ProductiveBees;
import cy.jdkdigital.productivebees.common.item.BeeCage;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nonnull;

/**
 * @description: TODO
 * @author: HowXu
 * @date: 2026/4/7 16:38
 */
@OnlyIn(Dist.CLIENT)
@Mixin(BeeCage.class)
public class BeeCageMixin {
    /**
     * @author howxu
     * @reason fix
     */
    @Nonnull
    @Overwrite
    public Component getName(ItemStack stack) {
        BeeCage self = (BeeCage) (Object) this;
        if (!isFilled(stack)) {
            return Component.translatable(self.getDescriptionId());
        } else {
            String typeId = ((CustomData) stack.get(DataComponents.CUSTOM_DATA)).copyTag().getString("type");
            String entityId = ((CustomData) stack.get(DataComponents.CUSTOM_DATA)).copyTag().getString("entity");
            // return Component.translatable(self.getDescriptionId()).append(Component.literal(" (" + entityId + ")"));
            return Component.translatable(self.getDescriptionId()).append(Component.literal(" (")).append(getTranslationKey(typeId, entityId)).append(Component.literal(")"));
        }
    }

    @Shadow
    public static boolean isFilled(ItemStack itemStack) {
        throw new AssertionError(itemStack);
    }

    private static Component getTranslationKey(String typeId, String entityId) {
        boolean should_add_prefix = true;
        String id;
        if (entityId.equals(ProductiveBees.MODID + ":configurable_bee")) {
            id = typeId;
        } else {
            id = entityId;
            should_add_prefix = false;
        }
        String[] splits = id.split(":");
        if (splits.length < 2) {
            return Component.literal(id);
        }
        // gen an entity.productivebee.xxx_bee name from lang.json
        Component translatable = Component.translatable("entity." + ProductiveBees.MODID + "." + (id.split(":"))[1] + (should_add_prefix ? "_bee" : ""));
        // 相等则返回本身的字符 找到了就返回翻译键
        if (translatable.getString().equals(id)) {
            return Component.literal(id);
        } else {
            return translatable;
        }
    }
}
