package com.example.hospitalproject.security.service;

import com.example.hospitalproject.security.node.Representative;
import com.example.hospitalproject.security.node.Role;
import com.example.hospitalproject.security.repository.RepresentativeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepresentativeService {
    private final RepresentativeRepository repo;
    private static final Logger LOG = LoggerFactory.getLogger(RepresentativeService.class);

    public RepresentativeService(RepresentativeRepository repo) {
        this.repo = repo;
    }


    public void createRepresentative(Role role){
        Representative representative = new Representative();
        representative.setRole(role);
        LOG.info("Representative {} created", role);
        repo.save(representative);
    }

    public List<Representative> getAll(){
        return repo.findAll();
    }
}
