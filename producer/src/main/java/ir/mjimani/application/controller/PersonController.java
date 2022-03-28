package ir.mjimani.application.controller;

import ir.mjimani.application.domain.Person;
import ir.mjimani.application.domain.Response;
import ir.mjimani.application.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/person")
public class PersonController {

    @Autowired
    private Producer producer;

    @PostMapping("publish")
    ResponseEntity<Response<Boolean>> publish(@RequestBody Person person) {
        producer.publish(person);
        return ResponseEntity.ok().body(new Response<>(true).addMessage("Your message was published successfully."));
    }

    @PostMapping("publish/reply")
    ResponseEntity<Response<Person>> publishReply(@RequestBody Person person) throws Exception {
        return ResponseEntity.ok().body(new Response<>(producer.publishReply(person)).addMessage("Your message was published successfully."));
    }
}
