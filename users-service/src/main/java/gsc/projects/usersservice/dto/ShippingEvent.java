package gsc.projects.usersservice.dto;

import java.util.UUID;



public record ShippingEvent (
     UUID uuidOrder,
     UUID trackingNumber,
     String userName,
     String userAddress,
     String restaurantEmail,
     String userEmail
){
}
