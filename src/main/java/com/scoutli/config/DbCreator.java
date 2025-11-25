package com.scoutli.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DbCreator {

    public static void main(String[] args) {
        System.out.println("--------------------------------------------------");
        System.out.println("Running DbCreator to check/create database...");
        System.out.println("--------------------------------------------------");

        try {
            Properties props = new Properties();
            // Load application.properties
            // Assuming running from project root or backend dir, path might vary.
            // We'll try to find it in src/main/resources
            try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
                props.load(input);
            } catch (Exception e) {
                System.err.println("Could not load application.properties: " + e.getMessage());
                return;
            }

            String username = props.getProperty("quarkus.datasource.username");
            String password = props.getProperty("quarkus.datasource.password");
            String url = props.getProperty("quarkus.datasource.jdbc.url");

            if (url == null) {
                System.err.println("quarkus.datasource.jdbc.url not found in properties.");
                return;
            }

            String dbName = url.substring(url.lastIndexOf("/") + 1);
            String baseUrl = url.substring(0, url.lastIndexOf("/") + 1);
            String postgresUrl = baseUrl + "postgres";

            System.out.println("Connecting to postgres to check DB: " + dbName);

            try (Connection connection = DriverManager.getConnection(postgresUrl, username, password);
                 Statement statement = connection.createStatement()) {

                ResultSet resultSet = statement.executeQuery("SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'");
                if (!resultSet.next()) {
                    System.out.println("Database '" + dbName + "' does not exist. Creating...");
                    statement.executeUpdate("CREATE DATABASE " + dbName);
                    System.out.println("Database '" + dbName + "' created successfully.");
                } else {
                    System.out.println("Database '" + dbName + "' already exists.");
                }

            }
        } catch (Exception e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("--------------------------------------------------");
    }
}
