package ir.mjimani.application.consumer;

import ir.mjimani.application.domain.Person;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @KafkaListener(topics = "MyTopic")
    public void listen(Person person) {
        System.out.println("MyTopic :: Person Received = " + person);
    }

    @SendTo
    @KafkaListener(topics = "MyTopicReply")
    public Person listenReply(Person person) {
        System.out.println("MyTopicReply :: Person Received = " + person);
        person.setFirstName(" ***** Changed by consumeR *****" + person.getFirstName() + " ***** Changed by consumeR *****");
        person.setLastName(" ***** Changed by consumeR *****" + person.getLastName() + " ***** Changed by consumeR *****");
        return person;
    }

}
