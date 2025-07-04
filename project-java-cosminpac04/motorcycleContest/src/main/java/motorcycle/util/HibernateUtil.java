package motorcycle.util;

import motorcycle.model.Participant;
import motorcycle.model.Race;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.io.FileReader;
import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Load db.config file
                Properties dbConfig = new Properties();
                try {
                    dbConfig.load(new FileReader("db.config"));
                    String dbPath = dbConfig.getProperty("db_path");
                    
                    // Create registry builder with properties
                    StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
                    Properties settings = new Properties();
                    
                    // Database settings
                    settings.put(Environment.DRIVER, "org.sqlite.JDBC");
                    settings.put(Environment.URL, "jdbc:sqlite:" + dbPath);
                    settings.put(Environment.DIALECT, "org.hibernate.community.dialect.SQLiteDialect");
                    
                    // Additional settings
                    settings.put(Environment.SHOW_SQL, "true");
                    settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                    settings.put(Environment.HBM2DDL_AUTO, "update");
                    
                    registryBuilder.applySettings(settings);
                    
                    // Create registry
                    StandardServiceRegistry registry = registryBuilder.build();



                    try {
                        // Create metadata sources and build session factory
                        MetadataSources sources = new MetadataSources(registry)
                            .addAnnotatedClass(Participant.class)
                            .addAnnotatedClass(Race.class);
                        
                        sessionFactory = sources.buildMetadata().buildSessionFactory();
                    } catch (Exception e) {
                        // If an error occurs, destroy registry
                        StandardServiceRegistryBuilder.destroy(registry);
                        throw e;
                    }
                } catch (Exception e) {
                    System.err.println("Error loading db.config: " + e.getMessage());
                    e.printStackTrace();
                }
            } catch (Exception e) {
                System.err.println("Error initializing Hibernate: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
} 