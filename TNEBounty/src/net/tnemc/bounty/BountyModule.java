package net.tnemc.bounty;

import com.github.tnerevival.core.SaveManager;
import com.github.tnerevival.core.db.SQLDatabase;
import net.tnemc.bounty.command.BountyCommand;
import net.tnemc.bounty.listeners.InventoryCloseListener;
import net.tnemc.bounty.listeners.PlayerDeathListener;
import net.tnemc.bounty.listeners.PlayerJoinListener;
import net.tnemc.bounty.menu.AmountSelectionMenu;
import net.tnemc.bounty.menu.BountyViewMenu;
import net.tnemc.core.TNE;
import net.tnemc.core.common.module.Module;
import net.tnemc.core.common.module.ModuleInfo;
import net.tnemc.core.menu.Menu;
import net.tnemc.core.menu.impl.CurrencySelectionMenu;
import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The New Economy Minecraft Server Plugin
 * <p>
 * Created by creatorfromhell on 6/28/2019.
 * <p>
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */
@ModuleInfo(
    name = "Bounty",
    author = "creatorfromhell",
    version = "0.1.0"
)
public class BountyModule extends Module {

  private static BountyModule instance;

  public BountyModule() {
    instance = this;
  }

  @Override
  public void load(TNE tne, String version) {

    commands.add(new BountyCommand(tne));

    Bukkit.getServer().getPluginManager().registerEvents(new InventoryCloseListener(tne), tne);
    Bukkit.getServer().getPluginManager().registerEvents(new PlayerDeathListener(tne), tne);
    Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinListener(tne), tne);
    TNE.logger().info("Bounty Module loaded!");
  }


  @Override
  public Map<String, List<String>> getTables() {
    Map<String, List<String>> tables = new HashMap<>();

    List<String> h2 = new ArrayList<>();
    //H2 Tables
    h2.add("CREATE TABLE IF NOT EXISTS " + BountyData.prefix + "_BOUNTY (" +
        "`bounty_id` VARCHAR(36) NOT NULL UNIQUE," +
        "`bounty_target` VARCHAR(36) NOT NULL," +
        "`bounty_benefactor` VARCHAR(36) NOT NULL," +
        "`bounty_created` BIGINT(60)," +
        "`bounty_head` BOOLEAN NOT NULL DEFAULT 0," +
        "`bounty_finished` BIGINT(60)," +
        "`bounty_claimed` BOOLEAN NOT NULL DEFAULT 0," +
        "`bounty_claimant` VARCHAR(36) NOT NULL," +
        "`bounty_currency` BOOLEAN NOT NULL DEFAULT 0," +
        "`bounty_currency_name` VARCHAR(70) NOT NULL," +
        "`bounty_world` VARCHAR(70) NOT NULL," +
        "`bounty_amount` DECIMAL(49,4) DEFAULT 0," +
        "`bounty_reward` TEXT NOT NULL" +
        ") ENGINE = INNODB;");

    h2.add("CREATE TABLE IF NOT EXISTS " + BountyData.prefix + "_BOUNTY_HUNTER (" +
        "`hunter_id` VARCHAR(36) NOT NULL UNIQUE," +
        "`hunter_experience` BIGINT(60)," +
        "`hunter_level` BIGINT(60)" +
        ") ENGINE = INNODB;");

    h2.add("CREATE TABLE IF NOT EXISTS " + BountyData.prefix + "_BOUNTY_REWARD (" +
        "`hunter_id` VARCHAR(36) NOT NULL UNIQUE," +
        "`hunter_rewards` TEXT NOT NULL" +
        ") ENGINE = INNODB;");

    tables.put("h2", h2);

    List<String> mysql = new ArrayList<>();

    //MySQL Tables
    mysql.add("CREATE TABLE IF NOT EXISTS " + BountyData.prefix + "_BOUNTY (" +
        "`bounty_id` VARCHAR(36) NOT NULL UNIQUE," +
        "`bounty_target` VARCHAR(36) NOT NULL," +
        "`bounty_benefactor` VARCHAR(36) NOT NULL," +
        "`bounty_created` BIGINT(60)," +
        "`bounty_head` BOOLEAN NOT NULL DEFAULT 0," +
        "`bounty_finished` BIGINT(60)," +
        "`bounty_claimed` BOOLEAN NOT NULL DEFAULT 0," +
        "`bounty_claimant` VARCHAR(36) NOT NULL," +
        "`bounty_currency` BOOLEAN NOT NULL DEFAULT 0," +
        "`bounty_currency_name` VARCHAR(70) NOT NULL," +
        "`bounty_world` VARCHAR(70) NOT NULL," +
        "`bounty_amount` DECIMAL(49,4) DEFAULT 0," +
        "`bounty_reward` TEXT NOT NULL" +
        ") ENGINE = INNODB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;");

    mysql.add("CREATE TABLE IF NOT EXISTS " + BountyData.prefix + "_BOUNTY_HUNTER (" +
        "`hunter_id` VARCHAR(36) NOT NULL UNIQUE," +
        "`hunter_experience` BIGINT(60)," +
        "`hunter_level` BIGINT(60)" +
        ") ENGINE = INNODB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;");

    mysql.add("CREATE TABLE IF NOT EXISTS " + BountyData.prefix + "_BOUNTY_REWARD (" +
        "`hunter_id` VARCHAR(36) NOT NULL UNIQUE," +
        "`hunter_rewards` TEXT NOT NULL" +
        ") ENGINE = INNODB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;");

    tables.put("mysql", mysql);

    return tables;
  }

  /**
   * Used to perform any data loading, manipulation, layout updating, etc.
   *
   * @param saveManager An instance of TNE's Save Manager
   */
  @Override
  public void enableSave(SaveManager saveManager) {
    for(String str : getTables().get("h2")) {
      SQLDatabase.open(SQLDatabase.getDataSource());

      try {
        SQLDatabase.getDb().getConnection().createStatement().executeUpdate(str);
      } catch (SQLException e) {
        e.printStackTrace();
      }

      SQLDatabase.close();
    }
  }

  @Override
  public Map<String, Menu> registerMenus(TNE pluginInstance) {
    Map<String, Menu> menus = new HashMap<>();
    menus.put("bounty_currency_selection", new CurrencySelectionMenu("bounty_currency_selection", "bounty_amount_selection"));
    menus.put("bounty_amount_selection", new AmountSelectionMenu("bounty_amount_selection"));
    menus.put("bounty_view_player", new BountyViewMenu());

    return menus;
  }

  public static BountyModule instance() {
    return instance;
  }
}