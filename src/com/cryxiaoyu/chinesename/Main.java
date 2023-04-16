package com.cryxiaoyu.chinesename;

import com.cryxiaoyu.chinesename.command.NameChanger;
import com.cryxiaoyu.chinesename.metrics.Metrics;
import com.cryxiaoyu.chinesename.metrics.PlaceHolderApiHooker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Main extends JavaPlugin implements Listener {
    public static ConcurrentHashMap<String , String> playerName = new ConcurrentHashMap<>();
    private static Main instance;
    public Main() {}
    public static Main getInstance() {return instance;}

    public void onEnable() {
        instance = this;

        File file = new File(Main.getInstance().getDataFolder(),"settings.yml");
        File language = new File(Main.getInstance().getDataFolder(), "language.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        //配置文件
        if (!file.exists()){
            Main.getInstance().saveResource("settings.yml",false);
        }
        if (!language.exists()){
            Main.getInstance().saveResource("language.yml",false);
        }

        //PAPI接入
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            (new PlaceHolderApiHooker(this)).register();
        }

        //bstats.org接入
        Metrics metrics = new Metrics(this, 15336);

        this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();

        this.getCommand("cn").setExecutor(new NameChanger());
        this.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getConsoleSender().sendMessage("§b================================");
        Bukkit.getConsoleSender().sendMessage("§b[ChineseName]中文名插件已加载");
        Bukkit.getConsoleSender().sendMessage("§b 作者:小雨        QQ:3066156386");
        Bukkit.getConsoleSender().sendMessage("§b   强烈建议添加反馈群:426996480");
        Bukkit.getConsoleSender().sendMessage("§b爱发电 https://afdian.net/@ixiaoyu");
        Bukkit.getConsoleSender().sendMessage("§b================================");
    }

    public void onDisable() {
        System.out.println("[ChineseName]插件已卸载");
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e) {
        File file = new File(Main.getInstance().getDataFolder(),"settings.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (this.getConfig().getString(e.getPlayer().getName()) != null) {
            e.getPlayer().setDisplayName(this.getConfig().getString(e.getPlayer().getName()).replaceAll("&","§") + ChatColor.RESET);
            playerName.put(this.getConfig().getString(e.getPlayer().getName()).replaceAll("&.",""), e.getPlayer().getName());
            if (config.getBoolean("tablist")) {
                e.getPlayer().setPlayerListName(this.getConfig().getString(e.getPlayer().getName()).replaceAll("&","§") + ChatColor.RESET);
            }
        }
    }
    @EventHandler
    public void PlayerQuit(PlayerQuitEvent e) {

    }
}
