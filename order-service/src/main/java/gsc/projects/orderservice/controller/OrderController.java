package gsc.projects.orderservice.controller;


import gsc.projects.orderservice.dto.OrderCreateDto;
import gsc.projects.orderservice.model.Order;
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
        return new ResponseEntity<>(orderServiceImp.saveOrderToRedis(orderCreateDto), HttpStatus.CREATED);
    }

    @GetMapping("/{uuidOrder}")
    public ResponseEntity<?> getOrder(@PathVariable ("uuidOrder") UUID uuid){
        return ResponseEntity.ok(orderServiceImp.getOrderByUuid(uuid));
    }

}
