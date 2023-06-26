package jbs.ledger.messenger;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.navigation.GpsEntry;
import jbs.ledger.classes.orders.OrderType;
import jbs.ledger.interfaces.markets.Market;
import jbs.ledger.types.assets.basic.Cash;
import jbs.ledger.types.markets.MarketTickData;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
}
