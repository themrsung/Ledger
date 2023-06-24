package jbs.ledger.messenger;

import jbs.ledger.assetholders.person.Person;
import org.bukkit.entity.Player;

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
}
