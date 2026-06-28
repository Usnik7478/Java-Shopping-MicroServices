package com.usnik.order_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usnik.order_service.dto.InventoryOrderingDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

	@Value("${spring.kafka.bootstrap-servers:localhost:9092}")
	private String bootstrapServers;

	@Bean
	public ProducerFactory<String, InventoryOrderingDTO> producerFactory(ObjectMapper objectMapper) {
		Map<String, Object> configProps = new HashMap<>();

		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);


		configProps.put(ProducerConfig.ACKS_CONFIG, "all");
		configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
		configProps.put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE);
		configProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5);

		StringSerializer keySerializer = new StringSerializer();
		JsonSerializer<InventoryOrderingDTO> valueSerializer = new JsonSerializer<>(objectMapper);

		return new DefaultKafkaProducerFactory<>(configProps, keySerializer, valueSerializer);
	}

	@Bean
	public KafkaTemplate<String, InventoryOrderingDTO> kafkaTemplate(ProducerFactory<String, InventoryOrderingDTO> producerFactory) {
		return new KafkaTemplate<>(producerFactory);
	}
}