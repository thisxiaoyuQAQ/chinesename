package main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;
import java.util.Set;

public class temp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        File file = new File(main.getInstance().getDataFolder(), "settings.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        Plugin plugin = main.getPlugin(main.class);

        if (strings.length == 2 && strings[0].equals("tp")) {
            if (commandSender instanceof Player) {
                Player p = (Player) commandSender;
                if (p.hasPermission("cn.tp")) {
                    //cn tp 日那
                    Set<String> key = config.getKeys(false);
                    String cn = null;
                    for (String now : key) {
                        String tmp = config.getString(now);
                        if (tmp.equalsIgnoreCase(strings[1])) {
                            cn = tmp;
                            break;
                        }
                    }
                    if (cn != null) {
                        boolean flag = false;
                        for (Player now : Bukkit.getOnlinePlayers()) {
                            if (now.getName().equalsIgnoreCase(cn)) {
                                flag = true;
                                ((Player) commandSender).teleport(now.getLocation().clone().add(0, 1, 0));
                                //免得卡方块
                                break;
                            }
                        }
                        if (flag) {
                            commandSender.sendMessage("§e§l传送成功!");
                        } else {
                            commandSender.sendMessage("§b玩家不在线诶?? 你要不要叫他上线一起♂van QAQ??");
                        }
                    } else {
                        commandSender.sendMessage("§b没有这个玩家诶?? 你是不是拼错了 QAQ??");
                    }
                }
            } else {
                System.out.println("[ChineseName]控制台请勿使用该指令");
            }
        } else if (strings.length == 1 && strings[0].equals("tp")) {
            commandSender.sendMessage("§b参数不对诶?? 你是不是要用/cn tp 玩家 QAQ??");
        }

        if (strings.length == 2 && strings[0].equals("tpa")) {
            if (commandSender instanceof Player) {
                Player p = (Player) commandSender;
                if (p.hasPermission("cn.tpa")) {
                    //cn tp 日那
                    Set<String> key = config.getKeys(false);
                    String cn = null;
                    for (String now : key) {
                        String tmp = config.getString(now);
                        if (tmp.equalsIgnoreCase(strings[1])) {
                            cn = tmp;
                            break;
                        }
                    }
                    if (cn != null) {
                        boolean flag = false;
                        for (Player now : Bukkit.getOnlinePlayers()) {
                            if (now.getName().equalsIgnoreCase(cn)) {
                                now.sendMessage("§e§l" + commandSender.getName() + "请求传送到你这里来，请抬头下蹲接受，低头下蹲取消!");
                                //我比较烦打指令
                                Thread t = new Thread(() -> {
                                    for (int i = 0; i < 100; i++) {
                                        try {
                                            Thread.sleep(100);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        if (!now.isOnline()) {
                                            break;
                                        }
                                        if (now.getLocation().getPitch() > 179 && p.isSneaking()) {
                                            ((Player) commandSender).teleport(now.getLocation().clone().add(0, 1, 0));
                                            now.sendMessage("§e§l你接受了传送!");
                                            break;
                                        }
                                        if (now.getLocation().getPitch() < 1 && p.isSneaking()) {
                                            now.sendMessage("§e§l你取消了传送!");
                                            break;
                                        }
                                    }
                                });
                                t.start();
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            commandSender.sendMessage("§e§l请求传送成功!");
                        } else {
                            commandSender.sendMessage("§b玩家不在线诶?? 你要不要叫他上线一起♂van QAQ??");
                        }
                    } else {
                        commandSender.sendMessage("§b没有这个玩家诶?? 你是不是拼错了 QAQ??");
                    }
                }
            } else {
                System.out.println("[ChineseName]控制台请勿使用该指令");
            }
        } else if (strings.length == 1 && strings[0].equals("tp")) {
            commandSender.sendMessage("§b参数不对诶?? 你是不是要用/cn tp 玩家 QAQ??");
        }

        if (commandSender.hasPermission("cn.reload"))
            if (strings.length == 1 && strings[0].equals("reload")) {
                plugin.reloadConfig();
                YamlConfiguration.loadConfiguration(new File("settings.yml"));
                for (Player player : Bukkit.getOnlinePlayers()) {
                    String p = player.getName();
                    player.setPlayerListName(plugin.getConfig().getString(p) + ChatColor.RESET);
                    player.setDisplayName(plugin.getConfig().getString(p) + ChatColor.RESET);
                }
                commandSender.sendMessage("§c[ChineseName]重载成功");
            }

        if (
                strings.length == 1 && !(strings[0].equals("set")
                        || strings[0].equals("reset")
                        || strings[0].equals("setother")
                        || strings[0].equals("check")
                        || strings[0].equals("givecard")
                        || strings[0].equals("usecard")
                        || strings[0].equals("help")
                        || strings[0].equals("reload"))
        ) {
            commandSender.sendMessage("§d小雨不能理解你在干啥诶?? 使用/cn help 1/2 ~~~ 查询帮助");
        }

        if (
                (strings.length == 0) || (strings.length == 1
                        && strings[0].equals("help"))
                        || (strings.length == 2
                        && strings[0].equals("help")
                        && strings[1].equals("1"))
        ) {
            commandSender.sendMessage("§b§l=========第一页=========");
            commandSender.sendMessage("§d/cn help 1/2 ~~~ 查询帮助");
            commandSender.sendMessage("§a/cn set 名称 ~~~ 设置自己的中文名 - cn.set");
            commandSender.sendMessage("§a/cn set 名称 -t ~~~ 设置自己的临时中文名 - cn.set.temporarily");
            commandSender.sendMessage("§a/cn setother ID 名称 ~~~ 设置他人的中文名 - cn.setother");
            commandSender.sendMessage("§a/cn setother ID 名称 -t ~~~ 设置他人的临时中文名 - cn.setother.temporarily");
            commandSender.sendMessage("§a/cn reset ID ~~~ 重置某人的中文名 - cn.reset");
            commandSender.sendMessage("§a/cn reset ID -t ~~~ 临时重置某人的中文名 - cn.reset.temporarily");
            commandSender.sendMessage("§e§l你的支持是小雨最大的动力");
            commandSender.sendMessage("§b§l=========共两页=========");
        } else if (
                strings.length == 2
                        && strings[0].equals("help")
                        && strings[1].equals("2")
        ) {
            commandSender.sendMessage("§b§l=========第二页=========");
            commandSender.sendMessage("§d/cn help 1/2 ~~~ 查询帮助");
            commandSender.sendMessage("§a/cn check ID ~~~ 查询某人的中文名 - cn.check");
            commandSender.sendMessage("§a/cn givecard ID ~~~ 给予某人一张改名卡 - cn.getcard");
            commandSender.sendMessage("§a/cn usecard ~~~ 食用一张改名卡 - cn.usecard");
            commandSender.sendMessage("§e§l你的支持是小雨最大的动力");
            commandSender.sendMessage("§b§l=========共两页=========");
        }
        return true;
    }
}