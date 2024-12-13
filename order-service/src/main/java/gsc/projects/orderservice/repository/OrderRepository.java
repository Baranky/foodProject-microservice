package gsc.projects.orderservice.repository;


import gsc.projects.orderservice.model.Order;
import org.springframework.data.repository.CrudRepository;


public interface OrderRepository extends CrudRepository<Order, Long> {

}
