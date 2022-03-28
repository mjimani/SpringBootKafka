package ir.mjimani.application.producer;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.mjimani.application.domain.Person;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

@Component
public class Producer {

    @Qualifier("replyTemplate")
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate;

    @Value("${spring.kafka.topic}")
    private String topic;

    @Value("${spring.kafka.topicReply}")
    private String topicReply;

    public void publish(Person person) {
        try {
            kafkaTemplate.send(topic, person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Person publishReply(Person person) throws Exception {
        try {
            ProducerRecord<String, Object> record = new ProducerRecord<>(topicReply, person);
            RequestReplyFuture<String, Object, Object> future = replyingKafkaTemplate.sendAndReceive(record);
            return (Person) future.get().value();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }
}
