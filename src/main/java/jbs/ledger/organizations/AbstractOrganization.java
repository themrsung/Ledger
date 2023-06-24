package jbs.ledger.organizations;

import jbs.ledger.interfaces.common.Unique;
import jbs.ledger.interfaces.organization.Organization;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public class AbstractOrganization<M extends Unique> implements Organization<M> {
    public AbstractOrganization(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.members = new ArrayList<>();
        this.representative = null;
    }

    public AbstractOrganization(AbstractOrganization<M> copy) {
        this.uniqueId = copy.uniqueId;
        this.members = copy.members;
        this.representative = copy.representative;
    }

    public AbstractOrganization() {
        this.uniqueId = null;
        this.members = new ArrayList<>();
        this.representative = null;
    }

    private final UUID uniqueId;
    private final ArrayList<M> members;
    @Nullable
    private M representative;

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public ArrayList<M> getMembers() {
        return new ArrayList<>(members);
    }

    @Override
    public void addMember(M member) {
        members.add(member);
    }

    @Override
    public boolean removeMember(M member) {
        return members.remove(member);
    }

    @Nullable
    @Override
    public M getRepresentative() {
        return representative;
    }

    @Override
    public void setRepresentative(@Nullable M representative) {
        this.representative = representative;
    }
}
