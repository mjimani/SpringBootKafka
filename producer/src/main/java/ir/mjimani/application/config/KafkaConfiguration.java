package ir.mjimani.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@EnableKafka
@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.consumer.group-id}")
    public String groupId;

    @Bean
    public KafkaTemplate<String, Object> replyTemplate(ProducerFactory<String, Object> pf, ConcurrentKafkaListenerContainerFactory<String, Object> factory) {
        KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<>(pf);
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setReplyTemplate(kafkaTemplate);
        return kafkaTemplate;
    }

    @Bean
    public ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate(ProducerFactory<String, Object> pf, ConcurrentKafkaListenerContainerFactory<String, Object> factory) {
        ConcurrentMessageListenerContainer<String, Object> replyContainer = factory.createContainer("MyTopicReplyTest");
        replyContainer.getContainerProperties().setMissingTopicsFatal(false);
        replyContainer.getContainerProperties().setGroupId(groupId);
        return new ReplyingKafkaTemplate<>(pf, replyContainer);
    }
}