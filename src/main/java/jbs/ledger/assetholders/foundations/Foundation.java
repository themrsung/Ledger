package jbs.ledger.assetholders.foundations;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.interfaces.common.Symbolic;
import jbs.ledger.interfaces.organization.Directorship;
import jbs.ledger.interfaces.sovereignty.NationMember;
import jbs.ledger.io.types.assetholders.foundations.FoundationData;
import jbs.ledger.organizations.corporate.Board;
import jbs.ledger.state.LedgerState;

import java.util.UUID;

/**
 * Foundations are not taxed, and cannot pay dividends.
 * Unlike a corporation, there are no shareholders or shareholder meetings.
 * Decisions of consequence must pass a board meeting.
 */
public final class Foundation extends Assetholder implements NationMember, Symbolic, Directorship {
    public Foundation(UUID uniqueId, String name, String symbol) {
        super(uniqueId, name);

        this.symbol = symbol;
        this.board = new Board();

        this.board.owner = this;
    }

    public Foundation(Foundation copy) {
        super(copy);

        this.symbol = copy.symbol;
        this.board = copy.board;

        this.board.owner = this;
    }

    private String symbol;
    private Board board;

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String getSearchTag() {
        return getSymbol();
    }

    @Override
    public AssetholderType getType() {
        return AssetholderType.FOUNDATION;
    }

    @Override
    public boolean hasPropertyAccess(Person person) {
        return getBoard().getMembers().contains(person);
    }

    // IO
    public FoundationData toData() {
        FoundationData data = new FoundationData(super.toData());

        data.symbol = symbol;
        data.board = board.toData();

        return data;
    }

    private Foundation(UUID uniqueId) {
        super(uniqueId);

        this.board = new Board();

        this.board.owner = this;
    }

    public static Foundation getEmptyInstance(UUID uniqueId) {
        return new Foundation(uniqueId);
    }

    public void load(FoundationData data, LedgerState state) {
        super.load(data, state);

        this.symbol = data.symbol;
        this.board = Board.fromData(data.board, state);

        this.board.owner = this;
    }

}
