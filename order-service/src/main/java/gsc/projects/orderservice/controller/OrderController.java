package gsc.projects.orderservice.controller;


import gsc.projects.orderservice.dto.OrderCreateDto;
import gsc.projects.orderservice.service.OrderServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/order")

public class OrderController {

    private final OrderServiceImp orderServiceImp;

    public OrderController(OrderServiceImp orderServiceImp) {
        this.orderServiceImp = orderServiceImp;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrderCreateDto orderCreateDto){
        return new ResponseEntity<>(orderServiceImp.createOrder(orderCreateDto), HttpStatus.CREATED);
    }

    @GetMapping("/uuid/{uuidOrder}")
    public ResponseEntity<?> getOrder(@PathVariable ("uuidOrder") UUID uuid){
        return ResponseEntity.ok(orderServiceImp.getOrderByUuid(uuid));
    }

    @GetMapping("/{userEmail}")
    public ResponseEntity<?> getOrders(@PathVariable ("userEmail") String userEmail){
        return ResponseEntity.ok(orderServiceImp.getAllOrders(userEmail));
    }
}
