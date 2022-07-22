package io.ncbpfluffybear.husktownsfly;

import net.william278.husktowns.chunk.ClaimedChunk;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class FlyListener implements Listener {

    public FlyListener() {
    }

    @EventHandler
    private void onFlyToggle(PlayerToggleFlightEvent e) {
        if (e.getPlayer().hasPermission("husktownfly.bypass")) {
            return;
        }

        if (e.isFlying()) {

            Player p = e.getPlayer();

            String playerTown = HuskTownsFly.getApi().getPlayerTown(p);
            if (playerTown == null) {
                cancelFlight(e);
                return;
            }

            ClaimedChunk chunk = HuskTownsFly.getApi().getClaimedChunk(p.getLocation());
            if (chunk == null) {
                cancelFlight(e);
                return;
            }

            String currTown = chunk.getTown();
            if (currTown == null) {
                cancelFlight(e);
                return;
            }

            if (!currTown.equalsIgnoreCase(playerTown)) {
                cancelFlight(e);
            }
        }
    }

    private void cancelFlight(PlayerToggleFlightEvent e) {
        e.setCancelled(true);
        e.getPlayer().setAllowFlight(false);
        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes(
                '&', "&6HuskTownsFly » &cYou can only turn on flight in your town!"
        ));
    }

}
