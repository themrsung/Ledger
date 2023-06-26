package jbs.ledger.assetholders.corporations.special;

import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.interfaces.currency.CurrencyIssuer;
import jbs.ledger.interfaces.sovereignty.Sovereign;
import jbs.ledger.io.types.assetholders.corporations.special.SovereignCorporationData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;

import java.util.ArrayList;
import java.util.UUID;

public final class SovereignCorporation extends Corporation implements Sovereign, CurrencyIssuer {
    public SovereignCorporation(
            UUID uniqueId,
            String name,
            String symbol,
            String currency,
            Cash capital,
            long shareCount
    ) {
        super(uniqueId, name, symbol, currency, capital, shareCount);

        this.laws = new ArrayList<>();

        this.bannedPlayers = new ArrayList<>();
        this.mutedPlayers = new ArrayList<>();


    }

    public SovereignCorporation(SovereignCorporation copy) {
        super(copy);

        this.laws = copy.laws;

        this.issuedCurrency = copy.issuedCurrency;

        this.bannedPlayers = copy.bannedPlayers;
        this.mutedPlayers = copy.mutedPlayers;
    }

    // Currency issuance
    private String issuedCurrency;

    @Override
    public String getIssuedCurrency() {
        return issuedCurrency;
    }

    public void setIssuedCurrency(String issuedCurrency) {
        this.issuedCurrency = issuedCurrency;
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
        return 5000;
    }

    // Laws
    private ArrayList<String> laws;

    @Override
    public ArrayList<String> getLaws() {
        return new ArrayList<>(laws);
    }

    @Override
    public void addLaw(String law) {

    }

    @Override
    public boolean removeLaw(String law) {
        return laws.remove(law);
    }

    @Override
    public void removeLaw(int index) {
        if (index >= getLaws().size()) return;
        laws.remove(index);
    }

    @Override
    public void changeLaw(int index, String law) {
        if (index >= laws.size()) return;

        laws.set(index, law);
    }

    // Punishment

    private final ArrayList<Person> bannedPlayers;
    private final ArrayList<Person> mutedPlayers;

    @Override
    public ArrayList<Person> getBannedPlayers() {
        return new ArrayList<>(bannedPlayers);
    }

    @Override
    public void addBannedPlayer(Person p) {
        bannedPlayers.add(p);
    }

    @Override
    public boolean removeBannedPlayer(Person p) {
        return bannedPlayers.remove(p);
    }

    @Override
    public ArrayList<Person> getMutedPlayers() {
        return new ArrayList<>(mutedPlayers);
    }

    @Override
    public void addMutedPlayer(Person p) {
        mutedPlayers.add(p);
    }

    @Override
    public boolean removeMutedPlayer(Person p) {
        return mutedPlayers.remove(p);
    }


    // IO

    public SovereignCorporationData toData() {
        SovereignCorporationData data = new SovereignCorporationData(super.toData());

        data.issuedCurrency = issuedCurrency;

        data.laws = laws;

        for (Person p : getBannedPlayers()) {
            data.bannedPlayers.add(p.getUniqueId());
        }

        for (Person p : getMutedPlayers()) {
            data.mutedPlayers.add(p.getUniqueId());
        }

        return data;
    }

    public static SovereignCorporation getEmptyInstance(UUID uniqueId) {
        return new SovereignCorporation(uniqueId);
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.SOVEREIGN_CORPORATION;
    }

    public SovereignCorporation(UUID uniqueId) {
        super(uniqueId);

        this.laws = new ArrayList<>();

        this.bannedPlayers = new ArrayList<>();
        this.mutedPlayers = new ArrayList<>();
    }

    public void load(SovereignCorporationData data, LedgerState state) {
        super.load(data, state);

        this.issuedCurrency = data.issuedCurrency;
        this.laws = data.laws;

        this.bannedPlayers.clear();
        for (UUID id : data.bannedPlayers) {
            this.bannedPlayers.add(state.getPerson(id));
        }

        this.mutedPlayers.clear();
        for (UUID id : data.mutedPlayers) {
            this.mutedPlayers.add(state.getPerson(id));
        }
    }
}
