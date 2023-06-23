package jbs.ledger.io.types.portfolios;

import jbs.ledger.io.types.assets.AssetData;

import java.util.ArrayList;
import java.util.UUID;

public abstract class PortfolioData<A extends AssetData> {
    public PortfolioData() {}

    public UUID holder;
    public ArrayList<A> entries;
}
