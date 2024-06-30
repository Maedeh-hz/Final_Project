package ir.maedehhz.final_project_spring.service.comment;

import ir.maedehhz.final_project_spring.exception.DuplicateInfoException;
import ir.maedehhz.final_project_spring.exception.InvalidInputException;
import ir.maedehhz.final_project_spring.exception.NotFoundException;
import ir.maedehhz.final_project_spring.exception.NotYourBusinessException;
import ir.maedehhz.final_project_spring.model.Comment;
import ir.maedehhz.final_project_spring.model.Order;
import ir.maedehhz.final_project_spring.model.enums.OrderStatus;
import ir.maedehhz.final_project_spring.repository.CommentRepository;
import ir.maedehhz.final_project_spring.service.order.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository repository;
    private final OrderServiceImpl orderService;

    @Override
    public Comment save(Comment comment, long orderId) {
        Order order = orderService.findById(orderId);

        if (repository.existsByOrder(order))
            throw new DuplicateInfoException("You have already registered a comment for this order!");

        if (!order.getStatus().equals(OrderStatus.DONE))
            throw new NotYourBusinessException("Order is not done yet, you can't register comment for it!");

        if (comment.getExpertScore()<1 || comment.getExpertScore()>5)
            throw new InvalidInputException("Expert score should be not null " +
                    "and greater than 1 and less than 5!");

        comment.setOrder(order);
        comment.setRegistrationDate(LocalDateTime.now());
        return repository.save(comment);
    }

    @Override
    public Comment findByOrder(Order order) {
        return repository.findByOrder(order).orElseThrow(() -> new NotFoundException
                (String.format("No comments found for Order with id %s ." , order.getId())));
    }

    @Override
    public double viewExpertsScoreByOrder(long orderId) {
        Order order = orderService.findById(orderId);
        Comment byOrder = findByOrder(order);
        return byOrder.getExpertScore();
    }
}
