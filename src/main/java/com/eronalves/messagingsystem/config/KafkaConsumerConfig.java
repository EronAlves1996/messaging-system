package com.eronalves.messagingsystem.config;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

/**
 * KafkaConsumerConfig
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapAddress;

  public ConsumerFactory<String, String> consumerFactory(String groupId) {
    return new DefaultKafkaConsumerFactory<>(Map.of(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress,
        ConsumerConfig.GROUP_ID_CONFIG, groupId,
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class));
  }

  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
      String groupId) {
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory(groupId));
    return factory;
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> fooKafkaListenerContainerFactory() {
    return kafkaListenerContainerFactory("foo");
  }
}
