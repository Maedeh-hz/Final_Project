package ir.maedehhz.final_project_spring.service.card;

import ir.maedehhz.final_project_spring.model.Card;

public interface CardService {
    Card save(Card card, long orderId);
}
