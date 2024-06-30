package ir.maedehhz.final_project_spring.service.order;

import ir.maedehhz.final_project_spring.model.Order;
import ir.maedehhz.final_project_spring.model.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    Order save(Order order, long subserviceId, long customerId);
    Order findById(long id);
    Order updatingOrderSuggestion(Order order);
    Order updateStatusToWaitingForExpertToVisit(long orderId);
    List<Order> findAllByExpertsSubservice(long expertId);
    List<Order> findAllByStatusWaitingForSuggestion();
    Order updateStatusToStarted(long orderId);
    Order updateStatusToDone(long orderId);
    Order updateStatusToPayed(long orderId);
    void reduce1ScoreFromExpertPerHour(long orderId);

}
