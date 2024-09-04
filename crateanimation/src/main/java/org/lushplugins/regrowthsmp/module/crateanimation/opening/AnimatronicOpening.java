package org.lushplugins.regrowthsmp.module.crateanimation.opening;

import me.thundertnt33.animatronics.api.Animatronic;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lushplugins.regrowthsmp.module.crateanimation.CrateAnimation;
import su.nightexpress.excellentcrates.CratesAPI;
import su.nightexpress.excellentcrates.api.event.CrateObtainRewardEvent;
import su.nightexpress.excellentcrates.crate.impl.CrateSource;
import su.nightexpress.excellentcrates.crate.impl.Reward;
import su.nightexpress.excellentcrates.key.CrateKey;
import su.nightexpress.excellentcrates.opening.AbstractOpening;

public class AnimatronicOpening extends AbstractOpening {
    private boolean rolled = false;

    public AnimatronicOpening(@NotNull Player player, @NotNull CrateSource source, @Nullable CrateKey key) {
        super(CratesAPI.PLUGIN, player, source, key);
    }

    @Override
    public void instaRoll() {
        this.roll();
        this.stop();
    }

    @Override
    public boolean isCompleted() {
        return rolled;
    }

    @Override
    public long getInterval() {
        return 1;
    }

    @Override
    protected void onLaunch() {}

    @Override
    protected void onTick() {
        super.onTick();
        if (this.isRunning()) {
            this.roll();
            this.stop();
        }
    }

    public void roll() {
        this.setRefundable(false);
        this.setHasRewardAttempts(true);

        Animatronic animatronic = new Animatronic("DefaultCrate");
        animatronic.start();

        Block crateBlock = source.getBlock();
        if (crateBlock != null) {
            Location particleLocation = crateBlock.getLocation().clone().add(0.5, 1.5, 0.5);

            String[][] particleScheduler = {
                {"SPIT", "3", "30"},
                {"SPIT", "3", "40"},
                {"SPIT", "3", "50"},
                {"LAVA", "15", "70"},
                {"SPIT", "5", "70"},
                {"SPIT", "5", "173"}
            };
            particleSchedule(particleScheduler, particleLocation);

            String[][] soundScheduler = {
                {"BLOCK_SCAFFOLDING_HIT", "1", "0.5", "30"},
                {"BLOCK_SCAFFOLDING_HIT", "1", "0.5", "40"},
                {"BLOCK_SCAFFOLDING_HIT", "1", "0.5", "50"},
                {"ENTITY_GENERIC_EXPLODE", "0.7", "1", "70"},
                {"BLOCK_NOTE_BLOCK_PLING", "1", "2", "173"}
            };
            soundSchedule(soundScheduler, particleLocation);
        }

        JavaPlugin plugin = CrateAnimation.getInstance().getPlugin();
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Reward reward = crate.rollReward(player);

            ArmorStand armorStand = animatronic.getArmorstand();
            if (armorStand != null) {
                armorStand.getEquipment().setHelmet(reward.getPreview());
            }

            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                if (armorStand != null) {
                    armorStand.getEquipment().setHelmet(reward.getPreview());
                }

                Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                    reward.give(player);

                    CrateObtainRewardEvent rewardEvent = new CrateObtainRewardEvent(reward, this.player);
                    this.plugin.getPluginManager().callEvent(rewardEvent);

                    Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                        rolled = true;
                        animatronic.gotoStart();
                        CrateAnimation.getInstance().unlockCrate(crate.getName());
                    }, 20);
                }, 61);
            }, 42);
        }, 70);
    }

    private void particleSchedule(String[][] particleScheduleArr, Location location) {
        for (String[] strings : particleScheduleArr) {
            Particle particleType = Particle.valueOf(strings[0]);
            int particleCount = Integer.parseInt(strings[1]);
            int delay = Integer.parseInt(strings[2]);

            Bukkit.getScheduler().runTaskLater(CrateAnimation.getInstance().getPlugin(), () -> {
                location.getWorld().spawnParticle(particleType, location, particleCount, 0, 0, 0, 0);
            }, delay);
        }
    }

    private void soundSchedule(String[][] soundScheduleArr, Location location) {
        for (String[] strings : soundScheduleArr) {
            Sound sound = Sound.valueOf(strings[0]);
            float volume = Float.parseFloat(strings[1]);
            float pitch = Float.parseFloat(strings[2]);
            int delay = Integer.parseInt(strings[3]);

            Bukkit.getScheduler().runTaskLater(CrateAnimation.getInstance().getPlugin(), () -> {
                location.getWorld().playSound(location, sound, volume, pitch);
            }, delay);
        }
    }
}
