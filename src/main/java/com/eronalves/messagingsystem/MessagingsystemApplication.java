package com.eronalves.messagingsystem;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class MessagingsystemApplication implements CommandLineRunner {

  private final KafkaTemplate<String, String> kafkaTemplate;

  public MessagingsystemApplication(
      KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public static void main(String[] args) {
    SpringApplication.run(MessagingsystemApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("[PROGRAM] Type some text!!");
    try (var scanner = new Scanner(System.in)) {
      while (true) {
        String nextLine = scanner.nextLine();
        System.out.println("[PRODUCER] %s".formatted(nextLine));
        this.kafkaTemplate.send("test", nextLine);
      }
    }
  }

}
