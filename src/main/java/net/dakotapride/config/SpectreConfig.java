package net.dakotapride.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.dakotapride.SpectreMain;

@Config(name = SpectreMain.id)
public class SpectreConfig implements ConfigData {

    @Comment("Determines if Phantoms spawn due to the Player not sleeping")
    public boolean allowInsomnia = true;

    @Comment("Determines if Phantoms spawn an ABNORMAL amount no matter the conditions")
    public boolean insanityPhantoms = false;

    @Comment("Amount of days before Phantoms can start spawning due to insomnia")
    public int daysUntilInsomniaTakesAffect = 3;

    @Comment("Determines if Phantoms spawn naturally in the End dimension")
    public boolean allowEndSpawning = true;

    @Comment("Determines if Phantoms spawn naturally in the End dimension")
    public int endSpawnWeight = 4;

    @Comment("Determines the minimum group size Phantoms can spawn in the End dimension")
    public int minEndSpawnSize = 1;

    @Comment("Determines the maximum group size Phantoms can spawn in the End dimension")
    public int maxEndSpawnSize = 2;

    @Comment("Determines how many seconds Pursuing lasts once applied from Gazing")
    public int pursuingEffectSeconds = 3;

    @Comment("Determines how many seconds the cooldown for the Unwearied Antique lasts")
    public int antiqueCooldown = 15;

    @Comment("Determines how many seconds Strength from Blood Rush lasts")
    public int bloodRushStrengthDuration = 15;

    @Comment("Determines how many seconds Strength from Sanguinary lasts")
    public int sanguinaryStrengthDuration = 10;

    public static SpectreConfig getInstance() {
        return AutoConfig.getConfigHolder(SpectreConfig.class).getConfig();
    }

}
