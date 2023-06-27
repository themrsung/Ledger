package jbs.ledger.types.credit;

public enum CreditRating {
    AAA,
    AA,
    A,
    BBB,
    BB,
    B,
    CCC,
    CC,
    C,
    D;

    public static CreditRating fromScore(float score) {
        if (score >= AAA.toScore()) return AAA;
        else if (score >= AA.toScore()) return AA;
        else if (score >= A.toScore()) return A;
        else if (score >= BBB.toScore()) return BBB;
        else if (score >= BB.toScore()) return BB;
        else if (score >= B.toScore()) return B;
        else if (score >= CCC.toScore()) return CCC;
        else if (score >= CC.toScore()) return CC;
        else if (score >= C.toScore()) return C;
        else return D;
    }

    public float toScore() {
        switch (this) {
            case AAA:
                return 5000f;
            case AA:
                return 4500f;
            case A:
                return 4000f;
            case BBB:
                return 3000f;
            case BB:
                return 2000f;
            case B:
                return 1000f;
            case CCC:
                return 700f;
            case CC:
                return 500f;
            case C:
                return 250f;
            case D:
                return 0f;
        }

        return 0f;
    }

    public float getNoteDiscountRate() {
        switch (this) {
            case AAA:
                return 0.01f;
            case AA:
                return 0.025f;
            case A:
                return 0.05f;
            case BBB:
                return 0.1f;
            case BB:
                return 0.15f;
            case B:
                return 0.2f;
            case CCC:
                return 0.3f;
            case CC:
                return 0.5f;
            case C:
                return 0.7f;
            case D:
                return 0.99f;
        }

        return 0f;
    }
}
