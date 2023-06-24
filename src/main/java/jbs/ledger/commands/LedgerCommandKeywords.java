package jbs.ledger.commands;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public abstract class LedgerCommandKeywords {
    public static List<String> PAY = Arrays.asList("p", "pay", "송금", "이체");
    public static  List<String> CREATE = Arrays.asList("new", "create", "신규", "추가", "생성");
    public static  List<String> BALANCE = Arrays.asList("bal", "balance", "m", "money", "cash", "잔고", "현금");
    public static List<String> STOCKS = Arrays.asList("s", "stock", "stocks", "share", "shares", "주식");
    public static List<String> BONDS = Arrays.asList("bond", "bonds", "채권");
    public static List<String> FUTURES = Arrays.asList("future", "futures", "선물");
    public static List<String> FORWARDS = Arrays.asList("contract", "contracts", "forward", "forwards", "선도", "계약");
    public static  List<String> INVITE = Arrays.asList("invite", "초대", "h", "hire", "고용");
    public static  List<String> KICK = Arrays.asList("k", "kick", "추방", "f", "fire", "해고", "해임");
    public static  List<String> PUNISH = Arrays.asList("punish", "재제", "형벌", "선고");
    public static  List<String> YES = Arrays.asList("y", "yes", "a", "accept", "agree", "동의", "수락", "예");
    public static  List<String> NO = Arrays.asList("n", "no", "d", "deny", "decline", "거부", "거절", "아니오");
    public static  List<String> LIST = Arrays.asList("l", "li", "ls", "list", "목록", "search", "검색");
    public static  List<String> MEMBERS = Arrays.asList("e", "em", "employ", "employment", "고용", "직원", "m", "member", "membership", "members", "멤버", "citizen", "citizenship", "시민", "시민권");
    public static  List<String> DIRECTORS = Arrays.asList("d", "director", "board", "이사", "이사회");
    public static  List<String> NET_WORTH = Arrays.asList("nw", "worth", "networth", "순자산", "equity", "자본", "capital", "자기자본");
    public static List<String> ASSETS = Arrays.asList("ast", "asset", "assets", "자산");
    public static List<String> LIABILITIES = Arrays.asList("debt", "liability", "liabilities", "부채", "빚");
    public static List<String> CARDS = Arrays.asList("card", "ccard", "dcard", "ccards", "dcards", "creditcard", "debitcard", "creditcards", "debitcards", "cards", "카드", "신용카드", "체크카드");
    public static List<String> VOTE = Arrays.asList("v", "vote", "votes", "voting", "elect", "election", "elections", "meeting", "투표", "선거", "의결", "주총", "주주총회", "총회");
    public static List<String> HOME = Arrays.asList("home", "office", "hq", "address", "house", "집", "홈", "사무실");
    public static List<String> SET_HOME = Arrays.asList("sh", "sethome", "newhome", "setaddress", "changeaddress", "newaddress", "changehome", "집설정", "집변경", "주소지설정", "주소지변경");
    public static List<String> DELETE_HOME = Arrays.asList("dh", "delhome", "deletehome", "deladdress", "deleteaddress", "집삭제", "주소지삭제");
    public static List<String> MANAGE = Arrays.asList("mg", "mgm", "manage", "modify", "setting", "settings", "설정", "관리");
    public static List<String> BANK = Arrays.asList("bn", "bk", "bnk", "bank", "banks", "banking", "은행");
    public static List<String> CANCEL = Arrays.asList("c", "cn", "can", "cancel", "취소");
    public static List<String> SUDO = Arrays.asList("d", "deputy", "su", "sud", "sudo", "as", "ex", "exe", "execute", "대변", "대신");

    public enum AcceptableAction {
        TELEPORT,
        MEMBERSHIP_OFFER,
        DIRECTOR_OFFER,
        REPRESENTATIVE_OFFER,
        CONTRACT_OFFER;

        @Nullable
        public static AcceptableAction get(String action) {
            if (LedgerCommandKeywords.TELEPORT.contains(action)) return TELEPORT;
            else if (LedgerCommandKeywords.MEMBERSHIP.contains(action)) return MEMBERSHIP_OFFER;
            else if (LedgerCommandKeywords.DIRECTOR.contains(action)) return DIRECTOR_OFFER;
            else if (LedgerCommandKeywords.REPRESENTATIVE.contains(action)) return REPRESENTATIVE_OFFER;
            else if (LedgerCommandKeywords.CONTRACT.contains(action)) return CONTRACT_OFFER;
            return null;
        }

        public boolean isCancellable() {
            switch (this) {
                case REPRESENTATIVE_OFFER:
                    return false;
            }

            return true;
        }
    }


    public static List<String> TELEPORT = Arrays.asList("tp", "tpa", "tpask", "tpr", "tprequest", "teleport", "teleportrequest", "티피", "티피에이", "티피요청", "텔레포트요청", "텔레포트");
    public static List<String> MEMBERSHIP = Arrays.asList("m", "mem", "member", "membership", "e", "em", "employ", "employment", "employer", "employee", "w", "work", "j", "job", "c", "cit", "citizen", "citizenship", "회원", "회원권", "시민", "시민권", "직원", "구인", "구직");
    public static List<String> DIRECTOR = Arrays.asList("d", "dr", "dir", "director", "directors", "b", "brd", "board", "이사", "이사회", "임원");
    public static List<String> REPRESENTATIVE = Arrays.asList("r", "rep", "representative", "p", "pr", "president", "ceo", "pm", "primeminister", "chancellor" ,"대표", "대표이사", "대통령", "총리", "총통");
    public static List<String> CONTRACT = Arrays.asList("con", "contract", "contracts", "n", "note", "f", "forward", "future", "futures", "bond", "debt", "trade", "transaction", "계약", "선도", "선물", "선도계약", "선물계약", "거래");
}
