package ir.maedehhz.final_project_spring.repository;

import ir.maedehhz.final_project_spring.model.Order;
import ir.maedehhz.final_project_spring.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllBySubservice_IdAndStatus(Long subservice_id, OrderStatus status);
}
