package io.ncbpfluffybear.husktownsfly;

import net.william278.husktowns.claim.TownClaim;
import net.william278.husktowns.town.Member;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.Optional;

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

            Optional<Member> playerTown = HuskTownsFly.getApi().getUserTown(p);
            if (playerTown.isEmpty()) {
                cancelFlight(e);
                return;
            }

            Optional<TownClaim> chunk = HuskTownsFly.getApi().getClaimAt(p.getLocation());
            if (chunk.isEmpty()) {
                cancelFlight(e);
                return;
            }


            if (!chunk.get().town().equals(playerTown.get().town())) {
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
