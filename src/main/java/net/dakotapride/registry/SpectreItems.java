package net.dakotapride.registry;

import net.dakotapride.SpectreMain;
import net.dakotapride.item.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class SpectreItems {

    public static Item GHOUL_SILK;
    public static Item PHANTOM_EYE;
    public static Item SPECTRE_SCYTHE;
    public static Item SCYTHE_OF_ICHOR;
    public static Item UNWEARIED_ANTIQUE;

    public static void items() {
        GHOUL_SILK = Registry.register(Registries.ITEM, SpectreMain.createResource("ghoul_silk"),
                new Item(new FabricItemSettings().fireproof().rarity(Rarity.RARE)));
        PHANTOM_EYE = Registry.register(Registries.ITEM, SpectreMain.createResource("phantom_eye"),
                new Item(new FabricItemSettings().fireproof().rarity(Rarity.RARE)));
        SPECTRE_SCYTHE = Registry.register(Registries.ITEM, SpectreMain.createResource("spectre_scythe"),
                new SpectreScytheItem(new FabricItemSettings().fireproof().rarity(Rarity.EPIC)));
        SCYTHE_OF_ICHOR = Registry.register(Registries.ITEM, SpectreMain.createResource("ichor_scythe"),
                new ScytheOfIchorItem(new FabricItemSettings().fireproof().rarity(Rarity.EPIC)));

        UNWEARIED_ANTIQUE = Registry.register(Registries.ITEM, SpectreMain.createResource("unwearied_antique"),
                new UnweariedAntiqueItem(new FabricItemSettings().fireproof().maxCount(1).maxDamage(24).rarity(Rarity.RARE)));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(GHOUL_SILK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(PHANTOM_EYE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(SPECTRE_SCYTHE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(SPECTRE_SCYTHE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(SCYTHE_OF_ICHOR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(SCYTHE_OF_ICHOR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(UNWEARIED_ANTIQUE));
    }

}
