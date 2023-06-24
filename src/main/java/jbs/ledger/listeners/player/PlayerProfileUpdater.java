package jbs.ledger.listeners.player;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.listeners.LedgerListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerProfileUpdater extends LedgerListener {
    public PlayerProfileUpdater(Ledger ledger) {
        super(ledger);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Person person = getState().getPerson(player.getUniqueId());

        if (person == null) {
            Person profile = new Person(player.getUniqueId(), player.getName());
            getState().addAssetholder(profile);

        } else {
            person.setName(player.getName());
        }
    }
}
