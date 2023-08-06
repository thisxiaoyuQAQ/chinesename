package com.cryxiaoyu.chinesename.command;

import com.cryxiaoyu.chinesename.Main;
import com.cryxiaoyu.chinesename.Tpa;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

public class NameChanger implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        //设置文件
        File file = new File(Main.getInstance().getDataFolder(), "settings.yml");
        File language = new File(Main.getInstance().getDataFolder(), "language.yml");
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(file);
        YamlConfiguration lan = YamlConfiguration.loadConfiguration(language);
        FileConfiguration config =  Main.getInstance().getConfig();
        String id = config.getConfigurationSection("").getKeys(false);


        Plugin plugin = Main.getPlugin(Main.class);

        String maxlength = lan.getString("maxlength_warn").replace("&", "§").replaceAll("%maxWords%", String.valueOf(settings.getInt("maxlength")));
        String onlyPlayer = lan.getString("onlyPlayer").replace("&", "§");

        //设置中文名
        if (strings.length == 2 && strings[0].equals("set")) {
            if (commandSender instanceof Player) {
                Player p = (Player) commandSender;
                if (p.hasPermission("cn.set")) {
                    //    /cn set 带师
                    String seted_string = strings[1].replace("&", "§");
                    if (!commandSender.hasPermission("cn.maxlength.bypass")) {
                        int length = seted_string.length();
                        if (length <= settings.getInt("maxlength")) {
                            p.setDisplayName(seted_string + ChatColor.RESET);
                            if (settings.getBoolean("tablist")) {
                                p.setPlayerListName(seted_string + ChatColor.RESET);
                            }
                            //p.sendMessage("§e§l你的中文名设置好了： " + ChatColor.RESET + seted_string);
                            p.sendMessage(lan.getString("setSuccess").replace("&", "§").replaceAll("%nick%", seted_string));
                            Main.getInstance().getConfig().set(p.getName(), seted_string.replace("&", "§"));
                            Main.getInstance().saveConfig();
                        } else {
                            //commandSender.sendMessage("§d§l中文名超过最大长度:" + settings.getInt("maxlength") + "个字符");
                            commandSender.sendMessage(maxlength);
                        }
                    } else {
                        p.setDisplayName(seted_string + ChatColor.RESET);
                        if (settings.getBoolean("tablist")) {
                            p.setPlayerListName(seted_string + ChatColor.RESET);
                        }
                        //p.sendMessage("§e§l你的中文名设置好了： " + ChatColor.RESET + seted_string);
                        p.sendMessage(lan.getString("setSuccess").replace("&", "§").replaceAll("%nick%", seted_string));
                        Main.getInstance().getConfig().set(p.getName(), seted_string.replace("&", "§"));
                        Main.getInstance().saveConfig();
                    }
                } else {
                    commandSender.sendMessage(lan.getString("perm_lack_set").replace("&", "§"));
                }
            } else {
                commandSender.sendMessage(onlyPlayer);
            }
        } else if (strings.length == 3 && strings[0].equals("set") && strings[2].equals("-t")) {
            if (commandSender instanceof Player) {
                Player p = (Player) commandSender;
                if (p.hasPermission("cn.set.temporarily")) {
                    //   /cn set 带师 -temporarily
                    String seted_string = strings[1].replace("&", "§");
                    if (!commandSender.hasPermission("cn.maxlength.bypass")) {
                        int length = seted_string.length();
                        if (length <= settings.getInt("maxlength")) {
                            p.setDisplayName(seted_string + ChatColor.RESET);
                            if (settings.getBoolean("tablist")) {
                                p.setPlayerListName(seted_string + ChatColor.RESET);
                            }
                            //p.sendMessage("§e§l你的§b§l临时中文名§e§l设置好了： " + ChatColor.RESET + seted_string);
                            p.sendMessage(lan.getString("setSuccess_T").replace("&", "§").replaceAll("%nick%", seted_string));
                        } else {
                            commandSender.sendMessage(maxlength);
                        }
                    } else {
                        p.setDisplayName(seted_string + ChatColor.RESET);
                        if (settings.getBoolean("tablist")) {
                            p.setPlayerListName(seted_string + ChatColor.RESET);
                        }
                        p.sendMessage(lan.getString("setSuccess_T").replace("&", "§").replaceAll("%nick%", seted_string));
                    }
                } else {
                    commandSender.sendMessage(lan.getString("perm_lack_set_T").replace("&", "§"));
                }
            } else {
                commandSender.sendMessage(onlyPlayer);
            }
        } else if (strings.length == 1 && strings[0].equals("set")) {
            //   /cn set
            commandSender.sendMessage(lan.getString("wrong_set").replace("&", "§"));
        }

        //设置别人中文名
        if (strings.length == 3 && strings[0].equals("setother")) {
            if (commandSender.hasPermission("cn.setother")) {
                //  /cn setother rina 带师
                String seted_string = strings[2];
                Player seted = Bukkit.getPlayer(strings[1]);
                seted_string = seted_string.replace("&", "§");
                if (!commandSender.hasPermission("cn.maxlength.bypass")) {
                    int length = seted_string.length();
                    if (length <= settings.getInt("maxlength")) {
                        //commandSender.sendMessage("§e§l" + strings[1] + "§e§l的中文名设置好了： " + ChatColor.RESET + seted_string);
                        commandSender.sendMessage(lan.getString("setOtherSuccess").replace("&", "§").replaceAll("%player%", strings[1]).replaceAll("%nick%", seted_string));
                        seted.setDisplayName(seted_string + ChatColor.RESET);
                        if (settings.getBoolean("tablist")) {
                            seted.setPlayerListName(seted_string + ChatColor.RESET);
                        }
                        Main.getInstance().getConfig().set(seted.getName(), seted_string.replace("&", "§"));
                        Main.getInstance().saveConfig();
                    } else {
                        commandSender.sendMessage(maxlength);
                    }
                } else {
                    commandSender.sendMessage(lan.getString("setOtherSuccess").replace("&", "§").replaceAll("%player%", strings[1]).replaceAll("%nick%", seted_string));
                    seted.setDisplayName(seted_string + ChatColor.RESET);
                    if (settings.getBoolean("tablist")) {
                        seted.setPlayerListName(seted_string + ChatColor.RESET);
                    }
                    Main.getInstance().getConfig().set(seted.getName(), seted_string.replace("&", "§"));
                    Main.getInstance().saveConfig();
                }
            } else {
                commandSender.sendMessage(lan.getString("perm_lack_setother"));
            }
        } else if (strings.length == 4 && strings[0].equals("setother") && strings[3].equals("-t")) {
            if (commandSender.hasPermission("cn.setother.temporarily")) {
                //   /cn setother rina 带师 -t
                String seted_string = strings[2];
                Player seted = Bukkit.getPlayer(strings[1]);
                seted_string = seted_string.replace("&", "§");
                if (!commandSender.hasPermission("cn.maxlength.bypass")) {
                    int length = seted_string.length();
                    if (length <= settings.getInt("maxlength")) {
                        //commandSender.sendMessage("§e§l" + strings[1] + "§e§l的§b§l临时中文名§e§l设置好了： " + ChatColor.RESET + seted_string);
                        commandSender.sendMessage(lan.getString("setOtherSuccess_T").replace("&", "§").replaceAll("%player%", strings[1]).replaceAll("%nick%", seted_string));
                        seted.setDisplayName(seted_string + ChatColor.RESET);
                        if (settings.getBoolean("tablist")) {
                            seted.setPlayerListName(seted_string + ChatColor.RESET);
                        }
                    } else {
                        commandSender.sendMessage(maxlength);
                    }
                } else {
                    commandSender.sendMessage(lan.getString("setOtherSuccess_T").replace("&", "§").replaceAll("%player%", strings[1]).replaceAll("%nick%", seted_string));
                    seted.setDisplayName(seted_string + ChatColor.RESET);
                    if (settings.getBoolean("tablist")) {
                        seted.setPlayerListName(seted_string + ChatColor.RESET);
                    }
                }
            } else {
                commandSender.sendMessage(lan.getString("perm_lack_setother_T"));
            }
        } else if (strings.length == 1 && strings[0].equals("setother")) {
            //   /cn setother
            //commandSender.sendMessage("§b参数不对诶?? 你是不是要用/cn setother 名称 [-t] QAQ??");
            commandSender.sendMessage(lan.getString("wrong_setother").replace("&", "§"));
        }

        //重置中文名
        if (strings.length == 2 && strings[0].equals("reset")) {
            //    /cn reset rina
            if (commandSender.hasPermission("cn.reset")) {
                Player seted = Bukkit.getPlayer(strings[1]);
                seted.setDisplayName(seted.getName());
                //seted.sendMessage("§e§l已经强制更改为" + seted.getName() + "§e§l的原名");
                commandSender.sendMessage(lan.getString("resetSuccess").replace("&", "§").replaceAll("%player%", seted.getName()));
                if (settings.getBoolean("tablist")) {
                    seted.setPlayerListName(seted.getName() + ChatColor.RESET);
                }
                Main.getInstance().getConfig().set(seted.getName(), seted.getName());
                Main.getInstance().saveConfig();
            } else {
                commandSender.sendMessage(lan.getString("perm_lack_reset").replace("&", "§"));
            }
        } else if (strings.length == 3 && strings[0].equals("reset") && strings[2].equals("-t")) {
            //  /cn reset rina -t
            if (commandSender.hasPermission("cn.reset.temporarily")) {
                Player seted = Bukkit.getPlayer(strings[1]);
                seted.setDisplayName(seted.getName());
                if (settings.getBoolean("tablist")) {
                    seted.setPlayerListName(seted.getName() + ChatColor.RESET);
                }
                //seted.sendMessage("§e§l已经临时强制更改为" + seted.getName() + "§e§l的原名");
                commandSender.sendMessage(lan.getString("resetSuccess_T").replace("&", "§").replaceAll("%player%", seted.getName()));
            } else {
                //commandSender.sendMessage("§4§l你缺少cn.reset.temporarily的权限!");
                commandSender.sendMessage(lan.getString("perm_lack_reset_T").replace("&", "§"));
            }
        } else if (strings.length == 1 && strings[0].equals("reset")) {
            //   /cn reset
            //commandSender.sendMessage("§b参数不对诶?? 你是不是要用/cn reset 名称 [-t] QAQ??");
            commandSender.sendMessage(lan.getString("wrong_reset").replace("&", "§"));
        }

        //检查中文名
        if (strings.length == 2 && strings[0].equals("check")) {
            //  /cn check rina
            if (commandSender.hasPermission("cn.check")) {
                String searched = strings[1];
                String searcher = plugin.getConfig().getString(strings[1]);
                if (searcher == null) {
                    //commandSender.sendMessage("§4§l该玩家暂未拥有中文名");
                    commandSender.sendMessage(lan.getString("check").replace("&", "§"));
                } else {
                    //commandSender.sendMessage("§b玩家 " + strings[1] + " §b的中文名是: " + ChatColor.RESET + plugin.getConfig().getString(searched));
                    commandSender.sendMessage(lan.getString("check_name").replace("&", "§").replaceAll("%player%", strings[1]).replaceAll("%nick%", plugin.getConfig().getString(searched)));
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        String p = player.getName();
                        if (searched.equals(p)) {
                            //commandSender.sendMessage("§e临时中文名是: " + Bukkit.getPlayer(strings[1]).getDisplayName());
                            commandSender.sendMessage(lan.getString("check_name").replace("&", "§").replaceAll("%player%", strings[1]).replaceAll("%nick%", Bukkit.getPlayer(strings[1]).getDisplayName()));
                        }
                    }
                }
            } else {
                //commandSender.sendMessage("§4§l你缺少cn.check的权限!");
                commandSender.sendMessage(lan.getString("perm_lack_check").replace("&", "§"));
            }
        } else if (strings.length == 1 && strings[0].equals("check")) {
            //   /cn check
            //commandSender.sendMessage("§b参数不对诶?? 你是不是要用/cn check 名称 QAQ??");
            commandSender.sendMessage(lan.getString("wrong_check").replace("&", "§"));
        }

        //改名卡改名卡改名卡改名卡改名卡改名卡改名卡改名卡改名卡改名卡改名卡改名卡改名卡改名卡改名卡改名卡
        ItemStack card = new ItemStack(Material.NAME_TAG);
        ItemMeta itemMeta = card.getItemMeta();
        String displayName = settings.getString("name");
        itemMeta.setDisplayName(settings.getString("name"));
        List<String> lores = settings.getStringList("lores");
        itemMeta.setLore((lores));
        card.setItemMeta(itemMeta);

        //给予改名卡给予改名卡给予改名卡给予改名卡给予改名卡给予改名卡给予改名卡给予改名卡给予改名卡
        if (strings.length == 2 && strings[0].equals("givecard")) {
            //  /cn givecard rina
            Player p = Bukkit.getPlayer(strings[1]);
            if (commandSender.hasPermission("cn.givecard")) {
                Inventory inventory = p.getInventory();
                inventory.addItem(card);
                //commandSender.sendMessage("§b成功给予" + strings[1] +"改名卡*1");
                commandSender.sendMessage(lan.getString("givecard").replace("&", "§").replaceAll("%player%", strings[1]));
                //p.sendMessage("§b你被给予改名卡*1");
                commandSender.sendMessage(lan.getString("givecard_informer").replace("&", "§"));

            } else {
                //commandSender.sendMessage("§4§l你缺少cn.givecard的权限");
                commandSender.sendMessage(lan.getString("perm_lack_givecard").replace("&", "§"));
            }
        } else if (strings.length == 1 && strings[0].equals("givecard")) {
            //   /cn givecard
            //commandSender.sendMessage("§b参数不对诶?? 你是不是要用/cn givecard 名称 QAQ??");
            commandSender.sendMessage(lan.getString("wrong_givecard").replace("&", "§"));
        }

        //使用改名卡使用改名卡使用改名卡使用改名卡使用改名卡
        if (strings.length == 2 && strings[0].equals("usecard")) {
            //  /cn usecard abc
            if (commandSender instanceof Player) {
                Player p = (Player) commandSender;
                Inventory inv = p.getInventory();
                if (p.hasPermission("cn.usecard")) {
                    for (int packet = 0; packet < inv.getSize(); ++packet) {
                        if (inv.getItem(packet) != null) {
                            ItemStack item = inv.getItem(packet);
                            boolean material = item.getType().equals(Material.NAME_TAG);
                            if (item.getItemMeta().getDisplayName() != null && material) {
                                boolean displayNameRight = item.getItemMeta().getDisplayName().equals(displayName);
                                if (item.getItemMeta().getLore() != null && displayNameRight) {
                                    boolean loresRight = item.getItemMeta().getLore().equals(lores);
                                    if (loresRight) {
                                        strings[1] = strings[1].replace("&", "§");
                                        String seted_string = strings[1];
                                        if (!commandSender.hasPermission("cn.maxlength.bypass")) {
                                            int length = seted_string.length();
                                            if (length <= settings.getInt("maxlength")) {
                                                p.setDisplayName(strings[1] + ChatColor.RESET);
                                                if (settings.getBoolean("tablist")) {
                                                    p.setPlayerListName(seted_string + ChatColor.RESET);
                                                }
                                                //p.sendMessage("§e§l你的中文名设置好了： " + ChatColor.RESET + strings[1]);
                                                commandSender.sendMessage(lan.getString("usecard").replace("&", "§").replaceAll("%nick%", seted_string));
                                                Main.getInstance().getConfig().set(p.getName(), strings[1]);
                                                Main.getInstance().saveConfig();
                                            } else {
                                                //commandSender.sendMessage("§d§l中文名超过最大长度:" + settings.getInt("maxlength") + "个字符");
                                                commandSender.sendMessage(maxlength);
                                                Inventory inventory = p.getInventory();
                                                inventory.addItem(card);
                                            }
                                        } else {
                                            p.setDisplayName(seted_string + ChatColor.RESET);
                                            if (settings.getBoolean("tablist")) {
                                                p.setPlayerListName(seted_string + ChatColor.RESET);
                                            }
                                            //p.sendMessage("§e§l你的中文名设置好了： " + ChatColor.RESET + seted_string);
                                            commandSender.sendMessage(lan.getString("usecard").replace("&", "§").replaceAll("%nick%", seted_string));
                                            Main.getInstance().getConfig().set(p.getName(), seted_string);
                                            Main.getInstance().saveConfig();
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
                } else {
                    //commandSender.sendMessage("§4§l你缺少cn.usecard的权限");
                    commandSender.sendMessage(lan.getString("perm_lack_usecard").replace("&", "§"));
                }
            } else {
                //commandSender.sendMessage("§4§l该命令仅玩家使用");
                commandSender.sendMessage(onlyPlayer);
            }
        } else if (strings.length == 1 && strings[0].equals("usecard")) {
            //   /cn usecard
            //commandSender.sendMessage("§b参数不对诶?? 你是不是要用/cn usecard 名称 QAQ??");
            commandSender.sendMessage(lan.getString("wrong_usecard").replace("&", "§"));
        }


        //tp功能
        if (strings.length == 2 && strings[0].equals("tp")) {
            // cn tp 日那
            if (commandSender instanceof Player) {
                Player p = (Player) commandSender;
                if (p.hasPermission("cn.tp")) {
                        try {
                            if (Bukkit.getPlayer(Main.playerName.get(strings[1]) ) !=null ) {
                                p.teleport(Bukkit.getPlayer(Main.playerName.get(strings[1])).getLocation().add(0, 1, 0));
                                //p.sendMessage("§b成功传送到玩家 " + Main.getInstance().getConfig().getString(Bukkit.getPlayer(Main.playerName.get(strings[1])).getName()).replace("&","§") + " §b的位置");
                                commandSender.sendMessage(lan.getString("tpSuccess").replace("&","§").replaceAll("%nick%", strings[1]).replaceAll("%player%",Main.getInstance().getConfig().getString(Bukkit.getPlayer(Main.playerName.get(strings[1])).getName()).replace("&","§")));
                            }
                        } catch (Exception e){
                            //p.sendMessage("§4§l玩家不在线");
                            p.sendMessage(lan.getString("offline").replace("&", "§"));
                        }

                } else {
                            p.sendMessage(lan.getString("perm_lack_tp").replace("&", "§"));
                        }
                    } else {
                        commandSender.sendMessage(onlyPlayer);
                    }
                } else if (strings.length == 1 && strings[0].equals("tp")) {
                    //   /cn tp
                    //commandSender.sendMessage("§b参数不对诶?? 你是不是要用/cn tp 名称 QAQ??");
                    commandSender.sendMessage(lan.getString("wrong_tp").replace("&", "§"));
                }
                if (strings.length == 2 && strings[0].equals("tpa")) {
                    try {
                        Tpa.sendTeleportRequest((Player) commandSender, Bukkit.getPlayer(Main.playerName.get(strings[1])));
                    } catch (Exception e) {
                        commandSender.sendMessage(lan.getString("offline").replace("&", "§"));
                    }
                } else if (strings.length == 1 && strings[0].equals("tpa")) {
                    //   /cn tp
                    //commandSender.sendMessage("§b参数不对诶?? 你是不是要用/cn tpa 名称 QAQ??");
                    commandSender.sendMessage(lan.getString("wrong_tpa").replace("&", "§"));
                }
                if (strings.length == 1 && strings[0].equals("tpaccept")) {
                    Tpa.acceptTeleportRequest((Player) commandSender);
                }


                //reload
                if (commandSender != null && commandSender.hasPermission("cn.reload"))
                    if (strings.length == 1 && strings[0].equals("reload")) {
                        plugin.reloadConfig();
                        YamlConfiguration.loadConfiguration(new File("settings.yml"));
                        YamlConfiguration.loadConfiguration(new File("language.yml"));
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            String p = player.getName();
                            player.setPlayerListName(plugin.getConfig().getString(p).replace("&", "§") + ChatColor.RESET);
                            player.setDisplayName(plugin.getConfig().getString(p).replace("&", "§") + ChatColor.RESET);
                        }
                        commandSender.sendMessage("§c[ChineseName]重载成功");
                    }

                assert commandSender != null;
                if (strings.length == 1 && !(strings[0].equals("set")
                                || strings[0].equals("reset")
                                || strings[0].equals("setother")
                                || strings[0].equals("check")
                                || strings[0].equals("givecard")
                                || strings[0].equals("usecard")
                                || strings[0].equals("help")
                                || strings[0].equals("reload")
                                || strings[0].equals("tp")
                                || strings[0].equals("tpa")
                                || strings[0].equals("tpaccept")
                        ))
                {

                    commandSender.sendMessage("§d小雨不能理解你在干啥诶?? 使用/cn help 1/2 ~~~ 查询帮助");
                }

                if (
                        (strings.length == 0) || (strings.length == 1
                                && strings[0].equals("help"))
                                || (strings.length == 2
                                && strings[0].equals("help")
                                && strings[1].equals("1"))
                ) {
                    commandSender.sendMessage("§b§l=========基础功能=========");
                    commandSender.sendMessage("&c[]内的是可选参数 比如[-t]就是临时的意思");
                    commandSender.sendMessage("§d/cn help 1/2 ~~~ 查询帮助");
                    commandSender.sendMessage("§a/cn set 名称 [-t] ~~~ 设置自己的[临时]中文名 - cn.set[.temporarily]");
                    commandSender.sendMessage("§a/cn setother ID 名称 [-t] ~~~ 设置他人的[临时]中文名 - cn.setother[.temporarily]");
                    commandSender.sendMessage("§a/cn reset ID [-t] ~~~ [临时]重置某人的中文名 - cn.reset[.temporarily]");
                    commandSender.sendMessage("§e§l你的支持是小雨最大的动力");
                    commandSender.sendMessage("§b§l========第二分之一页========");
                } else if (
                        strings.length == 2
                                && strings[0].equals("help")
                                && strings[1].equals("2")
                ) {
                    commandSender.sendMessage("§b§l=========增强功能=========");
                    commandSender.sendMessage("§d/cn help 1/2 ~~~ 查询帮助");
                    commandSender.sendMessage("§a/cn check ID ~~~ 查询某人的中文名 - cn.check");
                    commandSender.sendMessage("§a/cn givecard ID ~~~ 给予某人一张改名卡 - cn.getcard");
                    commandSender.sendMessage("§a/cn usecard ~~~ 食用一张改名卡 - cn.usecard");
                    commandSender.sendMessage("§a/cn tp 中文名 ~~~ 传送到某人身边 - cn.tp");
                    commandSender.sendMessage("§a/cn tpa 中文名 ~~~ 请求传送到某人身边 - cn.tpa");
                    commandSender.sendMessage("§a/cn tpaccept ~~~ 接受传送 - cn.tpaccept");
                    commandSender.sendMessage("§e§l你的支持是小雨最大的动力");
                    commandSender.sendMessage("§b§l========第二分之二页=======");
                }
                return true;
            }
        }



