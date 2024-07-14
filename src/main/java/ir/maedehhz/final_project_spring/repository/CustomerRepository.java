package ir.maedehhz.final_project_spring.repository;

import ir.maedehhz.final_project_spring.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    boolean existsByUsername(String username);

    Optional<Customer> findByUsername(String username);
}
