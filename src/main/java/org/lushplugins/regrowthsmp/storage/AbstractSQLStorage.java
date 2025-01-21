package org.lushplugins.regrowthsmp.storage;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.configuration.ConfigurationSection;
import org.lushplugins.regrowthsmp.RegrowthSMP;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

public abstract class AbstractSQLStorage implements Storage {
    private DataSource dataSource;

    @Override
    public void enable(ConfigurationSection config) {
        this.dataSource = setupDataSource(config);
        testDataSourceConnection();
        initialiseDatabase();
    }

    @Override
    public JsonObject loadSMPUserJson(UUID uuid) {
        try (Connection conn = conn(); PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM regrowthsmp_users WHERE uuid = ?;"
        )) {
            stmt.setString(1, uuid.toString());

            ResultSet results = stmt.executeQuery();
            if (results.next()) {
                String jsonRaw = results.getString("data");
                return jsonRaw != null ? JsonParser.parseString(jsonRaw).getAsJsonObject() : null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void saveSMPUserJson(UUID uuid, JsonObject json) {
        try (Connection conn = conn(); PreparedStatement stmt = conn.prepareStatement(
            "REPLACE INTO regrowthsmp_users (uuid, username, data) VALUES (?, ?, ?);"
        )) {
            stmt.setString(1, uuid.toString());
            JsonElement usernameElement = json.get("username");
            stmt.setString(2, usernameElement != null ? usernameElement.getAsString() : null);
            stmt.setString(3, json.toString());

            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void runSQLResource(String filePath) {
        String setup;
        try (InputStream in = AbstractSQLStorage.class.getClassLoader().getResourceAsStream(filePath)) {
            setup = new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining(""));
        } catch (IOException e) {
            RegrowthSMP.getInstance().getLogger().log(Level.SEVERE, "Failed to read sql resource: ", e);
            return;
        }

        String[] statements = setup.split("\\|");
        for (String statement : statements) {
            try (Connection conn = conn(); PreparedStatement stmt = conn.prepareStatement(statement)) {
                stmt.execute();
            } catch (SQLException e) {
                RegrowthSMP.getInstance().getLogger().log(Level.SEVERE, "Failed to execute sql from sql resource: ", e);
            }
        }
    }

    protected Connection conn() {
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            RegrowthSMP.getInstance().log(Level.SEVERE, "An error occurred whilst getting a connection: ", e);
            return null;
        }
    }

    protected DataSource getDataSource() {
        return dataSource;
    }

    protected abstract DataSource setupDataSource(ConfigurationSection config);

    protected void testDataSourceConnection() {
        try (Connection conn = conn()) {
            if (!conn.isValid(1000)) {
                throw new SQLException("Could not establish database connection.");
            }
        } catch (SQLException e) {
            RegrowthSMP.getInstance().log(Level.SEVERE, "An error occurred while testing the data source ", e);
        }
    }

    protected void initialiseDatabase() {}
}
