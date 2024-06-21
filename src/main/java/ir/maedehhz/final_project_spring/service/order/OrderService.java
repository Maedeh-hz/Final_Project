package ir.maedehhz.final_project_spring.service.order;

import ir.maedehhz.final_project_spring.model.Order;

public interface OrderService {
    Order save(Order order);
    Order findById(long id);
    Order updateStatusToWaitingForExpertToTake(long orderId);
    Order updateStatusToWaitingForExpertToVisit(long orderId);
    Order updateStatusToStarted(long orderId);
    Order updateStatusToDone(long orderId);
    Order updateStatusToPayed(long orderId);

}
