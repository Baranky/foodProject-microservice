package gsc.projects.shippingservice.rabbitmq.consumer;

import gsc.projects.shippingservice.dto.OrderEventDto;
import gsc.projects.shippingservice.service.ShippingServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component("rabbitOrderConsumer")
public class OrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);
    private final ShippingServiceImp shippingServiceImp;

    public OrderConsumer(ShippingServiceImp shippingServiceImp) {
        this.shippingServiceImp = shippingServiceImp;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.name}")
    public void consume(OrderEventDto orderEventDto){
        LOGGER.info(String.format("Order received in Shipping Service -> %s", orderEventDto.toString()));
        shippingServiceImp.sendOrderToUser(orderEventDto);
    }
}