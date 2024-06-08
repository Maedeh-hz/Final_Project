package org.example.service.subservice;

import org.example.base.service.BaseService;
import org.example.model.Subservice;

import java.util.List;

public interface SubserviceService extends BaseService<Subservice, Long> {

    List<Subservice> loadAll();
}
