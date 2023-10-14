package io.ncbpfluffybear.husktownsfly;

import net.william278.husktowns.chunk.ClaimedChunk;
import net.william278.husktowns.claim.TownClaim;
import net.william278.husktowns.town.Member;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Optional;

public class CheckFlyTask implements Runnable {
    @Override
    public void run() {
        for (Player p : HuskTownsFly.getInstance().getServer().getOnlinePlayers()) {
            if (p.hasPermission("husktownfly.bypass") || !p.isFlying()) {
                continue;
            }

            Optional<Member> playerTown = HuskTownsFly.getApi().getUserTown(p);
            if (playerTown.isEmpty()) {
                disableFlight(p);
                return;
            }

            Optional<TownClaim> chunk = HuskTownsFly.getApi().getClaimAt(p.getLocation());
            if (chunk.isEmpty()) {
                disableFlight(p);
                return;
            }


            if (!chunk.get().town().equals(playerTown.get().town())) {
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
