package ir.maedehhz.final_project_spring.service.order;

import ir.maedehhz.final_project_spring.dto.order.OrderFilteringResponse;
import ir.maedehhz.final_project_spring.dto.order.OrderFindAllResponse;
import ir.maedehhz.final_project_spring.exception.CouldNotUpdateException;
import ir.maedehhz.final_project_spring.exception.DateMismatchException;
import ir.maedehhz.final_project_spring.exception.NotFoundException;
import ir.maedehhz.final_project_spring.mapper.order.OrderMapper;
import ir.maedehhz.final_project_spring.model.*;
import ir.maedehhz.final_project_spring.model.enums.OrderStatus;
import ir.maedehhz.final_project_spring.repository.OrderRepository;
import ir.maedehhz.final_project_spring.service.customer.CustomerServiceImpl;
import ir.maedehhz.final_project_spring.service.expert.ExpertServiceImpl;
import ir.maedehhz.final_project_spring.service.service.ServiceServiceImpl;
import ir.maedehhz.final_project_spring.service.subservice.SubserviceServiceImpl;
import ir.maedehhz.final_project_spring.service.suggestion.SuggestionServiceImpl;
import ir.maedehhz.final_project_spring.service.user.UserServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository repository;

    private final UserServiceImpl userService;

    private final ExpertServiceImpl expertService;

    private final ServiceServiceImpl serviceService;

    private final SubserviceServiceImpl subserviceService;

    private final CustomerServiceImpl customerService;

    private final SuggestionServiceImpl suggestionService;

    private final EntityManager entityManager;

    @Override
    public Order save(Order order, long subserviceId) {
        Subservice subservice = subserviceService.findById(subserviceId);
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerService.findByEmail(currentUserEmail);
        order.setCustomer(customer);
        order.setSubservice(subservice);

        if (order.getToDoDateAndTime().isBefore(LocalDateTime.now()))
            throw new DateMismatchException("The entered date is before today!");

        order.setRegisterDate(LocalDateTime.now());
        order.setStatus(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION);
        order.setService(subservice.getService());
        return repository.save(order);
    }

    @Override
    public Order choosingExpert(long suggestionId) {
        Order order = updatingOrdersSuggestion(suggestionId);

        if (order.getExpert() == null)
            throw new CouldNotUpdateException("couldn't register the suggestion for order!");

        Order updated = updateStatusToWaitingForExpertToVisit(order.getId());

        suggestionService.confirmSuggestionAcceptance(suggestionId);

        return updated;
    }

    private Order updatingOrdersSuggestion(Long suggestionId){
        Suggestion suggestion = suggestionService.findById(suggestionId);

        Order orderById = findById(suggestion.getOrder().getId());

        if (orderById.getExpert() != null)
            throw new CouldNotUpdateException("You have already chose Expert for your order!");

        orderById.setExpert(suggestion.getExpert());

        return repository.save(orderById);
    }

    @Override
    public Order findById(long id) {
        return repository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Order with id %s couldn't be found.", id)));
    }

    @Override
    public Order updateStatusToWaitingForExpertToVisit(long orderId) {
        Order byId = findById(orderId);
        byId.setStatus(OrderStatus.WAITING_FOR_EXPERT_TO_VISIT);
        return repository.save(byId);
    }

    @Override
    public List<OrderFindAllResponse> findAllBySubserviceWaitingForExpertSuggestion(long subserviceId) {
        List<Order> all = repository.findAllBySubservice_IdAndStatus(subserviceId, OrderStatus.WAITING_FOR_EXPERT_SUGGESTION);
        if (all.isEmpty())
            throw new NotFoundException("No order found for this subservice!");
        List<OrderFindAllResponse> allResponses = new ArrayList<>();
        all.forEach(order ->
            allResponses.add(OrderMapper.INSTANCE.modelToOrderFindAllResponse(order))
        );

        return allResponses;
    }

    @Override
    public Order updateStatusToStarted(long orderId) {
        Order byId = findById(orderId);
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!byId.getCustomer().getEmail().equals(currentUserEmail))
            throw new CouldNotUpdateException("You can't update the status of this order!");
        byId.setStatus(OrderStatus.STARTED);
        Order saved = repository.save(byId);
        if (saved.getId()!=null)
            reduce1ScoreFromExpertPerHour(saved.getId());

        return saved;
    }

    @Override
    public Order updateStatusToDone(long orderId) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Order byId = findById(orderId);
        if (!byId.getCustomer().getEmail().equals(currentUserEmail))
            throw new CouldNotUpdateException("You can't update the status of this order!");
        byId.setStatus(OrderStatus.DONE);
        return repository.save(byId);
    }

    @Override
    public void updateStatusToPayed(long orderId) {
        Order byId = findById(orderId);
        byId.setStatus(OrderStatus.PAYED);
        repository.save(byId);
    }

    @Override
    public void reduce1ScoreFromExpertPerHour(long orderId) {
        Order order = findById(orderId);
        if (!order.getToDoDateAndTime().isBefore(LocalDateTime.now()))
            throw new DateMismatchException("Its not the orders date!");

        long between = ChronoUnit.HOURS.between(order.getToDoDateAndTime(), LocalDateTime.now());
        double newScore;
        Expert byUsername = expertService.findByEmail(order.getExpert().getUsername());
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

    @Override
    public List<Order> findAllByExpert_Id(Long expert_id) {

        return null;
    }

    @Override
    public List<Order> findAllByCustomer_Id(Long customer_id) {

        return null;
    }

    @Override
    public List<OrderFilteringResponse> filteringOrders(Long userId,
                                                        String registrationDate,
                                                        String orderStatus,
                                                        Long serviceId, Long subserviceId
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);

        Root<Order> root = cq.from(Order.class);
        List<Predicate> predicates = new ArrayList<>();

        if (userId != null){
            User user = userService.findById(userId);
            checkUsersType(user, predicates, cb, root);
        }

        if (registrationDate != null)
            predicates.add(cb.like(root.get("registerDate"), registrationDate));

        if (orderStatus != null)
            predicates.add(cb.equal(root.get("status"), orderStatus.toUpperCase(Locale.ROOT)));

        if (subserviceId != null){
            Subservice subservice = subserviceService.findById(subserviceId);
            predicates.add(cb.equal(root.get("subservice"), subservice));
        }

        if (serviceId != null) {
            ir.maedehhz.final_project_spring.model.Service service = serviceService.findById(serviceId);
            predicates.add(cb.equal(root.get("service"), service));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        List<Order> resultList = entityManager.createQuery(cq).getResultList();
        List<OrderFilteringResponse> responses = new ArrayList<>();
        resultList.forEach(order -> responses.add(OrderMapper.INSTANCE.modelToOrderFilteringResponse(order)));
        return responses;
    }

    private static void checkUsersType(User user, List<Predicate> predicates, CriteriaBuilder cb, Root<Order> root) {
        if (user.getDtype().equals("Customer"))
            predicates.add(cb.equal(root.get("customer"), user));

        if (user.getDtype().equals("Expert")){
            predicates.add(cb.equal(root.get("expert"), user));
        }
    }


}
