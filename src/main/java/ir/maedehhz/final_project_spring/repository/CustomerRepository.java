package ir.maedehhz.final_project_spring.repository;

import ir.maedehhz.final_project_spring.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByUsername(String username);
    <R> R findByUsername(String username);
}
