package ir.maedehhz.final_project_spring.service.subservice;

import ir.maedehhz.final_project_spring.exception.DuplicateInfoException;
import ir.maedehhz.final_project_spring.exception.NotFoundException;
import ir.maedehhz.final_project_spring.exception.SameInfoException;
import ir.maedehhz.final_project_spring.model.Subservice;
import ir.maedehhz.final_project_spring.repository.SubserviceRepository;
import ir.maedehhz.final_project_spring.service.service.ServiceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubserviceServiceImpl implements SubserviceService{

    private final SubserviceRepository repository;
    private final ServiceServiceImpl serviceService;

    @Override
    public Subservice save(Subservice subservice) {
        if (repository.existsByName(subservice.getName()))
            throw new DuplicateInfoException(String.format("Subservice with name %s exists!", subservice.getName()));
        if (!serviceService.existsByServiceName(subservice.getService().getServiceName()))
            throw new NotFoundException(String.format("Service with name %s doesn't exist.",
                    subservice.getService().getServiceName()));
        return repository.save(subservice);
    }

    @Override
    public Subservice findById(long id) {
        return repository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Subservice with id %s couldn't be found.", id)));
    }

    @Override
    public Subservice updateDescription(long id, String newDescription) {
        Subservice byId = findById(id);
        if (byId.getDescription().equals(newDescription))
            throw new SameInfoException("The description of subservice is already this!");
        byId.setDescription(newDescription);
        return repository.save(byId);
    }

    @Override
    public Subservice updateBasePrice(long id, double newBasePrice) {
        Subservice byId = findById(id);
        if (byId.getBasePrice().equals(newBasePrice))
            throw new SameInfoException("The base price of subservice is already this!");
        byId.setBasePrice(newBasePrice);
        return repository.save(byId);
    }
}
