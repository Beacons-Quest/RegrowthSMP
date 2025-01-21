package org.lushplugins.regrowthsmp.storage;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.bukkit.configuration.ConfigurationSection;

import javax.sql.DataSource;

public class MySQLStorage extends AbstractSQLStorage {

    @Override
    protected DataSource setupDataSource(ConfigurationSection config) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(config.getString("host"));
        dataSource.setPortNumber(config.getInt("port"));
        dataSource.setDatabaseName(config.getString("database"));
        dataSource.setUser(config.getString("user"));
        dataSource.setPassword(config.getString("password"));

        return dataSource;
    }

    @Override
    protected void initialiseDatabase() {
        runSQLResource("storage/mysql_setup.sql");
    }
}
