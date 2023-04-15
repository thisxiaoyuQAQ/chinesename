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

public class NameChanger implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        //设置文件
        File file = new File(main.getInstance().getDataFolder(),"settings.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        Plugin plugin = main.getPlugin(main.class);

        //设置中文名
        if (strings.length == 2 && strings[0].equals("set")) {
                if (commandSender instanceof Player) {
                    Player p = (Player) commandSender;
                    if (p.hasPermission("cn.set")) {
                        //    /cn set 带师
                        String seted_string = strings[1].replaceAll("&", "§");
                        if (!commandSender.hasPermission("cn.maxlength.bypass")) {
                            int length = seted_string.length();
                            if (length <= config.getInt("maxlength")) {
                                p.setDisplayName(seted_string + ChatColor.RESET);
                                if (config.getBoolean("tablist")) {
                                    p.setPlayerListName(seted_string + ChatColor.RESET);
                                }
                                p.sendMessage("§e§l你的中文名设置好了： " + ChatColor.RESET + seted_string);
                                main.getInstance().getConfig().set(p.getName(), seted_string);
                                main.getInstance().saveConfig();
                            } else {
                                commandSender.sendMessage("§d§l中文名超过最大长度:" + config.getInt("maxlength") + "个字符");
                            }
                        }else{
                            p.setDisplayName(seted_string + ChatColor.RESET);
                            if (config.getBoolean("tablist")) {
                                p.setPlayerListName(seted_string + ChatColor.RESET);
                            }
                            p.sendMessage("§e§l你的中文名设置好了： " + ChatColor.RESET + seted_string);
                            main.getInstance().getConfig().set(p.getName(), seted_string);
                            main.getInstance().saveConfig();
                        }
                    } else {
                        commandSender.sendMessage("§4§l你缺少cn.set的权限!");
                    }
                } else {
                    commandSender.sendMessage("§4§l该命令只能玩家使用");
                }
            }else if (strings.length == 3 && strings[0].equals("set") && strings[2].equals("-t")) {
            if (commandSender instanceof Player) {
                Player p = (Player) commandSender;
                if (p.hasPermission("cn.set.temporarily")) {
                    //   /cn set 带师 -temporarily
                    String seted_string = strings[1].replaceAll("&", "§");
                    if (!commandSender.hasPermission("cn.maxlength.bypass")) {
                        int length = seted_string.length();
                        if (length <= config.getInt("maxlength")) {
                            p.setDisplayName(seted_string + ChatColor.RESET);
                            if (config.getBoolean("tablist")) {
                                p.setPlayerListName(seted_string + ChatColor.RESET);
                            }
                            p.sendMessage("§e§l你的§b§l临时中文名§e§l设置好了： " + ChatColor.RESET + seted_string);
                        } else {
                            commandSender.sendMessage("§d§l中文名超过最大长度:" + config.getInt("maxlength") + "个字符");
                        }
                    } else {
                        p.setDisplayName(seted_string + ChatColor.RESET);
                        if (config.getBoolean("tablist")) {
                            p.setPlayerListName(seted_string + ChatColor.RESET);
                        }
                        p.sendMessage("§e§l你的中文名设置好了： " + ChatColor.RESET + seted_string);
                    }
                } else {
                    commandSender.sendMessage("§4§l你缺少cn.set.temporarily的权限!");
                }
            } else {
                commandSender.sendMessage("§4§l该命令只能玩家使用");
            }
        }else if (strings.length == 1 && strings[0].equals("set")) {
                //   /cn set
                    commandSender.sendMessage("§b参数不对诶?? 你是不是要用/cn set 名称 [-t] QAQ??");
            }

        //设置别人中文名
        if (strings.length == 3 && strings[0].equals("setother")) {
            if (commandSender.hasPermission("cn.setother")) {
                //  /cn setother rina 带师
                String seted_string = strings[2];
                Player seted = Bukkit.getPlayer(strings[1]);
                seted_string = seted_string.replaceAll("&", "§");
                if (!commandSender.hasPermission("cn.maxlength.bypass")) {
                    int length = seted_string.length();
                    if (length <= config.getInt("maxlength")) {
                    commandSender.sendMessage("§e§l" + strings[1] + "§e§l的中文名设置好了： " + ChatColor.RESET + seted_string);
                    seted.setDisplayName(seted_string + ChatColor.RESET);
                    if (config.getBoolean("tablist")) {
                        seted.setPlayerListName(seted_string + ChatColor.RESET);
                    }
                    main.getInstance().getConfig().set(seted.getName(), seted_string);
                    main.getInstance().saveConfig();
                }else {
                        commandSender.sendMessage("§d§l中文名超过最大长度:" + config.getInt("maxlength") + "个字符");
                    }
                }else{
                    commandSender.sendMessage("§e§l" + strings[1] + "§e§l的中文名设置好了： " + ChatColor.RESET + seted_string);
                    seted.setDisplayName(seted_string + ChatColor.RESET);
                    if (config.getBoolean("tablist")) {
                        seted.setPlayerListName(seted_string + ChatColor.RESET);
                    }
                    main.getInstance().getConfig().set(seted.getName(), seted_string);
                    main.getInstance().saveConfig();
                }
            } else {
                commandSender.sendMessage("§4§l你缺少cn.setother的权限!");
            }
        }else if (strings.length == 4 && strings[0].equals("setother") && strings[3].equals("-t")) {
            if (commandSender.hasPermission("cn.setother.temporarily")) {
                //   /cn setother rina 带师 -t
                String seted_string = strings[2];
                Player seted = Bukkit.getPlayer(strings[1]);
                seted_string = seted_string.replaceAll("&", "§");
                if (!commandSender.hasPermission("cn.maxlength.bypass")) {
                    int length = seted_string.length();
                    if (length <= config.getInt("maxlength")) {
                        commandSender.sendMessage("§e§l" + strings[1] + "§e§l的§b§l临时中文名§e§l设置好了： " + ChatColor.RESET + seted_string);
                        seted.setDisplayName(seted_string + ChatColor.RESET);
                        if (config.getBoolean("tablist")) {
                            seted.setPlayerListName(seted_string + ChatColor.RESET);
                        }
                    } else {
                        commandSender.sendMessage("§d§l中文名超过最大长度:" + config.getInt("maxlength") + "个字符");
                    }
                } else {
                    commandSender.sendMessage("§e§l" + strings[1] + "§e§l的中文名设置好了： " + ChatColor.RESET + seted_string);
                    seted.setDisplayName(seted_string + ChatColor.RESET);
                    if (config.getBoolean("tablist")) {
                        seted.setPlayerListName(seted_string + ChatColor.RESET);
                    }
                }
            }else {
                commandSender.sendMessage("§4§l你缺少cn.setother.temporarily的权限!");
            }
        }else if (strings.length == 1 && strings[0].equals("setother")) {
            //   /cn setother
            commandSender.sendMessage("§b参数不对诶?? 你是不是要用/cn setother 名称 [-t] QAQ??");
        }

        //重置中文名
        if (strings.length == 2 && strings[0].equals("reset")) {
            //    /cn reset rina
            if (commandSender.hasPermission("cn.reset")) {
                Player seted = Bukkit.getPlayer(strings[1]);
                seted.setDisplayName(seted.getName());
                seted.sendMessage("§e§l已经强制更改为" + seted.getName() + "§e§l的原名");
                if (config.getBoolean("tablist")) {
                    seted.setPlayerListName(seted.getName() + ChatColor.RESET);
                }
                main.getInstance().getConfig().set(seted.getName(), seted.getName());
                main.getInstance().saveConfig();
            } else {
                commandSender.sendMessage("§4§l你缺少cn.reset的权限!");
            }
        }else if (strings.length == 3 && strings[0].equals("reset") && strings[2].equals("-t")){
            //  /cn reset rina -t
            if (commandSender.hasPermission("cn.reset.temporarily")){
                Player seted = Bukkit.getPlayer(strings[1]);
                seted.setDisplayName(seted.getName());
                if (config.getBoolean("tablist")) {
                    seted.setPlayerListName(seted.getName() + ChatColor.RESET);
                }
                seted.sendMessage("§e§l已经临时强制更改为" + seted.getName() + "§e§l的原名");
            }else {
                commandSender.sendMessage("§4§l你缺少cn.reset.temporarily的权限!");
            }
        }else if (strings.length == 1 && strings[0].equals("reset")) {
            //   /cn reset
            commandSender.sendMessage("§b参数不对诶?? 你是不是要用/cn reset 名称 [-t] QAQ??");
        }

        //检查中文名
        if (strings.length == 2 && strings[0].equals("check")){
            //  /cn check rina
            if (commandSender.hasPermission("cn.check")){
                String searched = strings[1];
                String searcher = plugin.getConfig().getString(strings[1]);
                if (searcher == null){
                    commandSender.sendMessage("§4§l该玩家暂未拥有中文名");
                }
                else {
                    commandSender.sendMessage("§b玩家 " + strings[1] + " §b的中文名是: " + ChatColor.RESET + plugin.getConfig().getString(searched));
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        String p = player.getName();
                        if (searched.equals(p)) {
                            commandSender.sendMessage("§e临时中文名是: " + Bukkit.getPlayer(strings[1]).getDisplayName());
                        }
                    }
                }
            }else {
                commandSender.sendMessage("§4§l你缺少cn.check的权限!");
            }
        }else if (strings.length == 1 && strings[0].equals("check")) {
            //   /cn check
            commandSender.sendMessage("§b参数不对诶?? 你是不是要用/cn check 名称 QAQ??");
        }

        //改名卡改名卡改名卡改名卡改名卡改名卡改名卡改名卡改名卡改名卡改名卡改名卡改名卡改名卡改名卡改名卡
        ItemStack card = new ItemStack(Material.NAME_TAG);
        ItemMeta itemMeta = card.getItemMeta();
        String displayName = config.getString("name");
        itemMeta.setDisplayName(config.getString("name"));
        List<String> lores = config.getStringList("lores");
        itemMeta.setLore((lores));
        card.setItemMeta(itemMeta);

        //给予改名卡给予改名卡给予改名卡给予改名卡给予改名卡给予改名卡给予改名卡给予改名卡给予改名卡
        if (strings.length == 2 && strings[0].equals("givecard")) {
            //  /cn givecard rina
            Player p = Bukkit.getPlayer(strings[1]);
                if (commandSender.hasPermission("cn.givecard")) {
                    Inventory inventory = p.getInventory();
                    inventory.addItem(card);
                    commandSender.sendMessage("§b成功给予" + strings[1] +"改名卡*1");
                    p.sendMessage("§b你被给予改名卡*1");
                } else {
                    commandSender.sendMessage("§4§l你缺少cn.givecard的权限");
                }
            }else if (strings.length == 1 && strings[0].equals("givecard")) {
            //   /cn givecard
            commandSender.sendMessage("§b参数不对诶?? 你是不是要用/cn givecard 名称 QAQ??");
        }

        //使用改名卡使用改名卡使用改名卡使用改名卡使用改名卡
            if (strings.length == 2 && strings[0].equals("usecard")) {
                //  /cn usecard abc
                if (commandSender instanceof Player) {
                    Player p = (Player)commandSender;
                    Inventory inv = p.getInventory();
                if (p.hasPermission("cn.usecard")) {
                    for (int packet = 0; packet < inv.getSize(); ++packet) {
                        if (inv.getItem(packet) != null) {
                            ItemStack item = inv.getItem(packet);
                            boolean material = item.getType().equals(Material.NAME_TAG);
                            if(item.getItemMeta().getDisplayName()!=null && material) {
                                boolean displayNameRight = item.getItemMeta().getDisplayName().equals(displayName);
                                if (item.getItemMeta().getLore()!=null && displayNameRight) {
                                    boolean loresRight = item.getItemMeta().getLore().equals(lores);
                                    if (loresRight) {
                                        strings[1] = strings[1].replaceAll("&", "§");
                                        String seted_string = strings[1];
                                        if (!commandSender.hasPermission("cn.maxlength.bypass")) {
                                            int length = seted_string.length();
                                            if (length <= config.getInt("maxlength")) {
                                                p.setDisplayName(strings[1] + ChatColor.RESET);
                                                if (config.getBoolean("tablist")) {
                                                    p.setPlayerListName(seted_string + ChatColor.RESET);
                                                }
                                                p.sendMessage("§e§l你的中文名设置好了： " + ChatColor.RESET + strings[1]);
                                                main.getInstance().getConfig().set(p.getName(), strings[1]);
                                                main.getInstance().saveConfig();
                                            } else {
                                                commandSender.sendMessage("§d§l中文名超过最大长度:" + config.getInt("maxlength") + "个字符");
                                                Inventory inventory = p.getInventory();
                                                inventory.addItem(card);
                                            }
                                        } else {
                                            p.setDisplayName(seted_string + ChatColor.RESET);
                                            if (config.getBoolean("tablist")) {
                                                p.setPlayerListName(seted_string + ChatColor.RESET);
                                            }
                                            p.sendMessage("§e§l你的中文名设置好了： " + ChatColor.RESET + seted_string);
                                            main.getInstance().getConfig().set(p.getName(), seted_string);
                                            main.getInstance().saveConfig();
                                        }
                                        if (item.getAmount() == 1) {
                                            inv.setItem(packet, new ItemStack(Material.AIR));
                                        } else {
                                            ItemStack cardStack = inv.getItem(packet);
                                            cardStack.setAmount(cardStack.getAmount() - 1);
                                            inv.setItem(packet, cardStack);
                                        }
                                    } else {
                                        System.out.println("error");
                                    }
                                }
                            }
                        }
                    }
                }else {
                    commandSender.sendMessage("§4§l你缺少cn.usecard的权限");
                }
                }else {
                    commandSender.sendMessage("§4§l该命令仅玩家使用");
                }
            }else if (strings.length == 1 && strings[0].equals("usecard")) {
                //   /cn usecard
                commandSender.sendMessage("§b参数不对诶?? 你是不是要用/cn usecard 名称 QAQ??");
            }

            if (strings.length == 2 && strings[0].equals("tp")){
                if (commandSender instanceof Player){
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
                }
            } else if (strings.length == 1 && strings[0].equals("tp")) {
                commandSender.sendMessage("§b参数不对诶?? 你是不是要用/cn tp 玩家 QAQ??");
            }























            //reload
        if (commandSender != null && commandSender.hasPermission("cn.reload"))
            if (strings.length == 1 && strings[0].equals("reload")) {
                plugin.reloadConfig();
                YamlConfiguration.loadConfiguration(new File("settings.yml"));
                for (Player player : Bukkit.getOnlinePlayers()) {
                    String p = player.getName();
                    player.setPlayerListName(plugin.getConfig().getString(p).replaceAll("&","§") + ChatColor.RESET);
                    player.setDisplayName(plugin.getConfig().getString(p).replaceAll("&","§") + ChatColor.RESET);
                }
                commandSender.sendMessage("§c[ChineseName]重载成功");
            }

        assert commandSender != null;
        if (
                    strings.length == 1 && !(strings[0].equals("set")
                    || strings[0].equals("reset")
                    || strings[0].equals("setother")
                    || strings[0].equals("check")
                    || strings[0].equals("givecard")
                    || strings[0].equals("usecard")
                    || strings[0].equals("help")
                    || strings[0].equals("reload"))
                    || strings[0].equals("tp")
            ){

            commandSender.sendMessage("§d小雨不能理解你在干啥诶?? 使用/cn help 1/2 ~~~ 查询帮助");
            }

        if (
                (strings.length == 0) || (strings.length == 1
                && strings[0].equals("help"))
                || (strings.length == 2
                && strings[0].equals("help")
                && strings[1].equals("1"))
        ){
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
        }else if (
                strings.length == 2
                        && strings[0].equals("help")
                        && strings[1].equals("2")
        ){
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
