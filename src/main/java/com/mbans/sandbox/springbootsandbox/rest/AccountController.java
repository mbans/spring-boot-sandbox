package com.mbans.sandbox.springbootsandbox.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lumarmacy1 on 14/11/2018.
 */
@RestController
@RequestMapping(path = "/accounts")
public class AccountController implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private static List<Account> accounts = new ArrayList<>();

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${topic.name")
    private String topicName;

    @RequestMapping(path = "/{id}")
    public ResponseEntity<Account> get(@PathVariable int id) {

        if(id < 0) {
            throw new RuntimeException("Account id should be > 0");
        }

        return ResponseEntity.ok().body(accounts.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null));
    }

    @RequestMapping(path = "/publish/{message}")
    public void publish(@PathVariable String message) {
        log.info("Publishing {} onto topic",message);
        jmsTemplate.convertAndSend(message);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        accounts.add(new Account(1, "Martin"));
        accounts.add(new Account(2, "Lucy"));
        accounts.add(new Account(3, "Benedict"));
        accounts.add(new Account(4, "Joyce"));
    }

}
