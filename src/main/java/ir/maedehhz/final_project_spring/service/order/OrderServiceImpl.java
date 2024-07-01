package ir.maedehhz.final_project_spring.service.order;

import ir.maedehhz.final_project_spring.dto.order.OrderFindAllResponse;
import ir.maedehhz.final_project_spring.exception.IneligibleObjectException;
import ir.maedehhz.final_project_spring.exception.InvalidDateForOrderException;
import ir.maedehhz.final_project_spring.exception.NotFoundException;
import ir.maedehhz.final_project_spring.mapper.order.OrderMapper;
import ir.maedehhz.final_project_spring.model.Customer;
import ir.maedehhz.final_project_spring.model.Expert;
import ir.maedehhz.final_project_spring.model.Order;
import ir.maedehhz.final_project_spring.model.Subservice;
import ir.maedehhz.final_project_spring.model.enums.OrderStatus;
import ir.maedehhz.final_project_spring.repository.OrderRepository;
import ir.maedehhz.final_project_spring.service.customer.CustomerServiceImpl;
import ir.maedehhz.final_project_spring.service.expert.ExpertServiceImpl;
import ir.maedehhz.final_project_spring.service.subservice.SubserviceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository repository;

    private final ExpertServiceImpl expertService;

    private final SubserviceServiceImpl subserviceService;

    private final CustomerServiceImpl customerService;

    @Override
    public Order save(Order order, long subserviceId, long customerId) {
        Subservice subservice = subserviceService.findById(subserviceId);
        Customer customer = customerService.findById(customerId);
        order.setCustomer(customer);
        order.setSubservice(subservice);

        if (order.getToDoDateAndTime().isBefore(LocalDateTime.now()))
            throw new InvalidDateForOrderException("The entered date is before today!");

        order.setRegisterDate(LocalDate.now());
        order.setStatus(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION);
        return repository.save(order);
    }

    @Override
    public Order findById(long id) {
        return repository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Order with id %s couldn't be found.", id)));
    }

    @Override
    public Order updatingOrderSuggestion(Order order) {
        return repository.save(order);
    }

    @Override
    public Order updateStatusToWaitingForExpertToVisit(long orderId) {
        Order byId = findById(orderId);
        byId.setStatus(OrderStatus.WAITING_FOR_EXPERT_TO_VISIT);
        return repository.save(byId);
    }

    @Override
    public List<OrderFindAllResponse> findAllBySubservice(long subserviceId) {
        List<Order> all = repository.findAllBySubservice_Id(subserviceId);

        if (all.isEmpty())
            throw new NotFoundException("No order found for this subservice!");

        List<OrderFindAllResponse> allResponses = new ArrayList<>();
        all.forEach(order ->
            allResponses.add(OrderMapper.INSTANCE.modelToOrderFindAllResponse(order))
        );

        return allResponses;
    }

    @Override
    public List<Order> findAllByStatusWaitingForSuggestion() {
        if (!repository.existsByStatus(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION))
            throw new NotFoundException("There is no order with such status!");

        return repository.findAllByStatus(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION);
    }

    @Override
    public Order updateStatusToStarted(long orderId) {
        Order byId = findById(orderId);
        byId.setStatus(OrderStatus.STARTED);
        Order saved = repository.save(byId);
        if (saved.getId()!=null)
            reduce1ScoreFromExpertPerHour(saved.getId());

        return saved;
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

    @Override
    public void reduce1ScoreFromExpertPerHour(long orderId) {
        Order order = findById(orderId);
        if (!order.getToDoDateAndTime().isBefore(LocalDateTime.now()))
            throw new IneligibleObjectException("Its not the orders date!");
        if (!order.getStatus().equals(OrderStatus.WAITING_FOR_EXPERT_TO_VISIT))
            throw new IneligibleObjectException("Order is not waiting for expert!");

        long between = ChronoUnit.HOURS.between(order.getToDoDateAndTime(), LocalDateTime.now());
        double newScore;
        Expert byUsername = expertService.findByUsername(order.getSuggestion().getExpert().getUsername());
        if (between>=1){
            newScore = byUsername.getScore()-between;
            if (newScore<0)
                newScore = 0;
            byUsername.setScore(newScore);
        }
        Expert updated = expertService.updateScore(byUsername);
        if (updated.getScore().equals(0D))
            expertService.updateStatusToUnverified(byUsername.getId());
    }


}
