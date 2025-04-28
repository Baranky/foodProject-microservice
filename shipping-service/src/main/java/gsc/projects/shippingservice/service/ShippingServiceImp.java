package gsc.projects.shippingservice.service;

import gsc.projects.shippingservice.dto.OrderEventDto;
import gsc.projects.shippingservice.model.ShippingEvent;
import gsc.projects.shippingservice.rabbitmq.producer.ShippingProducer;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ShippingServiceImp {

    private final ShippingProducer shippingProducer;

    public ShippingServiceImp(ShippingProducer shippingProducer) {
        this.shippingProducer = shippingProducer;
    }

    public void sendOrderToUser(OrderEventDto orderEventDto){
        ShippingEvent shippingEvent = fromOrder(orderEventDto);
        shippingProducer.send(shippingEvent);
    }


    public ShippingEvent fromOrder(OrderEventDto orderEventDto){
        return ShippingEvent.builder()
                .uuidOrder(orderEventDto.uuidOrder())
                .trackingNumber(UUID.randomUUID())
                .userName(orderEventDto.userName())
                .userAddress(orderEventDto.userAddress())
                .userEmail(orderEventDto.userEmail())
                .restaurantName(orderEventDto.restaurantName())
                .restaurantEmail(orderEventDto.restaurantEmail())
                .build();
    }
}
