// DatabaseConfig.java - Configuration management
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class DatabaseConfig {
    private static Properties properties = new Properties();

    static {
        try {
            // Load database configuration from properties file
            FileInputStream fis = new FileInputStream("database.properties");
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            // Fallback to default values for demo
            properties.setProperty("db.url", "jdbc:mysql://localhost:3306/studentdb");
            properties.setProperty("db.username", "root");
            properties.setProperty("db.password", "Isuru@123");
            properties.setProperty("db.driver", "com.mysql.cj.jdbc.Driver");
        }
    }

    public static String getUrl() { return properties.getProperty("db.url"); }
    public static String getUsername() { return properties.getProperty("db.username"); }
    public static String getPassword() { return properties.getProperty("db.password"); }
    public static String getDriver() { return properties.getProperty("db.driver"); }
}