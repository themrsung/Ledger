package jbs.ledger.commands.actions.create;

import jbs.ledger.Ledger;
import jbs.ledger.commands.LedgerCommandAutoCompleter;
import jbs.ledger.commands.LedgerCommandKeywords;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class CreateCommandCompleter extends LedgerCommandAutoCompleter {
    public CreateCommandCompleter(Ledger ledger) {
        super(ledger);
    }

    @Override
    protected List<String> onLedgerTabComplete(@Nonnull String command, @Nonnull String[] args) {
        List<String> results = new ArrayList<>();

        if (args.length < 2) {
            results.addAll(LedgerCommandKeywords.COMPANY);
            results.addAll(LedgerCommandKeywords.CONSTRUCTION_COMPANY);
            results.addAll(LedgerCommandKeywords.MANUFACTURER);
            results.addAll(LedgerCommandKeywords.MERCHANT);
            results.addAll(LedgerCommandKeywords.PUBLISHER);
            results.addAll(LedgerCommandKeywords.DISTILLERY);
            results.addAll(LedgerCommandKeywords.BANK);
            results.addAll(LedgerCommandKeywords.CREDIT_CARD_COMPANY);
            results.addAll(LedgerCommandKeywords.FOREX);
            results.addAll(LedgerCommandKeywords.FUTURES_EXCHANGE);
            results.addAll(LedgerCommandKeywords.SECURITIES_EXCHANGE);
            results.addAll(LedgerCommandKeywords.LAW_FIRM);
            results.addAll(LedgerCommandKeywords.PRIVATE_MILITARY);
            results.addAll(LedgerCommandKeywords.FOUNDATION);
            results.addAll(LedgerCommandKeywords.PRESIDENTIAL_REPUBLIC);
            results.addAll(LedgerCommandKeywords.PARLIAMENTARY_REPUBLIC);
            results.addAll(LedgerCommandKeywords.PRINCIPALITY);
            results.addAll(LedgerCommandKeywords.FEDERATION);
            results.addAll(LedgerCommandKeywords.INVESTMENT_TRUST);
            results.addAll(LedgerCommandKeywords.REAL_ESTATE_TRUST);
        } else if (args.length < 3) {
            results.add("조직의 고유 코드를 입력해주세요. (알파벳 3글자 이하, 대소문자 구분 없음");
        } else if (args.length < 4) {
            results.add("조직의 이름을 입력해주세요. 띄어쓰기는 불가능합니다.");
        } else if (args.length < 5) {
            results.add("조직의 자본금을 입력해주세요. 예: CR300만 -> 3,000,000 크레딧");
        } else if (args.length < 6) {
            String type = args[0].toLowerCase();
            if (LedgerCommandKeywords.CORPORATIONS().contains(type)) {
                results.add("발행할 주식수를 입력해주세요. 1주 이상이어야 합니다.");
            } else if (LedgerCommandKeywords.FOUNDATIONS().contains(type) || LedgerCommandKeywords.NATIONS().contains(type)) {
                results.add("필요한 항목을 전부 입력했습니다.");
            } else if (LedgerCommandKeywords.TRUSTS().contains(type)) {
                results.add("수탁자를 입력해주세요. 수탁자는 신탁의 재산을 관리할 수 있습니다.");
            }
        } else if (args.length < 7) {
            String type = args[0].toLowerCase();

            if (LedgerCommandKeywords.CORPORATIONS().contains(type)) {
                results.add("필요한 항목을 전부 입력했습니다.");
            } else if (LedgerCommandKeywords.TRUSTS().contains(type)) {
                results.add("수익자를 입력해주세요. 수익자는 신탁의 이익금을 배당받습니다.");
            }
        } else if (args.length < 8) {
            String type = args[0].toLowerCase();

            if (LedgerCommandKeywords.TRUSTS().contains(type)) {
                results.add("필요한 항목을 전부 입력했습니다.");
            }
        }

        return results;
    }
}
