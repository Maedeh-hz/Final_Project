package ir.maedehhz.final_project_spring.service.order;

import ir.maedehhz.final_project_spring.exception.InvalidDateForOrderException;
import ir.maedehhz.final_project_spring.exception.NotFoundException;
import ir.maedehhz.final_project_spring.model.Order;
import ir.maedehhz.final_project_spring.model.enums.OrderStatus;
import ir.maedehhz.final_project_spring.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository repository;
    @Override
    public Order save(Order order) {
        if (order.getToDoDateAndTime().isBefore(LocalDateTime.now()))
            throw new InvalidDateForOrderException("The entered date is before today!");

        return repository.save(order);
    }

    @Override
    public Order findById(long id) {
        return repository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Order with id %s couldn't be found.", id)));
    }

    @Override
    public Order updateStatusToWaitingForExpertToTake(long orderId) {
        Order byId = findById(orderId);
        byId.setStatus(OrderStatus.WAITING_FOR_EXPERT_TO_TAKE);
        return repository.save(byId);
    }

    @Override
    public Order updateStatusToWaitingForExpertToVisit(long orderId) {
        Order byId = findById(orderId);
        byId.setStatus(OrderStatus.WAITING_FOR_EXPERT_TO_VISIT);
        return repository.save(byId);
    }

    @Override
    public Order updateStatusToStarted(long orderId) {
        Order byId = findById(orderId);
        byId.setStatus(OrderStatus.STARTED);
        return repository.save(byId);
    }

    @Override
    public Order updateStatusToDone(long orderId) {
        Order byId = findById(orderId);
        byId.setStatus(OrderStatus.DONE);
        return repository.save(byId);
    }

    @Override
    public Order updateStatusToPayed(long orderId) {
        Order byId = findById(orderId);
        byId.setStatus(OrderStatus.PAYED);
        return repository.save(byId);
    }
}
