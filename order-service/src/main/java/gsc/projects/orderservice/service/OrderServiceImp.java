package gsc.projects.orderservice.service;


import gsc.projects.orderservice.dto.OrderCreateDto;
import gsc.projects.orderservice.dto.OrderDto;
import gsc.projects.orderservice.dto.OrderEvent;
import gsc.projects.orderservice.dto.UserDto;
import gsc.projects.orderservice.model.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderServiceImp {
    private  final APIClient apiClient;
    private final OrderProducer orderProducer;
    private  final RedisTemplate redisTemplate;

    public OrderServiceImp(APIClient apiClient, OrderProducer orderProducer, RedisTemplate redisTemplate) {
        this.apiClient = apiClient;
        this.orderProducer = orderProducer;
        this.redisTemplate = redisTemplate;
    }

    public OrderDto getOrderByUuid(UUID uuid) {
        String orderKey = "orders:" + uuid.toString();
        Map<String, String> orderData = redisTemplate.opsForHash().entries(orderKey);
        if (orderData == null || orderData.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bu UUID için herhangi bir sipariş bulunamadı: " + uuid);
        }
        Order order = mapHashToOrder(orderData);
        return toDto(order);
    }


    public OrderDto saveOrderToRedis(OrderCreateDto orderCreateDto) {
        Order newOrder = fromCreateDto(orderCreateDto);
        String redisKey = "orders:" + newOrder.getUuidOrder();
        redisTemplate.opsForHash().putAll(redisKey, mapOrderToHash(newOrder));
        orderProducer.sendOrder(toEvent(newOrder));
        return toDto(newOrder);
    }


    private Map<String, Object> mapOrderToHash(Order order) {
        Map<String, Object> hash = new HashMap<>();
        hash.put("id",order.getId());
        hash.put("uuidOrder", order.getUuidOrder().toString());
        hash.put("restaurantEmail", order.getRestaurantEmail());
        hash.put("userEmail", order.getUserEmail());
        hash.put("foodAndQuantity", order.getFoodAndQuantity());
        return hash;
    }

    private Order mapHashToOrder(Map<String, String> orderData) {
            Order order = new Order();

            String uuidOrder = removeQuotes(orderData.get("uuidOrder"));
            String userEmail = removeQuotes(orderData.get("userEmail"));
            String restaurantEmail = removeQuotes(orderData.get("restaurantEmail"));
            Object foodAndQuantityObj = orderData.get("foodAndQuantity");
            Map<String, Double> foodAndQuantityMap = (Map<String, Double>) foodAndQuantityObj;

            order.setUuidOrder(UUID.fromString(uuidOrder));
            order.setUserEmail(userEmail);
            order.setRestaurantEmail(restaurantEmail);
            order.setFoodAndQuantity(foodAndQuantityMap);
            return order;
    }

    private String removeQuotes(String value) {
        return value != null ? value.replace("\"", "\"") : null;
    }

    private OrderDto toDto(Order order){
        return OrderDto.builder()
                .uuidOrder(order.getUuidOrder())
                .restaurantEmail(order.getRestaurantEmail())
                .userEmail(order.getUserEmail())
                .foodAndQuantity(order.getFoodAndQuantity())
                .build();
    }

    private Order fromCreateDto(OrderCreateDto orderCreateDto){
       Order order=new Order();
       order.setUuidOrder(order.getUuidOrder());
       order.setRestaurantEmail(orderCreateDto.restaurantEmail());
       order.setUserEmail(orderCreateDto.userEmail());
       order.setFoodAndQuantity(orderCreateDto.foodAndQuantity());
       return order;
    }

    private OrderEvent toEvent(Order order){
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
