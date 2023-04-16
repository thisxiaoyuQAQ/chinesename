package main;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.*;

public class SQLUtils {

    private static Connection connection;
    private static String ip;
    private static Integer port;
    private static String user;
    private static String password;
    private static String database_name;
    private static String table_name;
    private static Boolean ready = true;

    private static String fixed_url;


    /**
     * @author samyyc
     *
     * Prepare the mysql connection.
     * MUST be ran before running any function in this class.
     *
     * @return true if prepare successfully, false if error occurred.
     *
     */
    public static Boolean prepare() {
        try {
            File file = new File(main.getInstance().getDataFolder(), "settings.yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            ip = config.getString("database_ip");
            port = config.getInt("database_port");
            user = config.getString("database_user");
            password = config.getString("database_password");
            database_name = config.getString("database_name");
            table_name = config.getString("table_name");
            fixed_url = "jdbc:mysql://"+ip+":"+port+"/"+database_name+"??serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8";

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(fixed_url,user,password);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            ready = false;
        }
        return ready;
    }

    /**
     * @author samyyc
     *
     * Prepare the mysql connection.
     * This function prepare connection by parameters.
     * You can use SQLUtils.prepare() to auto prepare.
     * MUST be ran if you didn't use the prepare().
     *
     * @param ip database ip
     * @param port database port
     * @param user database user
     * @param password database password
     * @param database_name database name
     * @param table_name table name
     * @return true if prepare successfully, false if error occurred.
     */
    public static Boolean prepareByParameters(String ip, Integer port, String user, String password, String database_name, String table_name) {
        try {

            SQLUtils.ip = ip;
            SQLUtils.port = port;
            SQLUtils.user = user;
            SQLUtils.password = password;
            SQLUtils.database_name = database_name;
            SQLUtils.table_name = table_name;
            fixed_url = "jdbc:mysql://" + ip + ":" + port + "/" + database_name + "??serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8";

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(fixed_url, user, password);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            ready = false;
        }
        return ready;
    }

    /**
     * @author samyyc
     *
     * execute the sql statement.
     * Only for no result statement (e.g. INSERT...)
     *
     * @param sql the sql statement
     */
    public static void executeSQL(String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * @author samyyc
     *
     * execute the sql statement.
     * Only for returnable statment (e.g. SELECT...)
     *
     * @param sql sql statment
     * @return ResultSet when execute successfully, NULL WHEN ERROR OCCURRED.
     *
     */
    public static ResultSet executeSQLQuery(String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @author samyyc
     *
     * fast insert detail for ChineseName plugin (author xiaoyu).
     *
     * @param realName player's real name.
     * @param customName player's custom name.
     *
     * @return true if insert successfully, false if error occurred.
     */
    public static Boolean fastInsert(String realName, String customName) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS {table_name} (id INT AUTO_INCREMENT, real_name varchar(255) NOT NULL, custom_name varchar(255), primary key(id));".replace("{table_name}",table_name);
        String insertSQL = "INSERT INTO {table_name} (real_name,custom_name) VALUES ('{real_name}','{custom_name}'".replace("{table_name}",table_name).replace("{real_name}",realName).replace("{custom_name}",customName);
        try {
            PreparedStatement createTableStatement = connection.prepareStatement(createTableSQL);
            PreparedStatement insertContentStatement = connection.prepareStatement(insertSQL);
            createTableStatement.execute();
            insertContentStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     *
     * 以下所有方法均为自动生成
     *
     * */
    public static void setUser(String user) {
        SQLUtils.user = user;
    }

    public static void setTable_name(String table_name) {
        SQLUtils.table_name = table_name;
    }

    public static void setPort(Integer port) {
        SQLUtils.port = port;
    }

    public static void setPassword(String password) {
        SQLUtils.password = password;
    }

    public static void setIp(String ip) {
        SQLUtils.ip = ip;
    }

    public static void setDatabase_name(String database_name) {
        SQLUtils.database_name = database_name;
    }

    public static void setFixed_url(String fixed_url) {
        SQLUtils.fixed_url = fixed_url;
    }

    public static void setConnection(Connection connection) {
        SQLUtils.connection = connection;
    }

    public static String getUser() {
        return user;
    }

    public static String getTableName() {
        return table_name;
    }

    public static String getPassword() {
        return password;
    }

    public static String getIp() {
        return ip;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static String getFixed_url() {
        return fixed_url;
    }

    public static String getDatabaseName() {
        return database_name;
    }

    public static Integer getPort() {
        return port;
    }

    public static Boolean getReady() {
        return ready;
    }
}