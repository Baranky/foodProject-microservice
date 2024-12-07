package gsc.projects.restaurantservice.service;


import gsc.projects.restaurantservice.dto.*;
import gsc.projects.restaurantservice.model.Order;
import gsc.projects.restaurantservice.model.Restaurant;
import gsc.projects.restaurantservice.rabbit.producer.RestaurantProducer;
import gsc.projects.restaurantservice.repository.OrderRepository;
import gsc.projects.restaurantservice.repository.RestaurantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RestaurantServiceImp {


    private final RestaurantRepository restaurantRepository;
    private final RestaurantProducer restaurantProducer;
    private final OrderRepository orderRepository;

    public RestaurantServiceImp(RestaurantRepository restaurantRepository, RestaurantProducer restaurantProducer, OrderRepository orderRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantProducer = restaurantProducer;
        this.orderRepository = orderRepository;
    }


    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
        Restaurant existingRestaurant = restaurantRepository.findByRestaurantEmail(restaurantDto.getRestaurantEmail());
        if(existingRestaurant != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant already exists");
        }
        Restaurant newRestaurant = fromCreateDto(restaurantDto);
        restaurantRepository.save(newRestaurant);
        return toDto(newRestaurant);
    }


    public RestaurantDto getByName(String name) {
        Restaurant existingRestaurant = restaurantRepository.findByName(name.toUpperCase().trim());
        if(existingRestaurant == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This restaurant doesn't exists or wrong name");
        }
        return toDto(existingRestaurant);
    }

    public List<RestaurantDto> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(restaurant -> toDto(restaurant))
                .toList();
    }

    public void deleteById(Long id) {
        restaurantRepository.delete(restaurantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found")));
    }

    public List<OrderDto> getAllOrders(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"))
                .getOrderList().stream()
                .map(this::fromOrderToDto)
                .toList();
    }


    public void updateOrdersList(Long restaurantId, UUID uuidOrder) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        Optional<Order> orderOptional = restaurant.getOrderList().stream()
                .filter(order -> order.getUuidOrder().equals(uuidOrder))
                .findFirst();

        if (orderOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order ID not found");
        }

        Order orderToRemove = orderOptional.get();
        restaurant.getOrderList().remove(orderToRemove);
        restaurantRepository.save(restaurant);

        restaurantProducer.sendOrderToShipping(fromOrder(orderToRemove));
    }

    public void addOrder(OrderEvent orderEvent) {
        Restaurant restaurant = restaurantRepository.findByRestaurantEmail(orderEvent.restaurantEmail());
        if(restaurant == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found");
        }
        Order order = fromOrderEvent(orderEvent, restaurant);
        orderRepository.save(order);
        restaurant.getOrderList().add(order);
        restaurantRepository.save(restaurant);
    }


    public RestaurantDto toDto(Restaurant restaurant){
        return RestaurantDto.builder()
                .name(restaurant.getName())
                .restaurantEmail(restaurant.getRestaurantEmail())
                .address(restaurant.getAddress())
                .menu(restaurant.getMenu())
                .build();
    }

    public Restaurant fromCreateDto(RestaurantDto restaurantDto){
            Restaurant restaurant =new Restaurant();
            restaurant.setName(restaurantDto.getName());
            restaurant.setRestaurantEmail(restaurantDto.getRestaurantEmail());
            restaurant.setAddress(restaurantDto.getAddress());
            restaurant.setMenu(restaurantDto.getMenu());
            return restaurant;
    }

    public Order fromOrderEvent(OrderEvent orderEvent, Restaurant restaurant){
        Order order=new Order();
        order.setUuidOrder(orderEvent.uuidOrder());
        order.setUserAddress(orderEvent.userAddress());
        order.setUserEmail(orderEvent.userEmail());
        order.setUserName(orderEvent.userName());
        order.setFoodAndQuantity(orderEvent.foodAndQuantity());
        order.setRestaurant(restaurant);
        return order;
    }

    public OrderEventDto fromOrder(Order order){
        return OrderEventDto.builder()
                .uuidOrder(order.getUuidOrder())
                .restaurantName(order.getRestaurant().getName())
                .restaurantEmail(order.getRestaurant().getRestaurantEmail())
                .userAddress(order.getUserAddress())
                .userName(order.getUserName())
                .userEmail(order.getUserEmail())
                .foodAndQuantity(order.getFoodAndQuantity())
                .build();
    }

    public OrderDto fromOrderToDto(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .uuidOrder(order.getUuidOrder())
                .foodAndQuantity(order.getFoodAndQuantity())
                .build();
    }

}