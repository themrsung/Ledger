package jbs.ledger.interfaces.organization;

import jbs.ledger.interfaces.common.Unique;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * An organization of members.
 * @param <M> Which type of entity can join the organization.
 */
public interface Organization<M extends Unique> extends Representable<M> {
    ArrayList<M> getMembers();
    @Nullable
    default M getMember(UUID uniqueId) {
        for (M m : getMembers()) {
            if (m.getUniqueId().equals(uniqueId)) {
                return m;
            }
        }

        return null;
    }

    void addMember(M member);
    boolean removeMember(M member);
}
