package gsc.projects.securityservice.controller;

import gsc.projects.securityservice.dto.OrderCreateDto;
import gsc.projects.securityservice.service.OrderServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceImp orderServiceImp;

    public OrderController(OrderServiceImp orderServiceImp) {
        this.orderServiceImp = orderServiceImp;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrderCreateDto orderCreateDto){
        return new ResponseEntity<>(orderServiceImp.saveOrderToRedis(orderCreateDto), HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello World from Order Service");
    }

    @GetMapping("/uuid/{uuidOrder}")
    public ResponseEntity<?> getOrder(@PathVariable ("uuidOrder") UUID uuid){
        return ResponseEntity.ok(orderServiceImp.getOrderByUuid(uuid));
    }
}
