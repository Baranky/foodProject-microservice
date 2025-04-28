package gsc.projects.shippingservice.dto;


import lombok.Builder;

import java.util.Map;
import java.util.UUID;

@Builder
public record OrderEventDto(
        UUID uuidOrder,
        String restaurantName,
        String restaurantEmail,
        String userAddress,
        String userName,
        String userEmail,
        Map<String, Double> foodAndQuantity

){ }
