package jbs.ledger.interfaces.organization;

import jbs.ledger.assetholders.Assetholder;

import javax.annotation.Nullable;
import java.util.ArrayList;

public interface Trusteeship extends Organization<Assetholder> {
    Assetholder getTrustee();
    @Nullable
    Assetholder getBeneficiary();
    @Nullable
    Assetholder getGrantor();

    void setTrustee(@Nullable Assetholder trustee);
    void setBeneficiary(@Nullable Assetholder beneficiary);

    @Override
    default void setRepresentative(@Nullable Assetholder representative) {
        setTrustee(representative);
    }

    @Nullable
    @Override
    default Assetholder getRepresentative() {
        return getTrustee();
    }

    @Override
    default ArrayList<Assetholder> getMembers() {
        ArrayList<Assetholder> members = new ArrayList<>();

        members.add(getGrantor());
        members.add(getTrustee());
        members.add(getBeneficiary());

        return members;
    }

    /**
     * Not supported for trusts
     * @param member Does nothing
     */
    @Override
    default void addMember(Assetholder member) {

    }

    /**
     * Not supported for trusts
     * @param member Does nothing
     * @return Returns false
     */
    @Override
    default boolean removeMember(Assetholder member) {
        return false;
    }
}
