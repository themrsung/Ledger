package jbs.ledger.commands.actions.invite;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.assetholders.foundations.Foundation;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.federations.Federation;
import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.assetholders.trusts.Trust;
import jbs.ledger.commands.LedgerCommandAutoCompleter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class InviteCommandCompleter extends LedgerCommandAutoCompleter {
    public InviteCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length < 2) {
            if (getActor() instanceof Corporation) {
                Corporation corp = (Corporation) getActor();
                ArrayList<Person> people = getState().getPeople();
                people.removeIf(p -> corp.getMembers().contains(p));

                for (Person p : people) {
                    list.add(p.getSearchTag());
                }
            } else if (getActor() instanceof Nation) {
                Nation nation = (Nation) getActor();
                ArrayList<Person> people = getState().getPeople();
                people.removeIf(p -> nation.getMembers().contains(p));

                for (Person p : people) {
                    list.add(p.getSearchTag());
                }
            } else if (getActor() instanceof Trust) {
                Trust trust = (Trust) getActor();
                ArrayList<Assetholder> holders = getState().getAssetholders();
                holders.removeIf(h -> trust.getMembers().contains(h));

                for (Assetholder h : holders) {
                    list.add(h.getSearchTag());
                }
            } else if (getActor() instanceof Foundation) {
                Foundation foundation = (Foundation) getActor();
                ArrayList<Person> people = getState().getPeople();
                people.removeIf(p -> foundation.getBoard().getMembers().contains(p));

                for (Person p : people) {
                    list.add(p.getSearchTag());
                }
            } else if (getActor() instanceof Federation) {
                Federation fed = (Federation) getActor();
                ArrayList<Nation> nations = getState().getNations();
                nations.removeIf(n -> fed.getMembers().contains(n));

                for (Nation n : nations) {
                    list.add(n.getSearchTag());
                }
            } else if (getActor() instanceof Person) {
                list.add("초대 명령어는 조직 명의로 실행해야 합니다. /sudo를 이용해주세요.");
            }
        } else if (args.length < 3) {
            if (getActor() instanceof Corporation) {
                list.add(args[1] + "을 직원으로 초대합니다.");
            } else if (getActor() instanceof Foundation) {
                list.add(args[1] + "을 이사회에 초대합니다.");
            } else if (getActor() instanceof Nation) {
                list.add(args[1] + "을 국가 구성원으로 초대합니다.");
            } else if (getActor() instanceof Trust) {
                list.add("신탁의 수탁자로 지정합니다.");
            } else if (getActor() instanceof Federation) {
                list.add(args[1] + "을 연방에 초대합니다.");
            }
        }

        return list;
    }
}
