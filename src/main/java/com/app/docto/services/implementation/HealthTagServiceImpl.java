package com.app.docto.services.implementation;

import com.app.docto.beans.HealthTag;
import com.app.docto.dao.HealthTagRepository;
import com.app.docto.exception.ValidationException;
import com.app.docto.services.HealthTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthTagServiceImpl implements HealthTagService {
    @Autowired
    private HealthTagRepository healthTagRepository;

    @Override
    public void createHealthTag(HealthTag healthTag) {
        this.healthTagRepository.save(healthTag);
    }

    @Override
    public HealthTag getHealthTag(Long healthTagId) {
        return this.healthTagRepository.findById(healthTagId)
                .orElseThrow(() -> new ValidationException("health tag doesn't exists"));
    }

    @Override
    public List<HealthTag> getHealthTags() {
        return this.healthTagRepository.findAll();
    }
}
