package org.glockinmybape.tattysalary.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.glockinmybape.tattysalary.TattySalary;

public class Utils {
    private static HashMap<String, Long> data = new HashMap();
    private static final FileConfiguration config;
    private static final String daysCase1;
    private static final String daysCase2;
    private static final String daysCase3;
    private static final String hoursCase1;
    private static final String hoursCase2;
    private static final String hoursCase3;
    private static final String minutesCase1;
    private static final String minutesCase2;
    private static final String minutesCase3;
    private static final String secondsCase1;
    private static final String secondsCase2;
    private static final String secondsCase3;

    public static void sendInfo(Player player) {
        ArrayList<String> INFO_MESSAGE = new ArrayList();
        INFO_MESSAGE.add(" ");
        INFO_MESSAGE.add("  &b&lTattySalary");
        INFO_MESSAGE.add("    &7Версия: &fv1.0");
        INFO_MESSAGE.add("    &7Выпуск плагина: &fTattyInc");
        INFO_MESSAGE.add("    &7Автор плагина: &fglockinmybape &8(vk.com/glockinmybape)");
        INFO_MESSAGE.add(" ");
        sendMessageList(player, INFO_MESSAGE);
    }

    public static void sendMessageList(Player player, ArrayList<String> list) {
        Iterator var2 = list.iterator();

        while(var2.hasNext()) {
            String message = (String)var2.next();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }

    }

    public static void sendMessageFromConfig(Player player, String path) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString(path).replace("%n", "\n").replace("%player%", player.getName())));
    }

    public static void sendMessageFromConfig(Player player, String path, String time) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString(path).replace("%n", "\n").replace("%player%", player.getName()).replace("%time%", time)));
    }

    public static void sendMessageFromConfig(Player player, String path, int salary) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString(path).replace("%n", "\n").replace("%player%", player.getName()).replace("%salary%", salary + "")));
    }

    public static String getCooldownTime(long seconds) {
        String str = "";
        int day = (int)TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (long)day * 24L;
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toHours(seconds) * 60L;
        long second = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.SECONDS.toMinutes(seconds) * 60L;
        if (day > 0) {
            str = str + choosePluralMerge((long)day, daysCase1, daysCase2, daysCase3) + " ";
        }

        if (hours > 0L) {
            str = str + choosePluralMerge(hours, hoursCase1, hoursCase2, hoursCase3) + " ";
        }

        if (minute > 0L) {
            str = str + choosePluralMerge(minute, minutesCase1, minutesCase2, minutesCase3) + " ";
        }

        if (second > 0L) {
            str = str + choosePluralMerge(second, secondsCase1, secondsCase2, secondsCase3) + " ";
        }

        return str.trim();
    }

    public static String choosePluralMerge(long number, String caseOne, String caseTwo, String caseFive) {
        String str = number + " ";
        number = Math.abs(number);
        if (number % 10L == 1L && number % 100L != 11L) {
            str = str + caseOne;
        } else if (number % 10L < 2L || number % 10L > 4L || number % 100L >= 10L && number % 100L < 20L) {
            str = str + caseFive;
        } else {
            str = str + caseTwo;
        }

        return str;
    }

    public static HashMap<String, Long> getData() {
        return data;
    }

    public static void setData(String key, Long value) {
        data.put(key, value);
    }

    static {
        config = TattySalary.instance.getConfig();
        daysCase1 = config.getString("messages.time.days.1");
        daysCase2 = config.getString("messages.time.days.2");
        daysCase3 = config.getString("messages.time.days.3");
        hoursCase1 = config.getString("messages.time.hours.1");
        hoursCase2 = config.getString("messages.time.hours.2");
        hoursCase3 = config.getString("messages.time.hours.3");
        minutesCase1 = config.getString("messages.time.minutes.1");
        minutesCase2 = config.getString("messages.time.minutes.2");
        minutesCase3 = config.getString("messages.time.minutes.3");
        secondsCase1 = config.getString("messages.time.seconds.1");
        secondsCase2 = config.getString("messages.time.seconds.2");
        secondsCase3 = config.getString("messages.time.seconds.3");
    }
}
