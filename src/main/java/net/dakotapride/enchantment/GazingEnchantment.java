package net.dakotapride.enchantment;

import net.dakotapride.item.SpectreUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class GazingEnchantment extends Enchantment {
    public GazingEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return !(other instanceof CultistEnchantment) && super.canAccept(other);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return SpectreUtils.canApply$SpectreScythe(stack) || SpectreUtils.canApply$IchorScythe(stack);
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return true;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return true;
    }
}
