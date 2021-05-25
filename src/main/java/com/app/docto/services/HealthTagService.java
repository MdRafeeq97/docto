package com.app.docto.services;

import com.app.docto.beans.HealthTag;
import java.util.List;

public interface HealthTagService {
    void createHealthTag(HealthTag healthTag);

    HealthTag getHealthTag(Long healthTagId);

    List<HealthTag> getHealthTags();
}
