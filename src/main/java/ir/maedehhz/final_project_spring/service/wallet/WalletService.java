package ir.maedehhz.final_project_spring.service.wallet;

import ir.maedehhz.final_project_spring.model.Order;
import ir.maedehhz.final_project_spring.model.User;
import ir.maedehhz.final_project_spring.model.Wallet;

public interface WalletService {
    void register(User user);

    Wallet findByUserId(long userId);

    Wallet payingFromWallet(Order order);

    Wallet showWalletToUser(long userId);

}
