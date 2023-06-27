package jbs.ledger.interfaces.cards;

import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.common.Searchable;

import java.util.Date;

public interface Card extends Searchable {
    @Override
    default String getSearchTag() {
        // Changing this after deployment should be of no consequence
        return getUniqueId().toString().substring(0, 16);
    }

    CardIssuer getIssuer();
    Economic getHolder();

    String getCurrency();

    Date getExpiration();

    double getPayable();
    double getBalance();

    boolean isActive();
    void setActive(boolean active);

    void addBalance(double delta);
    void removeBalance(double delta);
}
