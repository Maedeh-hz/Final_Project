package ir.maedehhz.final_project_spring.repository;

import ir.maedehhz.final_project_spring.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    <S extends Service> boolean existsByServiceName(String serviceName);

    @Override
    List<Service> findAll();
}
