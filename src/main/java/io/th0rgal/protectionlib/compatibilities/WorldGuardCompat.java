package io.th0rgal.protectionlib.compatibilities;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import io.th0rgal.protectionlib.ProtectionCompatibility;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class WorldGuardCompat extends ProtectionCompatibility {

    private final RegionContainer regionContainer;
    private final WorldGuard worldGuard;

    public WorldGuardCompat(Plugin plugin) {
        super(plugin);
        worldGuard = WorldGuard.getInstance();
        regionContainer = worldGuard.getPlatform().getRegionContainer();
    }

    @Override
    public boolean canBuild(Player player, Location target) {
        LocalPlayer localPlayer = ((WorldGuardPlugin) getPlugin()).wrapPlayer(player);
        return regionContainer.createQuery().testBuild(BukkitAdapter.adapt(target), localPlayer, Flags.BLOCK_PLACE)
                || worldGuard.getPlatform()
                .getSessionManager()
                .hasBypass(localPlayer, BukkitAdapter.adapt(player.getWorld())
                );
    }

    @Override
    public boolean canBreak(Player player, Location target) {
        LocalPlayer localPlayer = ((WorldGuardPlugin) getPlugin()).wrapPlayer(player);
        return regionContainer.createQuery().testBuild(BukkitAdapter.adapt(target), localPlayer, Flags.BLOCK_BREAK)
                || worldGuard.getPlatform()
                .getSessionManager()
                .hasBypass(localPlayer, BukkitAdapter.adapt(player.getWorld())
                );
    }
}
