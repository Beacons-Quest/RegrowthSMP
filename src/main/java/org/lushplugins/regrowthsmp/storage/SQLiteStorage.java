package org.lushplugins.regrowthsmp.storage;

import org.bukkit.configuration.ConfigurationSection;
import org.lushplugins.regrowthsmp.RegrowthSMP;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class SQLiteStorage extends MySQLStorage {
    private static final String DATABASE_PATH = new File(RegrowthSMP.getInstance().getDataFolder(), "data.db").getAbsolutePath();

    @Override
    protected Connection conn() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + DATABASE_PATH);
        } catch (SQLException e) {
            RegrowthSMP.getInstance().log(Level.SEVERE, "An error occurred whilst getting a connection: ", e);
            return null;
        }
    }

    @Override
    protected DataSource setupDataSource(ConfigurationSection config) {
        return null;
    }

    @Override
    protected void initialiseDatabase() {
        runSQLResource("storage/sqlite_setup.sql");
    }
}
