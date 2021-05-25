package com.app.docto.dao;

import com.app.docto.beans.HealthTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthTagRepository extends JpaRepository<HealthTag, Long> {
}
