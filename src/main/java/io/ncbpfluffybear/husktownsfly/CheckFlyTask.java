package io.ncbpfluffybear.husktownsfly;

import net.william278.husktowns.chunk.ClaimedChunk;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CheckFlyTask implements Runnable {
    @Override
    public void run() {
        for (Player p : HuskTownsFly.getInstance().getServer().getOnlinePlayers()) {
            if (p.hasPermission("husktownfly.bypass") || !p.isFlying()) {
                continue;
            }

            String playerTown = HuskTownsFly.getApi().getPlayerTown(p);
            if (playerTown == null) {
                disableFlight(p);
                return;
            }

            ClaimedChunk chunk = HuskTownsFly.getApi().getClaimedChunk(p.getLocation());
            if (chunk == null) {
                disableFlight(p);
                return;
            }

            String currTown = chunk.getTown();
            if (currTown == null) {
                disableFlight(p);
                return;
            }

            if (!playerTown.equalsIgnoreCase(currTown)) {
                disableFlight(p);
            }
        }
    }

    private void disableFlight(Player p) {
        p.setAllowFlight(false);
        p.sendMessage(ChatColor.translateAlternateColorCodes(
                '&', "&6HuskTownsFly » &cYou have left your town, flight has been disabled!"
        ));
    }
}
