package ir.maedehhz.final_project_spring.service.order;

import ir.maedehhz.final_project_spring.dto.order.OrderFilteringResponse;
import ir.maedehhz.final_project_spring.dto.order.OrderFindAllResponse;
import ir.maedehhz.final_project_spring.model.Order;

import java.util.List;

public interface OrderService {
    Order save(Order order, long subserviceId, long customerId);
    Order choosingExpert(long suggestionId);
    Order findById(long id);
    Order updateStatusToWaitingForExpertToVisit(long orderId);
    List<OrderFindAllResponse> findAllBySubserviceWaitingForExpertSuggestion(long subserviceId);
    Order updateStatusToStarted(long orderId);
    Order updateStatusToDone(long orderId);
    void updateStatusToPayed(long orderId);
    void reduce1ScoreFromExpertPerHour(long orderId);

    List<OrderFilteringResponse> filteringOrders(
            Long userId, String registrationDate, String orderStatus,
            Long serviceId, Long subserviceId
    );

}
