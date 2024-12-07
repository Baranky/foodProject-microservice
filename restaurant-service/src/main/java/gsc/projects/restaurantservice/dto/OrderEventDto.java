package gsc.projects.restaurantservice.dto;

import lombok.Builder;

import java.util.Map;
import java.util.UUID;

@Builder
public record OrderEventDto(
        UUID uuidOrder,
        String restaurantName,
        String restaurantEmail,
        String userName,
        String userAddress,
        String userEmail,
        Map<String, Double>foodAndQuantity
) {
}
