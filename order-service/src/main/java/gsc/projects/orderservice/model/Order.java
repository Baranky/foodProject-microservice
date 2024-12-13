package gsc.projects.orderservice.model;



import jakarta.persistence.ElementCollection;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("orders")
public class Order  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuidOrder= UUID.randomUUID();

    private String restaurantEmail;

    private String userEmail;

    @ElementCollection
    private Map<String, Double> foodAndQuantity;

}
