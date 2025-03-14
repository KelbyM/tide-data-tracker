package org.kelbymannigel.tidedatatracker;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Manages the MySQL database transactions.
 */
public class DatabaseManager {

    private static final EntityManagerFactory entityManagerFactory;

    // setup
    static {
        try {
            System.out.println("Checking classpath for persistence.xml...");
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            // checking the path to the persistence.xml file
            System.out.println("Found: " + classLoader.getResource("META-INF/persistence.xml"));

            // loading the database properties from database.properties file
            Properties properties = new Properties();
            try(InputStream input = classLoader.getResourceAsStream("database.properties")) {
                if(input == null) {
                    throw new IOException("Error: database.properties was not found.");
                }
                properties.load(input);
                System.out.println("Database properties have been loaded successfully.");
            }

            // initializing the EntityManagerFactory
            System.out.println("Initializing EntityManagerFactory...");
            entityManagerFactory = Persistence.createEntityManagerFactory("tide-data-tracker", properties);
            System.out.println("EntityManagerFactory initialized successfully.");

            System.out.println("Database URL: " + properties.getProperty("database.url"));
            System.out.println("Database User: " + properties.getProperty("database.user"));
            System.out.println("Database Password: " + properties.getProperty("database.password"));
        } catch (Exception e) {
            System.err.println("Failed to initialize EntityManagerFactory.");
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Returns a new EntityManager used to interact with the database.
     * @return The JPA EntityManager connected to the database.
     */
    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    /**
     * Closes the EntityManagerFactory.
     */
    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    /**
     * Adds a Day object to the database.
     * @param day The Day object.
     */
    public static void writeDay(Day day) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(day);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    /**
     * Adds all Day objects from a List to the database.
     * @param days The list of Day objects.
     */
    public static void writeDays(List<Day> days) {
        // get the DatabaseManager
        EntityManager entityManager = DatabaseManager.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        // write the days to the database
        try {
            transaction.begin();
            // persisting the Day objects
            for (Day day : days) {
                entityManager.persist(day);
            }
            // commiting the transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            // closing the EntityManager
            entityManager.close();
        }
    }

    /**
     * Gets all Day objects from the database.
     * @return An ArrayList containing Day objects.
     */
    public static ArrayList<Day> getYear() {
        EntityManager entityManager = DatabaseManager.getEntityManager();
        ArrayList<Day> year = new ArrayList<>();
        try {
            // read the all days from the database
            List<Day> days = entityManager.createQuery("SELECT d FROM Day d", Day.class).getResultList();
            // populate the ArrayList with the Day objects
            year.addAll(days);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return year;
    }

    /**
     * Deletes all data from the database.
     */
    public static void deleteAllData() {
        // get the DatabaseManager
        EntityManager entityManager = DatabaseManager.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            // deleting all data in the tables
            entityManager.createQuery("DELETE FROM TideData").executeUpdate();
            entityManager.createQuery("DELETE FROM Day").executeUpdate();
            entityManager.createQuery("DELETE FROM MoonData").executeUpdate();
            // commiting the transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            // closing the EntityManager
            entityManager.close();
        }
    }
}
