package ir.maedehhz.final_project_spring.repository;

import ir.maedehhz.final_project_spring.model.Subservice;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubserviceRepository extends JpaRepository<Subservice, Long> {

    <S extends Subservice> boolean existsByName(String name);
    <S extends Subservice, R> R findByName(String name);

    @Override
    List<Subservice> findAll();
}
