package jbs.ledger.io.types.meetings;

public enum MeetingType {
    // Board meetings
    BOARD_CASH_DIVIDEND,
    BOARD_STOCK_DIVIDEND,
    BOARD_STOCK_SPLIT,
    BOARD_STOCK_ISSUE,
    BOARD_STOCK_RETIRE,
    BOARD_BOND_ISSUE,


    // Shareholder meetings
    SHAREHOLDER_HIRE_CEO,
    SHAREHOLDER_FIRE_CEO,
    SHAREHOLDER_HIRE_DIRECTOR,
    SHAREHOLDER_FIRE_DIRECTOR,
    SHAREHOLDER_CHANGE_NAME,
    SHAREHOLDER_CASH_DIVIDEND,
    SHAREHOLDER_STOCK_DIVIDEND,
    SHAREHOLDER_STOCK_SPLIT,
    SHAREHOLDER_STOCK_ISSUE,
    SHAREHOLDER_STOCK_RETIRE,
    SHAREHOLDER_LIQUIDATE,

    // Parliamentary
    PARLIAMENT_NEW_LAW,
    PARLIAMENT_CHANGE_LAW,
    PARLIAMENT_REPEAL_LAW,
    PARLIAMENT_NO_CONFIDENCE,

    // Presidential
    SENATE_NEW_LAW,
    SENATE_CHANGE_LAW,
    SENATE_REPEAL_LAW,
    SENATE_IMPEACH_PRESIDENT,

    // Supreme Court
    SUPREME_COURT_IMPEACH_PRESIDENT,

    // Referendums
    REFERENDUM_CHANGE_NAME,

    // Federations
    FEDERATION_CHANGE_NAME,
    FEDERATION_NEW_MEMBER,
    FEDERATION_KICK_MEMBER,
    FEDERATION_CHANGE_CAPITAL;

    boolean isBoard() {
        switch(this) {
            case BOARD_BOND_ISSUE:
            case BOARD_CASH_DIVIDEND:
            case BOARD_STOCK_DIVIDEND:
            case BOARD_STOCK_ISSUE:
            case BOARD_STOCK_SPLIT:
            case BOARD_STOCK_RETIRE:
                return true;

        }

        return false;
    }

    boolean isShareholder() {
        switch (this) {
            case SHAREHOLDER_CASH_DIVIDEND:
            case SHAREHOLDER_FIRE_CEO:
            case SHAREHOLDER_CHANGE_NAME:
            case SHAREHOLDER_FIRE_DIRECTOR:
            case SHAREHOLDER_HIRE_CEO:
            case SHAREHOLDER_HIRE_DIRECTOR:
            case SHAREHOLDER_LIQUIDATE:
            case SHAREHOLDER_STOCK_DIVIDEND:
            case SHAREHOLDER_STOCK_ISSUE:
            case SHAREHOLDER_STOCK_SPLIT:
            case SHAREHOLDER_STOCK_RETIRE:
                return true;
        }

        return false;
    }

    boolean isSenate() {
        switch (this) {
            case SENATE_CHANGE_LAW:
            case SENATE_NEW_LAW:
            case SENATE_REPEAL_LAW:
            case SENATE_IMPEACH_PRESIDENT:
                return true;
        }

        return false;
    }

    boolean isParliament() {
        switch (this) {
            case PARLIAMENT_CHANGE_LAW:
            case PARLIAMENT_NEW_LAW:
            case PARLIAMENT_REPEAL_LAW:
            case PARLIAMENT_NO_CONFIDENCE:
                return true;
        }

        return false;
    }

    boolean isSupremeCourt() {
        switch (this) {
            case SUPREME_COURT_IMPEACH_PRESIDENT:
                return true;
        }

        return false;
    }

    boolean isReferendum() {
        switch (this) {
            case REFERENDUM_CHANGE_NAME:
                return true;
        }

        return false;
    }

    boolean isFederation() {
        switch (this) {
            case FEDERATION_CHANGE_CAPITAL:
            case FEDERATION_CHANGE_NAME:
            case FEDERATION_KICK_MEMBER:
            case FEDERATION_NEW_MEMBER:
                return true;
        }

        return false;
    }
}
