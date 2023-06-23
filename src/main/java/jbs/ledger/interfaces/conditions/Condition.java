package jbs.ledger.interfaces.conditions;

/**
 * An invertible condition.
 */
public interface Condition  {
    boolean isMet();
    Condition invert();
}
