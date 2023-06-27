package jbs.ledger.commands.economy.cards;

import jbs.ledger.Ledger;
import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.corporations.finance.Bank;
import jbs.ledger.assetholders.corporations.finance.CreditCardCompany;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.cards.CreditCard;
import jbs.ledger.classes.cards.DebitCard;
import jbs.ledger.commands.LedgerCommandKeywords;
import jbs.ledger.commands.LedgerPlayerCommand;
import jbs.ledger.interfaces.banking.Account;
import jbs.ledger.interfaces.cards.Card;
import jbs.ledger.interfaces.cards.CardIssuer;
import jbs.ledger.interfaces.common.Economic;
import jbs.ledger.interfaces.organization.Representable;
import jbs.ledger.types.assets.basic.Cash;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CardsCommand extends LedgerPlayerCommand {
    public CardsCommand(Ledger ledger) {
        super(ledger);
    }

    public CardsCommand(LedgerPlayerCommand originalCommand, Assetholder actor) {
        super(originalCommand, actor);
    }

    @Override
    protected void onPlayerCommand(@Nullable String mainArg, @Nonnull String[] argsAfterMain) {
        if (mainArg == null || argsAfterMain.length < 2) {
            getMessenger().insufficientArgs();
            return;
        }

        if (LedgerCommandKeywords.ISSUE.contains(mainArg)) {
            if (!(getActor() instanceof CardIssuer)) {
                getMessenger().executorNotCardIssuer();
                return;
            }

            Assetholder holder = getState().getAssetholder(argsAfterMain[0], true,true);
            if (holder == null) {
                getMessenger().assetholderNotFound();
                return;
            }


            if (getActor() instanceof Bank) {
                Bank bank = (Bank) getActor();

                ArrayList<Account<Cash>> accounts = bank.getAccounts(holder);
                for (Account<Cash> a : accounts) {
                    if (a.getUniqueId().toString().contains(argsAfterMain[1])) {

                        DebitCard card = new DebitCard(bank, holder, a);

                        bank.addIssuedCard(card);

                        ItemStack physicalCard = new ItemStack(getConfig().CREDIT_CARD_ITEM, 1);

                        ItemMeta meta = physicalCard.getItemMeta();
                        if (meta != null) {
                            meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "체크카드");

                            meta.setLore(Arrays.asList(
                                    card.getUniqueId().toString().substring(0, 16),
                                    ChatColor.WHITE + "명의자: " + holder.getName(),
                                    ChatColor.WHITE + "은행: " + bank.getName()
                            ));

                            physicalCard.setItemMeta(meta);
                        }

                        getPlayer().getInventory().addItem(physicalCard);

                        getMessenger().cardIssued();
                        return;
                    }
                }

                getMessenger().bankAccountNotFound();
                return;
            } else if (getActor() instanceof CreditCardCompany) {
                CreditCardCompany ccc = (CreditCardCompany) getActor();
                try {
                    double limit = Double.parseDouble(argsAfterMain[1]);
                    if (limit <= 0d) {
                        getMessenger().invalidMoney();
                        return;
                    }

                    CreditCard card = new CreditCard(ccc, holder, limit);

                    ccc.addIssuedCard(card);

                    ItemStack physicalCard = new ItemStack(getConfig().CREDIT_CARD_ITEM, 1);

                    ItemMeta meta = physicalCard.getItemMeta();
                    if (meta != null) {
                        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "신용카드");

                        meta.setLore(Arrays.asList(
                                card.getUniqueId().toString().substring(0, 16),
                                ChatColor.WHITE + "명의자: " + holder.getName(),
                                ChatColor.WHITE + "카드사: " + ccc.getName()
                        ));

                        physicalCard.setItemMeta(meta);
                    }

                    getPlayer().getInventory().addItem(physicalCard);

                    getMessenger().cardIssued();
                    return;
                } catch (NumberFormatException e) {
                    getMessenger().invalidMoney();
                    return;
                }
            }

            getMessenger().unknownError();
            return;
        } else if (LedgerCommandKeywords.BALANCE.contains(mainArg)) {
            String tag = argsAfterMain[0];
            for (CardIssuer ci : getState().getCardIssuers()) {
                for (Card c : ci.getIssuedCards()) {
                    if (c.getUniqueId().toString().equalsIgnoreCase(tag)) {
                        getMessenger().cardInformation(c);
                        return;
                    }
                }
            }

            for (CardIssuer ci : getState().getCardIssuers()) {
                for (Card c : ci.getIssuedCards()) {
                    if (c.getUniqueId().toString().toLowerCase().contains(tag.toLowerCase())) {
                        getMessenger().cardInformation(c);
                        return;
                    }
                }
            }


        } else if (LedgerCommandKeywords.CANCEL.contains(mainArg)) {
            String tag = argsAfterMain[0];
            for (CardIssuer ci : getState().getCardIssuers()) {
                for (Card c : ci.getIssuedCards()) {
                    if (c.getUniqueId().toString().equalsIgnoreCase(tag)) {
                        if (!(c.getHolder().equals(getActor()) || c.getIssuer().equals(getActor()))) {
                            getMessenger().insufficientPermissions();
                            return;
                        }

                        ci.removeIssuedCard(c);
                        getMessenger().cardCancelled();
                        return;
                    }
                }
            }

            for (CardIssuer ci : getState().getCardIssuers()) {
                for (Card c : ci.getIssuedCards()) {
                    if (c.getUniqueId().toString().toLowerCase().contains(tag.toLowerCase())) {
                        if (!(c.getHolder().equals(getActor()) || c.getIssuer().equals(getActor()))) {
                            getMessenger().insufficientPermissions();
                            return;
                        }

                        ci.removeIssuedCard(c);
                        getMessenger().cardCancelled();
                        return;
                    }
                }
            }
        } else if (LedgerCommandKeywords.ACTIVATE.contains(mainArg)) {
            ItemStack card = getPlayer().getInventory().getItemInMainHand();
            if (card.getType() != getConfig().CREDIT_CARD_ITEM) {
                getMessenger().notHoldingCard();
                return;
            }

            if (!card.hasItemMeta()) {
                getMessenger().invalidCard();
                return;
            }

            ItemMeta meta = card.getItemMeta();
            if (meta == null) {
                getMessenger().invalidCard();
                return;
            }

            List<String> lore = meta.getLore();
            if (lore == null ) {
                getMessenger().invalidCard();
                return;
            }

            String cardNumber = lore.get(0);
            for (CardIssuer ci : getState().getCardIssuers()) {
                for (Card c : ci.getIssuedCards()) {
                    if (c.getUniqueId().toString().contains(cardNumber)) {
                        if (c.isActive()) {
                            getMessenger().cardAlreadyActive();
                            return;
                        }

                        Economic holder = c.getHolder();
                        if (holder instanceof Person) {
                            if (!holder.equals(getActor())) {
                                getMessenger().insufficientPermissions();
                                return;
                            }
                        } else if (holder instanceof Representable<?>) {
                            if (!getPerson().equals(((Representable<?>) holder).getRepresentative())) {
                                getMessenger().insufficientPermissions();
                                return;
                            }
                        }


                        c.setActive(true);
                        getMessenger().cardActivated();
                        return;
                    }
                }
            }

            getMessenger().invalidCard();
            return;
        }

        getMessenger().invalidKeyword();
    }
}
