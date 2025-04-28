package gsc.projects.securityservice.repository;


import gsc.projects.securityservice.model.Order;
import org.springframework.data.repository.CrudRepository;


public interface OrderRepository extends CrudRepository<Order, Long> {

}
