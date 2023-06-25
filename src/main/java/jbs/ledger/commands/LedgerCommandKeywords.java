package jbs.ledger.commands;

import javax.annotation.Nullable;
import java.util.ArrayList;
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
    public static  List<String> ACCEPT = Arrays.asList("y", "yes", "a", "accept", "agree", "동의", "수락", "예");
    public static  List<String> DENY = Arrays.asList("n", "no", "d", "deny", "decline", "거부", "거절", "아니오");
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
    public static List<String> OPTIONS = Arrays.asList("o", "option", "options", "옵션");
    public static List<String> CREDIT_RATING = Arrays.asList("cs", "cr", "cscore", "crank", "crating", "creditrating", "creditscore", "신용등급", "신용점수");

    // ADD | DELETE
    public static List<String> ADD = Arrays.asList("a", "add", "s", "set", "추가", "지정", "설정", "신규");
    public static List<String> DELETE = Arrays.asList("r", "remove", "rem", "d", "del", "delete", "삭제", "제거");

    // ACCEPT | DENY | CANCEL
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

    // CREATE
    public static List<String> COMPANY = Arrays.asList("c", "comp", "company", "corp", "corporation", "inc", "회사", "기업", "주식회사");
    public static List<String> CONSTRUCTION_COMPANY = Arrays.asList("cc", "contractor", "construction", "constructioncompany", "건설", "건설사", "건설회사");
    public static List<String> MANUFACTURER = Arrays.asList("mf", "mfc", "manufacture", "manufacturer", "fac", "factory", "제조사", "제조", "공장");
    public static List<String> MERCHANT = Arrays.asList("mr", "mrc", "mc", "merchant", "상업", "유통", "소매", "소매사","소매회사");
    public static List<String> PUBLISHER = Arrays.asList("p", "pb", "pu", "publish", "publisher", "publishing", "출판", "출판사");
    public static List<String> DISTILLERY = Arrays.asList("d", "dis", "ds", "distill", "distillery", "증류소", "포션공장");
    public static List<String> CREDIT_CARD_COMPANY = Arrays.asList("ccc", "ccard", "cardcomp", "ccardcomp", "ccardcompany", "cardcompany", "creditcard", "creditcardcompany", "카드사", "신용카드사");
    public static List<String> FOREX = Arrays.asList("fx", "frx", "forex", "foreignex", "foreignexchange", "외환시장", "외환거래소");
    public static List<String> FUTURES_EXCHANGE = Arrays.asList("fex", "future", "futures", "futuresexchange", "futurexex", "선물시장", "선물거래소");
    public static List<String> SECURITIES_EXCHANGE = Arrays.asList("sx", "sex", "stockex", "securitiesex", "securityex", "stockexchange", "securitiesexchange", "증권시장", "주식시장", "증권거래소", "주식거래소");
    public static List<String> LAW_FIRM = Arrays.asList("lf", "law", "lawfirm", "lfirm", "법무법인", "로펌");
    public static List<String> PRIVATE_MILITARY = Arrays.asList("pmc", "military", "privatemilitary", "용병", "용병회사", "민간군사기업");
    public static List<String> CORPORATIONS() {
        List<String> corps = new ArrayList<>();

        corps.addAll(COMPANY);
        corps.addAll(CONSTRUCTION_COMPANY);
        corps.addAll(MANUFACTURER);
        corps.addAll(MERCHANT);
        corps.addAll(PUBLISHER);
        corps.addAll(DISTILLERY);
        corps.addAll(BANK);
        corps.addAll(CREDIT_CARD_COMPANY);
        corps.addAll(FOREX);
        corps.addAll(FUTURES_EXCHANGE);
        corps.addAll(SECURITIES_EXCHANGE);
        corps.addAll(LAW_FIRM);
        corps.addAll(PRIVATE_MILITARY);

        return corps;
    }
    public static List<String> FOUNDATION = Arrays.asList("fd", "fnd", "foundation", "재단", "재단법인");
    public static List<String> FOUNDATIONS() {
        return FOUNDATION;
    }
    public static List<String> PRESIDENTIAL_REPUBLIC = Arrays.asList("rpr", "reppr", "presidency", "presidentialrepublic", "대통령제", "공화국대통령제", "대통령제공화국");
    public static List<String> PARLIAMENTARY_REPUBLIC = Arrays.asList("rpa", "reppa", "parliamentary", "parliament", "parliamentaryrepublic", "의원내각제", "공화국의원내각제", "내각제", "공화국내각제", "내각제공화국", "의원내각제공화국");
    public static List<String> PRINCIPALITY = Arrays.asList("pnp", "prin", "principality", "공국", "군주제", "전제군주제");
    public static List<String> FEDERATION = Arrays.asList("fed", "federation", "연발", "연방국", "연방국가");
    public static List<String> NATIONS() {
        List<String> nations = new ArrayList<>();

        nations.addAll(PRESIDENTIAL_REPUBLIC);
        nations.addAll(PARLIAMENTARY_REPUBLIC);
        nations.addAll(PRINCIPALITY);
        nations.addAll(FEDERATION);

        return nations;
    }
    public static List<String> INVESTMENT_TRUST = Arrays.asList("it", "itrust", "invtrust", "investtrust", "investmenttrust", "투자신탁");
    public static List<String> REAL_ESTATE_TRUST = Arrays.asList("ret", "reit", "reits", "realestate", "realestatetrust", "realestateinvestmenttrust", "부동산신탁", "부동산투자신탁", "리츠");
    public static List<String> TRUSTS() {
        List<String> trusts = new ArrayList<>();

        trusts.addAll(INVESTMENT_TRUST);
        trusts.addAll(REAL_ESTATE_TRUST);

        return trusts;
    }

}
