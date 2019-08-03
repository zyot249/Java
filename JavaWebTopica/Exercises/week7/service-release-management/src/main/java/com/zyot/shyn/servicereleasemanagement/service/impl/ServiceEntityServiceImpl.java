package com.zyot.shyn.servicereleasemanagement.service.impl;

import com.zyot.shyn.servicereleasemanagement.repository.ServiceRepository;
import com.zyot.shyn.servicereleasemanagement.service.ServiceEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceEntityServiceImpl implements ServiceEntityService {
    @Autowired
    private ServiceRepository serviceRepository;
}
