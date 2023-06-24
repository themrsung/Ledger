package jbs.ledger.interfaces.common;

public interface Symbolic extends Searchable {
    String getSymbol();

    @Override
    default String getSearchTag() {
        return getSymbol();
    }
}
