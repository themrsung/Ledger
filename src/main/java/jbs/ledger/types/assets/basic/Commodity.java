package jbs.ledger.types.assets.basic;

import jbs.ledger.interfaces.assets.IntegralAsset;
import jbs.ledger.io.types.assets.basic.CommodityData;
import jbs.ledger.types.assets.AssetType;
import org.bukkit.Material;

import javax.annotation.Nullable;

/**
 * Commodity
 * Has a material type(string) and quantity(long).
 * Non-Minecraft materials are supported, but cannot be delivered.
 */
public final class Commodity implements IntegralAsset {
    /**
     * Constructs a new commodity instance
     * @param symbol Identifier of this commodity
     * @param quantity Quantity
     */
    public Commodity(
            String symbol,
            long quantity
    ) {
        this.symbol = symbol;
        this.quantity = quantity;
    }
    public Commodity(final Commodity copy) {
        this.symbol = copy.symbol;
        this.quantity = copy.quantity;
    }
    public Commodity() {
        this.symbol = null;
        this.quantity = 0L;
    }

    private final String symbol;
    private long quantity;


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

    @Override
    public AssetType getType() {
        return AssetType.COMMODITY;
    }

    // IO
    public static Commodity fromData(CommodityData data) {
        return new Commodity(data);
    }

    private Commodity(CommodityData data) {
        this.symbol = data.symbol;
        this.quantity = data.quantity;
    }

    public CommodityData toData() {
        CommodityData data = new CommodityData();

        data.type = AssetType.COMMODITY;
        data.symbol = symbol;
        data.quantity = quantity;

        return data;
    }

}
