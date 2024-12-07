package gsc.projects.orderservice.dto;

import lombok.Builder;

import java.util.Map;
import java.util.UUID;

@Builder
public record OrderEvent(
         UUID uuidOrder,
         String restaurantEmail,
         String userName,
         String userAddress,
         String userEmail,
         Map<String, Double> foodAndQuantity
) { }
