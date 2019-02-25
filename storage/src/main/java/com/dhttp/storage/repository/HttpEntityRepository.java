package com.dhttp.storage.repository;

import com.dhttp.storage.model.HttpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HttpEntityRepository extends JpaRepository<HttpEntity, String> {
}
