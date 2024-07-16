package ir.maedehhz.final_project_spring.repository;

import ir.maedehhz.final_project_spring.model.User;
import ir.maedehhz.final_project_spring.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUser(User user);
}
