package jbs.ledger.assetholders;

public enum AssetholderType {
    /**
     * A natural person
     */
    PERSON,

    /**
     * Can be contracted to build in protected areas
     */
    CONSTRUCTION_COMPANY,

    /**
     * Can manufacture special goods (e.g. card terminals)
     */
    MANUFACTURER,

    /**
     * Can accept card payments
     */
    MERCHANT,

    /**
     * Can copy books via commands
     */
    PUBLISHER,

    /**
     * Can handle lawsuits
     */
    LAW_FIRM,

    /**
     * A generic company with no special abilities
     */
    COMPANY,

    /**
     * A non-profit organization
     */
    FOUNDATION,

    /**
     * Can accept deposits, pay interest, issue debit cards, and provide line of credit
     */
    BANK,

    /**
     * Can issue credit cards
     */
    CREDIT_CARD_COMPANY,

    /**
     * FOREX
     */
    FOREIGN_EXCHANGE,

    /**
     * Can process futures orders
     */
    FUTURES_EXCHANGE,

    /**
     * Can process stock/bond orders
     */
    SECURITIES_EXCHANGE,

    /**
     * Able to participate in warfare as an independent faction
     */
    PRIVATE_MILITARY,

    /**
     * An entity that can hold assets. Cannot be taxed.
     */
    INVESTMENT_TRUST,

    /**
     * An entity that can hold property. Cannot be taxed.
     */
    REAL_ESTATE_TRUST,

    /**
     * A republic with presidency
     */
    PRESIDENTIAL_REPUBLIC,

    /**
     * A republic with parliamentary governance
     */
    PARLIAMENTARY_REPUBLIC,

    /**
     * An autocracy
     */
    PRINCIPALITY,

    /**
     * An organization of multiple nations as member states.
     * Every member state has votes proportional to their population count.
     * Declaring war on a member state automatically triggers declaration from all member states.
     */
    FEDERATION;
}
