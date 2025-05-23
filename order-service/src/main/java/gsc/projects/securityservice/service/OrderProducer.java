package gsc.projects.securityservice.service;


import gsc.projects.securityservice.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrder(OrderEvent orderEvent){
        LOGGER.info(String.format("Order sent to Restaurant Service -> %s", orderEvent.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, orderEvent);
    }

}