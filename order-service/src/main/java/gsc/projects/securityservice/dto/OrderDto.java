package gsc.projects.securityservice.dto;


import lombok.Builder;

import java.util.Map;
import java.util.UUID;


@Builder
public record OrderDto(
         UUID uuidOrder,
         String restaurantEmail,
         String userEmail,
         Map<String, Double> foodAndQuantity
) { }
