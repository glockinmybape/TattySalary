package org.glockinmybape.tattysalary;

import java.util.logging.Logger;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.glockinmybape.tattysalary.commands.SalaryCommand;
import org.glockinmybape.tattysalary.utils.Metrics;

public final class TattySalary extends JavaPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy economy;
    public static TattySalary instance;
    private static Permission perms;

    public void onEnable() {
        instance = this;
        if (this.setupEconomy() && this.setupPermissions()) {
            new Metrics(this, 12538);
            this.setupPermissions();
            this.sendStartedInfo();
            this.saveDefaultConfig();
            this.registerCommands();
        } else {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", this.getDescription().getName()));
            this.getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void registerCommands() {
        this.getCommand("salary").setExecutor(new SalaryCommand());
    }

    private boolean setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        } else {
            RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                return false;
            } else {
                economy = (Economy)rsp.getProvider();
                return economy != null;
            }
        }
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = this.getServer().getServicesManager().getRegistration(Permission.class);
        perms = (Permission)rsp.getProvider();
        return perms != null;
    }

    private void sendStartedInfo() {
        Logger log = Bukkit.getLogger();
        log.info("§b");
        log.info("§b .----------------------------------------------------------. ");
        log.info("§b| .-------------------------------------------------------. |");
        log.info("§b| |             \t\t\t\t\t\t");
        log.info("§b| |            §7Плагин: §bTattySalary§8| §7Версия: §b1.0                ");
        log.info("§b| |        §7Создан для §bTattyWorld §8- §7Разработал: §bglockinmybape\t");
        log.info("§b| |                    §bvk.com/TattyWorld");
        log.info("§b| |             \t\t\t\t\t\t");
        log.info("§b| '-------------------------------------------------------'§b|");
        log.info("§b'-----------------------------------------------------------'");
        log.info("§b");
    }

    public static Economy getEconomy() {
        return economy;
    }

    public static Permission getPermissions() {
        return perms;
    }
}
