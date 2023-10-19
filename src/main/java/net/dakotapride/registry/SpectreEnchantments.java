package net.dakotapride.registry;

import net.dakotapride.SpectreMain;
import net.dakotapride.enchantment.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class SpectreEnchantments {

    public static Enchantment GAZING;
    public static Enchantment CULTIST;
    public static Enchantment BLOOD_RUSH;
    public static Enchantment ADRIFT;
    public static Enchantment CLOAKING;
    public static Enchantment CONNECTION;
    public static Enchantment SPIRITUAL_LEECH;

    public static void enchantments() {
        GAZING = Registry.register(Registries.ENCHANTMENT, SpectreMain.createResource("gazing"),
                new GazingEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON,
                        new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
        CULTIST = Registry.register(Registries.ENCHANTMENT, SpectreMain.createResource("cultist"),
                new CultistEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON,
                        new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
        BLOOD_RUSH = Registry.register(Registries.ENCHANTMENT, SpectreMain.createResource("blood_rush"),
                new BloodRushEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON,
                        new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
        ADRIFT = Registry.register(Registries.ENCHANTMENT, SpectreMain.createResource("adrift"),
                new AdriftEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON,
                        new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
        CLOAKING = Registry.register(Registries.ENCHANTMENT, SpectreMain.createResource("cloaking"),
                new CloakingEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON,
                        new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
        CONNECTION = Registry.register(Registries.ENCHANTMENT, SpectreMain.createResource("connection"),
                new ConnectionEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON,
                        new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
        SPIRITUAL_LEECH = Registry.register(Registries.ENCHANTMENT, SpectreMain.createResource("spiritual_leech"),
                new SpiritualLeechEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON,
                        new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
    }

}
