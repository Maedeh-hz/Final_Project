package org.example.service.subservice;

import org.example.base.service.BaseService;
import org.example.model.Service;
import org.example.model.Subservice;

import java.util.List;
import java.util.Optional;

public interface Sub_serviceService extends BaseService<Subservice, Long>  {
    List<Subservice> loadAll();

    List<Subservice> loadAllByService(Service service);


}
