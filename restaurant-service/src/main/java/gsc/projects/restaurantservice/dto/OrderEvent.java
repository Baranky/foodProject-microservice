package gsc.projects.restaurantservice.dto;

import java.util.Map;
import java.util.UUID;

public record OrderEvent(
        UUID uuidOrder,
        String restaurantEmail,
        String userName,
        String userAddress,
        String userEmail,
        Map<String, Double> foodAndQuantity
) {
}
