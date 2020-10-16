package com.fastwok.crawler;

import com.fastwok.crawler.services.isservice.CreateTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = {"com.fastwok.crawler.entities"})
@EnableJpaRepositories(basePackages = {"com.fastwok.crawler.repository"})
@EnableTransactionManagement
@Slf4j
@EnableScheduling
public class CrawlerFastworkApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrawlerFastworkApplication.class, args);
    }

}
