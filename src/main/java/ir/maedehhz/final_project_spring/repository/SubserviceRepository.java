package ir.maedehhz.final_project_spring.repository;

import ir.maedehhz.final_project_spring.model.Subservice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubserviceRepository extends JpaRepository<Subservice, Long> {

    <S extends Subservice> boolean existsByName(String name);
    <S extends Subservice, R> R findByName(String name);
}
