package com.example.springbootproject;

import org.bson.Document;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

@Component
public class MongoDBHealthIndicator implements HealthIndicator {

    private final MongoClient mongoClient;

    public MongoDBHealthIndicator(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }
    @Override
    public Health health() {
        try {
            MongoDatabase adminDatabase = mongoClient.getDatabase("admin");
            Document pingResult = adminDatabase.runCommand(new Document("ping", 1));
            if (pingResult.getDouble("ok") == 1.0) {
                return Health.up().build();
            } else {
                return Health.down().withDetail("error", "MongoDB ping failed").build();
            }
        } catch (Exception e) {
            return Health.down(e).build();
        }
    }

    }

