package gsc.projects.restaurantservice.controller;


import gsc.projects.restaurantservice.dto.RestaurantDto;
import gsc.projects.restaurantservice.service.RestaurantServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/restaurant")

public class RestaurantController {

    private final RestaurantServiceImp restaurantServiceImp;

    public RestaurantController(RestaurantServiceImp restaurantServiceImp) {
        this.restaurantServiceImp = restaurantServiceImp;
    }
    @GetMapping("/test")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello World from restaurant Service");
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody RestaurantDto restaurantDto){
        return new ResponseEntity<>(restaurantServiceImp.createRestaurant(restaurantDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getRestaurant(@RequestParam ("name") String name){
        return ResponseEntity.ok(restaurantServiceImp.getByName(name));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(restaurantServiceImp.getAllRestaurants());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRestaurant(@RequestParam ("id") Long id){
        restaurantServiceImp.deleteById(id);
        return ResponseEntity.ok("Restaurant deleted successfully");
    }



    @GetMapping("/{id}/order/{uuidOrder}")
    public ResponseEntity<?> updateOrders(@PathVariable ("id") Long id, @PathVariable ("uuidOrder") UUID uuidOrder){
        restaurantServiceImp.updateOrdersList(id, uuidOrder);
        return ResponseEntity.ok("Order successfully deleted from list");
    }
    @GetMapping("/{id}/orders")
    public ResponseEntity<?> getOrders(@PathVariable ("id") Long id){
        return ResponseEntity.ok(restaurantServiceImp.getAllOrders(id));
    }

}