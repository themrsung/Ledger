package jbs.ledger.types.assets;

import jbs.ledger.interfaces.assets.IntegralAsset;
import jbs.ledger.interfaces.common.Economic;
import org.bukkit.Material;

import javax.annotation.Nullable;

/**
 * Commodity
 * Has a material type(string) and quantity(long).
 * Non-Minecraft materials are supported, but cannot be delivered.
 */
public final class Commodity implements IntegralAsset {
    public Commodity(final Commodity copy) {
        this.holder = copy.holder;
        this.symbol = copy.symbol;
        this.quantity = copy.quantity;
    }
    public Commodity() {
        this.holder = null;
        this.symbol = null;
        this.quantity = 0L;
    }

    private Economic holder;
    private final String symbol;
    private long quantity;


    @Override
    public Economic getHolder() {
        return holder;
    }

    @Override
    public void setHolder(Economic holder) {
        this.holder = holder;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public long getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Nullable
    public Material getMaterial() {
        try {
            return Material.valueOf(getSymbol());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public Commodity copy() {
        return new Commodity(this);
    }

    @Override
    public Commodity negate() {
        return (Commodity) IntegralAsset.super.negate();
    }
}
