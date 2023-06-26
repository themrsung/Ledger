package jbs.ledger.commands.actions.create;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.AssetholderType;
import jbs.ledger.classes.meetings.MeetingType;
import jbs.ledger.commands.LedgerCommandAutoCompleter;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.interfaces.organization.Electorate;

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
            results.addAll(LedgerCommandKeywords.VOTE);
        } else if (args.length < 3) {
            String action = args[1].toLowerCase();
            if (LedgerCommandKeywords.VOTE.contains(action)) {
                for (Assetholder a : getState().getAssetholders()) {
                    results.add(a.getSearchTag());
                }
            } else {
                results.add("조직의 고유 코드를 입력해주세요. (알파벳 3글자 이하, 대소문자 구분 없음");
            }
        } else if (args.length < 4) {
            String action = args[1].toLowerCase();
            if (LedgerCommandKeywords.VOTE.contains(action)) {
                Assetholder a = getState().getAssetholder(args[2], true, true);
                if (a == null) {
                    results.add("조직을 찾을 수 없습니다.");
                } else {
                    if (a.getType().isCorporation()) {
                        results.add(MeetingType.BOARD_CASH_DIVIDEND.toString());
                        results.add(MeetingType.BOARD_STOCK_DIVIDEND.toString());
                        results.add(MeetingType.BOARD_STOCK_SPLIT.toString());
                        results.add(MeetingType.BOARD_STOCK_ISSUE.toString());
                        results.add(MeetingType.BOARD_STOCK_RETIRE.toString());
                        results.add(MeetingType.BOARD_BOND_ISSUE.toString());
                        results.add(MeetingType.SHAREHOLDER_HIRE_CEO.toString());
                        results.add(MeetingType.SHAREHOLDER_FIRE_CEO.toString());
                        results.add(MeetingType.SHAREHOLDER_HIRE_DIRECTOR.toString());
                        results.add(MeetingType.SHAREHOLDER_FIRE_DIRECTOR.toString());
                        results.add(MeetingType.SHAREHOLDER_CHANGE_NAME.toString());
                        results.add(MeetingType.SHAREHOLDER_LIQUIDATE.toString());
                    } else if (a.getType() == AssetholderType.PRESIDENTIAL_REPUBLIC) {
                        results.add(MeetingType.SENATE_NEW_LAW.toString());
                        results.add(MeetingType.SENATE_CHANGE_LAW.toString());
                        results.add(MeetingType.SENATE_REPEAL_LAW.toString());
                        results.add(MeetingType.SENATE_IMPEACH_PRESIDENT.toString());
                        results.add(MeetingType.REFERENDUM_CHANGE_NAME.toString());
                        results.add(MeetingType.ELECTION_PRESIDENTIAL.toString());
                        results.add(MeetingType.ELECTION_GENERAL.toString());
                    } else if (a.getType() == AssetholderType.PARLIAMENTARY_REPUBLIC) {
                        results.add(MeetingType.PARLIAMENT_NO_CONFIDENCE.toString());
                        results.add(MeetingType.PARLIAMENT_NEW_LAW.toString());
                        results.add(MeetingType.PARLIAMENT_CHANGE_LAW.toString());
                        results.add(MeetingType.PARLIAMENT_REPEAL_LAW.toString());
                        results.add(MeetingType.REFERENDUM_CHANGE_NAME.toString());
                        results.add(MeetingType.ELECTION_GENERAL.toString());
                    } else if (a.getType() == AssetholderType.FEDERATION) {
                        results.add(MeetingType.FEDERATION_CHANGE_NAME.toString());
                        results.add(MeetingType.FEDERATION_NEW_MEMBER.toString());
                        results.add(MeetingType.FEDERATION_KICK_MEMBER.toString());
                        results.add(MeetingType.FEDERATION_CHANGE_CAPITAL.toString());
                    }
                }
            } else {
                results.add("조직의 이름을 입력해주세요. 띄어쓰기는 불가능합니다.");
            }
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
