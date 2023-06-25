package jbs.ledger.assetholders.corporations.special;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.interfaces.sovereignty.Sovereign;
import jbs.ledger.io.types.assetholders.corporations.special.SovereignCorporationData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;

import java.util.UUID;

public final class SovereignCorporation extends Corporation implements Sovereign {
    public SovereignCorporation(
            UUID uniqueId,
            String name,
            String symbol,
            String currency,
            Cash capital,
            long shareCount
    ) {
        super(uniqueId, name, symbol, currency, capital, shareCount);
    }

    public SovereignCorporation(Corporation copy) {
        super(copy);
    }

    // Powers

    @Override
    public boolean hasAdministrativePower(Person person) {
        return getBoard().getMembers().contains(person);
    }

    @Override
    public boolean hasLegislativePower(Person person) {
        return getBoard().getMembers().contains(person);
    }

    @Override
    public boolean hasJudicialPower(Person person) {
        return getBoard().getMembers().contains(person);
    }

    @Override
    public boolean hasClemency(Person person) {
        if (getRepresentative() == null) return false;
        return getRepresentative().equals(person);
    }

    // Protection

    @Override
    public long getProtectionRadius() {
        return 1000;
    }


    // IO

    public SovereignCorporationData toData() {
        SovereignCorporationData data = new SovereignCorporationData(super.toData());

        return data;
    }

    public static SovereignCorporation getEmptyInstance(UUID uniqueId) {
        return new SovereignCorporation(uniqueId);
    }

    @Override
    public AssetholderType getType() {
        return null;
    }

    public SovereignCorporation(UUID uniqueId) {
        super(uniqueId);
    }

    public void load(SovereignCorporationData data, LedgerState state) {
        super.load(data, state);
    }
}
