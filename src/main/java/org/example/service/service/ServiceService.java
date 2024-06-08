package org.example.service.service;

import org.example.base.service.BaseService;
import org.example.model.Service;

import java.util.List;

public interface ServiceService extends BaseService<Service, Long> {
    List<Service> loadAll();
}
