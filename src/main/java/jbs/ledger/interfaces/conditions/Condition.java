package jbs.ledger.interfaces.conditions;

public interface Condition  {
    boolean isMet();
    Condition invert();
}
