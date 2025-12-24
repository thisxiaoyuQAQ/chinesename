package com.cryxiaoyu.chinesename.metrics;

import com.cryxiaoyu.chinesename.Main;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

public class PlaceHolderApiHooker extends PlaceholderExpansion {
    private final Main plugin;

    public PlaceHolderApiHooker(Main plugin) {
        this.plugin = plugin;
    }

    public String getAuthor() {
        return "小雨";
    }

    public String getIdentifier() {
        return "cn";
    }

    public String getVersion() {
        return "2.66666 卡拉比丘限定版";
    }

    public boolean persist() {
        return true;
    }

    public String onRequest(OfflinePlayer player, String params) {
        if (this.plugin.getConfig().getString(player.getPlayer().getName()).isEmpty()){
            return params.equalsIgnoreCase("name") ? player.getPlayer().getName(): null;

        }
        if (this.plugin.getConfig().getString(player.getName()).isEmpty()){
            return params.equalsIgnoreCase("name") ? player.getName() + ChatColor.RESET : null;
        }else{
            return params.equalsIgnoreCase("name") ? this.plugin.getConfig().getString(player.getPlayer().getName()).replaceAll("&", "§") + ChatColor.RESET : null;
        }

    }
}
