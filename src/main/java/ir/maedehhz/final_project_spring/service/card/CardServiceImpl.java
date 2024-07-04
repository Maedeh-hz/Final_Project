package ir.maedehhz.final_project_spring.service.card;

import ir.maedehhz.final_project_spring.exception.InvalidInputException;
import ir.maedehhz.final_project_spring.model.Card;
import ir.maedehhz.final_project_spring.repository.CardRepository;
import ir.maedehhz.final_project_spring.service.order.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository repository;
    private final OrderServiceImpl orderService;

    @Override
    public Card save(Card card, long orderId) {
        if (card.getCardNumber().length() !=16)
            throw new InvalidInputException("Card number should be 16 digits!");

        Card saved = repository.save(card);
        if (saved.getId()!=null)
            orderService.updateStatusToPayed(orderId);

        return saved;
    }
}
