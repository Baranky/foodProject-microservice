package gsc.projects.restaurantservice.dto;

import lombok.Builder;

import java.util.Map;
import java.util.UUID;

@Builder
public record OrderDto(
        Long id,
        UUID uuidOrder,
        Map<String, Double>foodAndQuantity
) {
}
