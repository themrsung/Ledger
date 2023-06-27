package jbs.ledger.types.config;

import jbs.ledger.io.types.navigation.Address;
import org.bukkit.Material;

public final class LedgerConfig {
    public LedgerConfig() {}

    public Address SERVER_SPAWN = null;
    public String DEFAULT_CURRENCY = "CR";

    public Material CREDIT_CARD_ITEM = Material.BOOK;
    public Material CREDIT_CARD_TERMINAL_BLOCK = Material.COMMAND_BLOCK;

}
