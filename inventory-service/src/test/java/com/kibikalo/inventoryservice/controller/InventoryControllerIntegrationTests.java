package com.kibikalo.inventoryservice.controller;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Slf4j
public class InventoryControllerIntegrationTests {

    @BeforeAll
    public static void beforeAll() {
        PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
                .withDatabaseName("inventory-service")
                .withUsername("postgres")
                .withPassword("postgres");

        postgresContainer.start();

        System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresContainer.getPassword());
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCheckIsInStock() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory/"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
