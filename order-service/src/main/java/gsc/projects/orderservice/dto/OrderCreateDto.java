package gsc.projects.orderservice.dto;


import java.util.Map;
import java.util.UUID;

public record OrderCreateDto(
    UUID uuidOrder,
    String restaurantEmail,
    String userEmail,
    Map<String, Double> foodAndQuantity){
}
