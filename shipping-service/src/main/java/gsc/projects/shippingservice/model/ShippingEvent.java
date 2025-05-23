package gsc.projects.shippingservice.model;


import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingEvent {

    private UUID uuidOrder;
    private UUID trackingNumber;
    private String userName;
    private String userEmail;
    private String userAddress;
    private String restaurantName;
    private String restaurantEmail;

    @Override
    public String toString() {
        return "ShippingEvent{" +
                "uuidOrder=" + uuidOrder +
                ", trackingNumber=" + trackingNumber +
                ", userName='" + userName + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", restaurantEmail='" + restaurantEmail + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
