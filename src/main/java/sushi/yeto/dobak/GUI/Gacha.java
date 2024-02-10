package sushi.yeto.dobak.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import sushi.yeto.dobak.Main;

public class Gacha extends GUI {
    private final Player openedGUIPlayer; // 플레이어 참조 변수 추가

    public Gacha(Player player) {
        super(player, "가챠 도박", 27);
        this.openedGUIPlayer = player; // 생성자에서 열린 GUI를 연 플레이어 참조 저장
    }
    Random random = new Random();
    void switchItem(int i) {
        int randomnumber = random.nextInt(46);

        String displayName;
        String lore;
        Material material;

        switch (randomnumber) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                displayName = "§r§f1배";
                lore = "§r§71배 당첨";
                material = Material.GOLD_NUGGET;
                setItem(displayName, Collections.singletonList(lore), material, 1, i + 11, "1", true);
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                displayName = "§r§f2배";
                lore = "§r§72배 당첨";
                material = Material.GOLD_INGOT;
                setItem(displayName, Collections.singletonList(lore), material, 1, i + 11, "2", true);
                break;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                displayName = "§r§f5배";
                lore = "§r§75배 당첨";
                material = Material.LAPIS_LAZULI;
                setItem(displayName, Collections.singletonList(lore), material, 1, i + 11, "5", true);
                break;
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
                displayName = "§r§f10배";
                lore = "§r§710배 당첨";
                material = Material.EMERALD;
                setItem(displayName, Collections.singletonList(lore), material, 1, i + 11, "10", true);
                break;
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
                displayName = "§r§f50배";
                lore = "§r§750배 당첨";
                material = Material.DIAMOND;
                setItem(displayName, Collections.singletonList(lore), material, 1, i + 11, "50", true);
                break;
            case 36:
            case 37:
            case 38:
                displayName = "§r§f100배";
                lore = "§r§7100배 당첨";
                material = Material.NETHERITE_INGOT;
                setItem(displayName, Collections.singletonList(lore), material, 1, i + 11, "100", true);
                break;
            case 39:
            case 40:
            case 41:
                displayName = "§r§f150배";
                lore = "§r§7150배 당첨";
                material = Material.AMETHYST_SHARD;
                setItem(displayName, Collections.singletonList(lore), material, 1, i + 11, "150", true);
                break;
            case 42:
            case 43:
                displayName = "§r§f200배";
                lore = "§r§7200배 당첨";
                material = Material.MAGMA_CREAM;
                setItem(displayName, Collections.singletonList(lore), material, 1, i + 11, "200", true);
                break;
            case 44:
                displayName = "§r§f500배";
                lore = "§r§7500배 당첨";
                material = Material.NETHER_STAR;
                setItem(displayName, Collections.singletonList(lore), material, 1, i + 11, "500", true);
                break;
            default:
                ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
                skullMeta.setOwner("chosinong");
                playerHead.setItemMeta(skullMeta);
                displayName = "§r§f1000배";
                lore = "§r§71000배 당첨";
                setItem(displayName, Collections.singletonList(lore), playerHead, 1, i + 11, "1000", true);
                break;
        }
    }

    @Override
    void init() {
        for (int i = 0; i <= 10; i++) {
            setItem(" ", Collections.singletonList(""), Material.GRAY_STAINED_GLASS_PANE, 1, i, "", false);
        }

        for (int i = 11; i <= 15; i++) {
            setItem("§r§f1배", Collections.singletonList("§r§71배 당첨"), Material.GOLD_NUGGET, 1, i, "1배", true);
        }

        for (int i = 16; i <= 26; i++) {
            setItem(" ", Collections.singletonList(""), Material.GRAY_STAINED_GLASS_PANE, 1, i, "", false);
        }

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> setItem("§r§f1배", Collections.singletonList("§r§71배 당첨"), Material.GOLD_NUGGET, 1, 11, "1배", true), 20L);

        final long[] lastExecutionTime = {System.currentTimeMillis()};
        final double intervalSeconds = 0.07;

        AtomicInteger executionCount = new AtomicInteger();
        int maxExecutions = 25;

        int[] taskId = {-1};

        taskId[0] = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            long currentTime = System.currentTimeMillis();
            double elapsedSeconds = (currentTime - lastExecutionTime[0]) / 1000.0;

            if (elapsedSeconds >= intervalSeconds) {
                switchItem(0);
                switchItem(1);
                switchItem(2);
                switchItem(3);
                switchItem(4);

                // 수정: 해당 플레이어에게만 소리 들리도록 변경
                playSoundForPlayer(openedGUIPlayer);

                lastExecutionTime[0] = currentTime;
                executionCount.getAndIncrement();

                if (executionCount.get() >= maxExecutions) {
                    Bukkit.getScheduler().cancelTask(taskId[0]);
                }
            }
        }, 0L, 1L).getTaskId();
    }

    // 수정: 해당 플레이어에게만 소리 들리도록 변경
    private void playSoundForPlayer(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
    }

    @Override
    public void onClick(InventoryClickEvent event) {

    }
}
