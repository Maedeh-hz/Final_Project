package ir.maedehhz.final_project_spring.service.wallet;

import ir.maedehhz.final_project_spring.exception.CouldNotUpdateException;
import ir.maedehhz.final_project_spring.exception.NotFoundException;
import ir.maedehhz.final_project_spring.model.Order;
import ir.maedehhz.final_project_spring.model.User;
import ir.maedehhz.final_project_spring.model.Wallet;
import ir.maedehhz.final_project_spring.repository.WalletRepository;
import ir.maedehhz.final_project_spring.service.order.OrderServiceImpl;
import ir.maedehhz.final_project_spring.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService{

    private final WalletRepository repository;
    private final UserServiceImpl userService;

    @Override
    public void register(User user) {
        Wallet wallet = Wallet.builder().balance(0D).user(user).build();

        repository.save(wallet);
    }

    @Override
    public Wallet findByUserId(long userId) {
        User user = userService.findById(userId);
        return repository.findByUser(user).orElseThrow(() -> new NotFoundException(
                String.format("Wallet with user id %s not found!", userId)
        ));
    }

    @Override
    public Wallet payingFromWallet(Order order) {
        Long customerId = order.getCustomer().getId();
        Wallet wallet = findByUserId(customerId);
        Double ordersPrice = order.getSuggestion().getPrice();
        
        if (wallet.getBalance() < ordersPrice)
            throw new CouldNotUpdateException("Insufficient credit!");
        
        double newBalance = wallet.getBalance() - ordersPrice;
        wallet.setBalance(newBalance);
        Wallet saved = repository.save(wallet);

        double expertShare = (ordersPrice * 100) / 70;
        Wallet expertWallet = findByUserId(order.getSuggestion().getExpert().getId());
        expertWallet.setBalance(expertWallet.getBalance()+expertShare);
        repository.save(expertWallet);

        return saved;
    }

    @Override
    public Wallet showWalletToUser(long userId) {
        return findByUserId(userId);
    }
}
