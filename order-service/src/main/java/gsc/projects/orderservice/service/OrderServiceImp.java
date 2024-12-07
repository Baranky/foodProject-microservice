package gsc.projects.orderservice.service;


import gsc.projects.orderservice.dto.OrderCreateDto;
import gsc.projects.orderservice.dto.OrderDto;
import gsc.projects.orderservice.dto.OrderEvent;
import gsc.projects.orderservice.dto.UserDto;
import gsc.projects.orderservice.model.Order;
import gsc.projects.orderservice.rabbitmq.producer.OrderProducer;
import gsc.projects.orderservice.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service

public class OrderServiceImp {
    private  final APIClient apiClient;
    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public OrderServiceImp(APIClient apiClient, OrderRepository orderRepository, OrderProducer orderProducer) {
        this.apiClient = apiClient;
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
    }

    public OrderDto createOrder(OrderCreateDto orderCreateDto) {
       Order newOrder = fromCreateDto(orderCreateDto);
       orderRepository.save(newOrder);
       OrderDto orderDto = toDto(newOrder);
       orderProducer.sendOrder(toEvent(newOrder));
       return orderDto;
    }

    public OrderDto getOrderByUuid(UUID uuid) {
        Order existingOrder = orderRepository.findByUuidOrder(uuid);
        if(existingOrder == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        return toDto(existingOrder);
    }


    public List<OrderDto> getAllOrders(String userEmail) {
        return orderRepository.findByUserEmail(userEmail).stream()
                .map(this::toDto)
                .toList();
    }

    public OrderDto toDto(Order order){
        return OrderDto.builder()
                .uuidOrder(order.getUuidOrder())
                .restaurantEmail(order.getRestaurantEmail())
                .userEmail(order.getUserEmail())
                .foodAndQuantity(order.getFoodAndQuantity())
                .build();
    }


    public Order fromCreateDto(OrderCreateDto orderCreateDto){
       Order order=new Order();
       order.setUuidOrder(order.getUuidOrder());
       order.setRestaurantEmail(orderCreateDto.restaurantEmail());
       order.setUserEmail(orderCreateDto.userEmail());
       order.setFoodAndQuantity(orderCreateDto.foodAndQuantity());
       return order;
    }

    public OrderEvent toEvent(Order order){
        UserDto userDto = apiClient.getUser(order.getUserEmail());
        return OrderEvent.builder()
                .uuidOrder(order.getUuidOrder())
                .restaurantEmail(order.getRestaurantEmail())
                .userName(userDto.userName())
                .userAddress(userDto.userAddress())
                .userEmail(userDto.userEmail())
                .foodAndQuantity(order.getFoodAndQuantity())
                .build();
    }


}
