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
                return 5000;
            case AA:
                return 4500;
            case A:
                return 4000;
            case BBB:
                return 3000;
            case BB:
                return 2000;
            case B:
                return 1000;
            case CCC:
                return 700;
            case CC:
                return 500;
            case C:
                return 250;
            case D:
                return 0;
        }

        return 0;
    }
}
