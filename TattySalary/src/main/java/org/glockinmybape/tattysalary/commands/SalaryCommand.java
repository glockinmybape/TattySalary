package org.glockinmybape.tattysalary.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.glockinmybape.tattysalary.TattySalary;
import org.glockinmybape.tattysalary.utils.Utils;

public class SalaryCommand implements CommandExecutor {
    private static final FileConfiguration config;

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Command available only for players!");
            return true;
        } else {
            Player player = (Player)sender;
            if (args.length > 0) {
                Utils.sendInfo(player);
                return true;
            } else if (!player.hasPermission("tattysalary.salary")) {
                Utils.sendMessageFromConfig(player, "messages.no-permission");
                return true;
            } else {
                String playerName = player.getName();
                String groupName = TattySalary.getPermissions().getPrimaryGroup(player);
                if (!TattySalary.instance.getConfig().getConfigurationSection("groups").getKeys(false).contains(groupName.toLowerCase())) {
                    Utils.sendMessageFromConfig(player, "messages.no-salary-specified");
                    return true;
                } else {
                    int salary = config.getInt("groups." + groupName.toLowerCase() + ".salary");
                    if (!Utils.getData().containsKey(playerName)) {
                        TattySalary.getEconomy().depositPlayer(player, (double)salary);
                        Utils.setData(playerName, System.currentTimeMillis());
                        Utils.sendMessageFromConfig(player, "messages.received-salary", salary);
                        return true;
                    } else {
                        long timeout = System.currentTimeMillis() - (Long)Utils.getData().get(playerName);
                        int groupTimeout = config.getInt("groups." + groupName.toLowerCase() + ".cooldown") * 1000;
                        String time = Utils.getCooldownTime((long)(groupTimeout / 1000) - timeout / 1000L);
                        if (timeout < (long)groupTimeout) {
                            Utils.sendMessageFromConfig(player, "messages.cooldown", time);
                            return true;
                        } else {
                            TattySalary.getEconomy().depositPlayer(player, (double)salary);
                            Utils.setData(playerName, System.currentTimeMillis());
                            Utils.sendMessageFromConfig(player, "messages.received-salary", salary);
                            return true;
                        }
                    }
                }
            }
        }
    }

    static {
        config = TattySalary.instance.getConfig();
    }
}
