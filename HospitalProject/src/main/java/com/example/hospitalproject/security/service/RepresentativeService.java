package com.example.hospitalproject.security.service;


import com.example.hospitalproject.security.node.Representative;
import com.example.hospitalproject.security.node.Role;
import com.example.hospitalproject.security.repository.RepresentativeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepresentativeService {
    private final RepresentativeRepository repo;

    public RepresentativeService(RepresentativeRepository repo) {
        this.repo = repo;
    }


    public void createRepresentative(Role role){
        Representative representative = new Representative();
        representative.setRole(role);
        repo.save(representative);
    }

    public List<Representative> getAll(){
        return repo.findAll();
    }
}
