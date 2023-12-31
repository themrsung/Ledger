package jbs.ledger.messenger;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.assetholders.corporations.finance.Bank;
import jbs.ledger.assetholders.foundations.Foundation;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.assetholders.trusts.Trust;
import jbs.ledger.classes.navigation.GpsEntry;
import jbs.ledger.classes.orders.OrderType;
import jbs.ledger.interfaces.banking.Account;
import jbs.ledger.interfaces.cards.Card;
import jbs.ledger.interfaces.common.Symbolic;
import jbs.ledger.interfaces.corporate.Corporate;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.interfaces.sovereignty.Sovereign;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.assets.basic.Stock;
import jbs.ledger.types.markets.MarketTickData;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Objects;

public final class LedgerPlayerMessenger {
    public static LedgerPlayerMessenger getInstance(Player player) {
        return new LedgerPlayerMessenger(player);
    }

    private LedgerPlayerMessenger(Player player) {
        this.player = player;
    }
    private final Player player;

    private void s(String msg) {
        player.sendRawMessage(msg.replaceAll("&", "§"));
    }

    public void custom(String msg) {
        s(msg);
    }

    public void unknownError() {
        s("알 수 없는 오류가 발생했습니다.");
    }
    public void insufficientArgs() {
        s("입력하신 항목이 부족합니다.");
    }
    public void insufficientPermissions() {
        s("권한이 부족합니다. 명령어를 실행한 명의자를 확인해주세요.");
    }
    public void invalidKeyword() {
        s("유효하지 않은 키워드입니다.");
    }

    public void insufficientCash() {
        s("현금이 부족합니다.");
    }

    public void assetholderNotFound() {
        s("경제주체를 찾을 수 없습니다.");
    }
    public void invalidTeleportDestination() {
        s("유효하지 않은 도착지입니다.");
    }
    public void teleportSucceeded() {
        s("텔레포트가 성공적으로 실행되었습니다.");
    }
    public void noMessagesReceived() {
        s("수신한 메시지가 없습니다.");
    }

    public void teleportRequestSent() {
        s("텔레포트 요청을 보냈습니다.");
    }
    public void teleportRequestReceived(Person sender) {
        s(sender.getName() + "에게 텔레포트 요청을 받았습니다. 수락하시려면 /y, 거절하시려면 /n을 입력해주세요.");
    }
    public void teleportRequestDeclined(Person sender) {
        s(sender.getName() + "에게 수신한 텔레포트 요청을 거부했습니다.");
    }
    public void noTeleportRequestsReceived() {
        s("수신한 텔레포트 요청이 없습니다.");
    }
    public void noTeleportRequestsSent() {
        s("발신한 텔레포트 요청이 없습니다.");
    }

    public void noAcceptableOfferReceived() {
        s("수신한 제안이 없습니다.");
    }
    public void commandOnlyExecutableByOneself() {
        s("본인 명의로만 실행 가능한 명령어입니다.");
    }

    public void invalidSymbol() {
        s("유효하지 않은 코드입니다.");
    }
    public void symbolTooLong() {
        s("코드가 너무 깁니다.");
    }
    public void nameTooLong() {
        s("이름이 너무 깁니다.");
    }
    public void commandOnlyExecutableByPlayer() {
        s("플레이어만 실행 가능한 명령입니다.");
    }
    public void commandOnlyExecutableByOrganizations() {
        s("조직 명의로만 실행 가능한 명령입니다.");
    }
    public void commandOnlyExecutableByNations() {
        s("국가만 실행 가능한 명령입니다.");
    }
    public void foundationCreated() {
        s("재단이 설립되었으며, 이사회 의장으로 등록되셨습니다.");
    }
    public void federationCreated() {
        s("연방국가가 설립되었습니다.");
    }
    public void nationCreated() {
        s("국가가 설립되었으며, 국가 원수로 등록되셨습니다.");
    }
    public void corporationCreated() {
        s("주식회사가 설립되었으며, 대표이사로 등록되셨습니다.");
    }
    public void trustCreated() {
        s("신탁 설립되었습니다.");
    }
    public void invalidNumber() {
        s("유효하지 않은 숫자입니다.");
    }
    public void symbolInUse() {
        s("고유코드가 사용중입니다.");
    }
    public void invalidMoney() {
        s("유효하지 않은 금액입니다.");
    }
    public void invalidQuantity() {
        s("유효하지 않은 수량입니다.");
    }

    public void memberNotInOrganization() {
        s("해당 조직에 소속되어있지 않습니다.");
    }
    public void memberCannotJoinThisOrganization() {
        s("해당 조직에 소속될 수 있는 회원이 아닙니다.");
    }

    public void memberKickedFromOrganization() {
        s("조직에서 추방되었습니다.");
    }

    public void balanceInformationHeader() {
        s("잔고를 조회합니다.");
    }

    public void onlyPlayersCanJoinThisOrganization() {
        s("플레이어만 가입 가능한 조직입니다.");
    }

    public void onlyNationsCanJoinThisOrganization() {
        s("국만 가입 가능한 조직입니다.");
    }

    public void invitedCannotJoinNations() {
        s("국가에 소속될 수 없는 대상입니다.");
    }

    public void inviteSuccessful() {
        s("초대가 발송되었습니다.");
    }

    public void marketNotFound() {
        s("시장을 찾을 수 없습니다.");
    }

    public void orderSubmitted(OrderType type) {
        s(type.toString() + " 주문이 접수되었습니다.");
    }


    public void trusteeSet(Assetholder trustee) {
        s(trustee.getName() + "이 수탁자로 지정되었습니다.");
    }

    public void balanceInformation(@Nullable Cash cash) {
        if (cash == null) return;
        s("- " + cash.format());
    }

    public void cashSent(Assetholder recipient, Cash payment) {
        s(recipient.getName() + "에게 " + payment.format() + "를 보냈습니다.");
    }

    public void creditRating(Assetholder holder) {
        s(holder.getName() + "의 신용등급은 " + holder.getCreditRating().toString() + "입니다.");
    }

    public void addressSet() {
        s("주소지가 설정되었습니다.");
    }

    public void addressTooCloseToAnotherAddress() {
        s("다른 주소지와 너무 가깝습니다.");
    }

    public void addressDeleted() {
        s("주소지가 삭제되었습니다.");
    }

    public void gpsEntryAdded() {
        s("현재 위치가 GPS에 등록되었습니다.");
    }

    public void gpsEntryRemoved() {
        s("GPS에서 주소를 삭제했습니다.");
    }

    public void gpsEntryNotFound() {
        s("주소지를 찾을 수 없습니다.");
    }

    public void gpsEntryListHeader() {
        s("GPS에 등록된 주소지 목록을 조회합니다.");
    }

    public void gpsEntryInformation(GpsEntry entry) {
        s("- " + entry.getName() + ": " + Objects.requireNonNull(entry.getAddress().getWorld()).getName() + ", x: " + entry.getAddress().getBlockY() + ", y: " + entry.getAddress().getBlockZ() + ", z: " + entry.getAddress().getBlockX());
    }

    public void commandRequiresPremiumStatus() {
        s("프리미엄 유저만 이용할 수 있는 명령어입니다.");
    }

    public void premiumInformation(Person p) {
        s(p.isPremium() + "의 프리미엄 정보");
        s("프리미엄 여부: " + p.isPremium());
        s("프리미엄 만기일:" + (p.getPremiumExpiration() != null ? DateFormat.getDateInstance().format(p.getPremiumExpiration()) : "무기한"));
    }

    public void premiumDaysAdded(Person p) {
        s(p.getName() + "의 프리미엄 기간을 추가했습니다.");
    }

    public void lifetimePremiumSet(Person p) {
        s(p.getName() + "를 평생 프리미엄 유저로 설정했습니다.");
    }

    public void premiumDaysRemoved(Person p) {
        s(p.getName() + "의 프리미엄 기간을 제거했습니다.");
    }

    public void premiumUnset(Person p) {
        s(p.getName() + "의 프리미엄 상태를 박탈했습니다.");
    }

    public void marketPriceInformation(Market<?> m) {
        s("&l" + m.getSymbol() + " 가격정보");
        s("거래소: " + m.getExchange().getSearchTag() + " / 거래통화: " + m.getCurrency());
        s("현재가: " + new Cash(m.getCurrency(), m.getPrice()).format() + " / 거래량: " + NumberFormat.getInstance().format(m.getVolume()));

        ArrayList<MarketTickData> buyTicks = m.getBuyTicks();
        ArrayList<MarketTickData> sellTicks = m.getSellTicks();

        if (buyTicks.size() > 3) {
            buyTicks.subList(3, buyTicks.size()).clear();
        }

        if (sellTicks.size() > 3) {
            sellTicks.subList(3, sellTicks.size()).clear();
        }

        sellTicks.sort((s1, s2) -> Double.compare(s2.getPrice(), s1.getPrice()));

        for (MarketTickData sellTick : sellTicks) {
            s("&9" + new Cash(m.getCurrency(), sellTick.getPrice()).format() + "&r &f" + NumberFormat.getIntegerInstance().format(sellTick.getQuantity()));
        }

        for (MarketTickData buyTick : buyTicks) {
            s("&c" + new Cash(m.getCurrency(), buyTick.getPrice()).format() + "&r &f" + NumberFormat.getIntegerInstance().format(buyTick.getQuantity()));
        }
    }

    public void playerPardoned() {
        s("유저가 사면되었습니다.");
    }

    public void playerNotPardonable() {
        s("유저에게 적용 중인 제재가 없습니다.");
    }

    public void playerNotCitizen() {
        s("유저가 해당 국가의 시민이 아닙니다.");
    }

    public void playerAlreadyBanned() {
        s("유저가 이미 밴되어있습니다.");
    }

    public void playerNotBanned() {
        s("유저가 밴되어있지 않습니다.");
    }

    public void playerBanned() {
        s("유저가 밴되었습니다.");
    }

    public void playerUnBanned() {
        s("유저에 대한 밴에 해제되었습니다.");
    }

    public void playerAlreadyMuted() {
        s("유저가 이미 뮤트되어있습니다.");
    }

    public void playerNotMuted() {
        s("유저가 뮤트되어있지 않습니다.");
    }

    public void playerMuted() {
        s("유저가 뮤트되었습니다.");
    }

    public void playerUnMuted() {
        s("유저에 대한 뮤트가 해제되었습니다.");
    }

    public void playerNotOnline() {
        s("플레이어가 접속해있지 않습니다.");
    }

    public void playerKicked() {
        s("플레이어가 킥되었습니다.");
    }

    public void meetingNotFound() {
        s("투표 가능한 안건을 찾지 못했습니다.");
    }

    public void noVotesRemaining() {
        s("투표 가능한 표가 남아있지 않습니다.");
    }

    public void votesCast() {
        s("의결을 완료했습니다.");
    }

    public void unsupportedMeetingType() {
        s("해당 사안을 상정할 수 있는 기관이 아닙니다.");
    }

    public void noRightsToProposeMeeting() {
        s("해당 사안을 발의할 권한이 없습니다.");
    }

    public void ceoPositionNotVacant() {
        s("CEO가 공석이 아닙니다.");
    }

    public void sameMeetingAlreadyOpen() {
        s("중복상정이 불가능한 안건입니다.");
    }

    public void ceoPositionAlreadyVacant() {
        s("CEO가 이미 공석입니다.");
    }

    public void personAlreadyDirector() {
        s("이미 이사회에 소속되어있습니다.");
    }

    public void personNotDirector() {
        s("이사회에 소속되어있지 않습니다.");
    }

    public void meetingProposed() {
        s("안건이 상정되었습니다.");
    }

    public void actorNotDirectorship() {
        s("이사회가 없는 조직입니다.");
    }

    public void directorsListHeader() {
        s("이사회 구성원");
    }

    public void directorsListEntry(Person p) {
        s("- " + p.getName());
    }

    public void actorNotOrganization() {
        s("회원이 없는 조직입니다.");
    }

    public void membersListHeader() {
        s("회원 목록");
    }

    public void membersListEntry(Assetholder a) {
        s("- " + a.getName());
    }

    public void netWorthLeaderboard(ArrayList<Assetholder> sortedList, String currency, int cutoff, LedgerState state) {
        s("순자산 상위 목록 (표시통화: " + currency + ")");
        for (int i = 0; i < cutoff; i++) {
            Assetholder a = sortedList.get(i);
            double netWorth = a.getNetWorth(state, currency);

            s(NumberFormat.getIntegerInstance().format(i + 1) + ". " + a.getName() + ": " + NumberFormat.getInstance().format(netWorth));
        }
    }
    public void netWorthMyRank(int rankIndex) {
        String rankString;

        if (rankIndex != -1) {
            rankString = NumberFormat.getIntegerInstance().format(rankIndex + 1) + "위";
        } else {
            rankString = "없음";
        }

        s("내 순위: " + rankString);
    }

    public void listHeader() {
        s("조회를 시작힙니다.");
    }

    public void assetholderListEntry(Assetholder a) {
        if (a instanceof Symbolic) {
            s("- " + a.getName() + " (" + ((Symbolic) a).getSymbol() + ")");
        } else {
            s("- " + a.getName());
        }
    }

    public void marketListEntry(Market<?> m) {
        s("- "+ m.getSymbol() + " (" + "유형:" + m.getUnitAsset().getType() + " / 거래통화: " + m.getCurrency() + ")");
    }

    public void playerListEntry(Person p) {
        s("- " + p.getName());
    }

    public void corporateListEntry(Corporate c) {
        if (c instanceof Corporation) {
            s("- " + ((Corporation) c).getName() + " (" + c.getSymbol() + ")");
        } else {
            s("- " + c.getSymbol());
        }
    }

    public void foundationListEntry(Foundation f) {
        s("- " + f.getName() + "( " + f.getSymbol() + ")");
    }

    public void sovereignListEntry(Sovereign s) {
        if (s instanceof Nation) {
            s("- " + ((Nation) s).getName() + " (" + s.getSymbol() + ")");
        } else if (s instanceof Corporation) {
            s("- " + ((Corporation) s).getName() + " (" + s.getSymbol() + ")");
        } else {
            s("- " + s.getSymbol());
        }
    }

    public void trustListEntry(Trust t) {
        s("- " + t.getName() + "( " + t.getSymbol() + ")");
    }

    public void assetholderPropertySet() {
        s("설정이 완료되었습니다.");
    }

    public void assetList(Assetholder h, String denotation, LedgerState state) {
        s(h.getName() + "의 자산 (표시통화: " + denotation + ")");
        s("신용등급: " + h.getCreditRating().toString());
        s("자산총계: " + NumberFormat.getInstance().format(h.getAssetValue(state, denotation)));
    }

    public void liabilitiesList(Assetholder h, String denotation, LedgerState state) {
        s(h.getName() + "의 부채 (표시통화: " + denotation + ")");
        s("신용등급: " + h.getCreditRating().toString());
        s("부채총계: " + NumberFormat.getInstance().format(h.getLiabilityValue(state, denotation)));
    }

    public void netWorthList(Assetholder h, String denotation, LedgerState state) {
        s(h.getName() + "의 재무상태 (표시통화: " + denotation + ")");
        s("신용등급: " + h.getCreditRating().toString());
        s("자산총계: " + NumberFormat.getInstance().format(h.getAssetValue(state, denotation)));
        s("부채총계: " + NumberFormat.getInstance().format(h.getLiabilityValue(state, denotation)));
        s("자본총계: " + NumberFormat.getInstance().format(h.getNetWorth(state, denotation)));
    }

    public void featureUnderDevelopment() {
        s("현재 개발중인 기능입니다.");
    }

    public void bankDepositSuccessful() {
        s("입금이 처리되었습니다.");
    }
    public void bankWithdrawalSuccessful() {
        s("출금이 처리되었습니다.");
    }

    public void bankBalanceListHeader() {
        s("은행 잔고를 조회합니다.");
    }

    public void bankBalanceInformation(Bank bank, Account<Cash> account) {
        s(bank.getName() + "에 예탁된 잔고: " + account.getContent().format());
    }

    public void stockNotFound() {
        s("주식을 찾을 수 없습니다.");
    }
    public void stockPortfolioHeader() {
        s("보유주식을 조회합니다.");
    }
    public void stockInformation(Stock s) {
        s("- " + s.format());
    }

    public void cardCancelled() {
        s("카드가 발급취소되었습니다.");
    }

    public void cardIssued() {
        s("카드가 발행되었습니다. 고객에게 전달해주세요.");
    }

    public void cardActivated() {
        s("카드가 활성화되었습니다.");
    }

    public void cardInformation(Card card) {
        s("카드번호: " + card.getSearchTag());
        s("명의자: " + card.getHolder().getSearchTag() + " / 카드사: " + card.getIssuer().getSymbol());
        s("잔여한도: " + new Cash(card.getCurrency(), card.getPayable()).format());
    }
    public void executorNotCardIssuer() {
        s("카드를 발급할 수 없습니다. 카드사나 은행의 명의로 명령어를 실행해주세요.");
    }

    public void bankAccountNotFound() {
        s("은행 계좌를 찾지 못했습니다.");
    }

    public void notHoldingCard() {
        s("카드를 들고 있지 않습니다.");
    }

    public void invalidCard() {
        s("유효한 카드가 아닙니다.");
    }

    public void cardAlreadyActive() {
        s("이미 활성화된 카드입니다.");
    }

}
