package jbs.ledger.messenger;

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

    public void unknownError() {
        s("알 수 없는 오류가 발생했습니다.");
    }
    public void insufficientArgs() {
        s("입력하신 항목이 부족합니다.");
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
}
