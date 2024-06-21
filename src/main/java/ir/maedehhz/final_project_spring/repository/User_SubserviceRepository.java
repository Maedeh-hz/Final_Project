package ir.maedehhz.final_project_spring.repository;

import ir.maedehhz.final_project_spring.model.Expert;
import ir.maedehhz.final_project_spring.model.Subservice;
import ir.maedehhz.final_project_spring.model.User_SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface User_SubserviceRepository extends JpaRepository<User_SubService, Long> {
    <S extends User_SubService> boolean existsByExpertAndSubservice(Expert expert, Subservice subservice);

    @Query("select u from User_SubService u where u.expert.id = ?1 and u.subservice.id = ?2")
    Optional<User_SubService> findByExpert_IdAndAndSubservice_Id(long expertId, long subserviceId);

    @Override
    void delete(User_SubService userSubService);
    List<User_SubService> findAllByExpert_Id(Long expert_id);
}
