package com.zyot.shyn.servicereleasemanagement.repository;

import com.zyot.shyn.servicereleasemanagement.model.ServiceEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ServiceRepository extends PagingAndSortingRepository<ServiceEntity, String> {
}
