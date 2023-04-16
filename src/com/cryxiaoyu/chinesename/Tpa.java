package com.cryxiaoyu.chinesename;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public class Tpa {
    private static final ConcurrentHashMap<Player, Player> pendingRequests = new ConcurrentHashMap<>();

    public static void sendTeleportRequest(Player sender, Player target) {
        // 添加到挂起的传送请求
        pendingRequests.put(sender, target);

        // 发给目标玩家的信息
        sender.sendMessage("请求传送到" + Main.getInstance().getConfig().getString(target.getName()).replace("&","§") + ". 请等待对方接受");
        target.sendMessage("玩家 " + Main.getInstance().getConfig().getString(sender.getName()).replace("&","§") + " 想要传送到你那里,你可以输入/cn tpaccept 来接受.");

        // 60秒后移除挂起的传送请求
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if (pendingRequests.containsKey(sender)) {
                pendingRequests.remove(sender);
                sender.sendMessage("请求传送到 " + Main.getInstance().getConfig().getString(target.getName()).replace("&","§") + " 请求已过期.");
            }
        }, 60 * 20L); // 60 秒
    }

    public static void acceptTeleportRequest(Player player) {
        // 检测玩家是否有挂起的传送请求
        if (!pendingRequests.containsKey(player)) {
            player.sendMessage("§b你没有任何挂起的远程传输请求.");
            return;
        }
        Player sender = pendingRequests.get(player);
        pendingRequests.remove(player);
        // 传送时间
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            sender.teleport(player.getLocation().add(0, 1, 0));
            sender.sendMessage("玩家 " + Main.getInstance().getConfig().getString(player.getName()).replace("&","§") + " 传送到了你这里.");
        }, 3 * 20L); // 3 秒
    }
}
