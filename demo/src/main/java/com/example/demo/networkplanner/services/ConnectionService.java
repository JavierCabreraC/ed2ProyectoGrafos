package com.example.demo.networkplanner.services;

import com.example.demo.networkplanner.models.Connection;
import com.example.demo.networkplanner.repositories.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ConnectionService {
    @Autowired
    private ConnectionRepository connectionRepository;

    public List<Connection> getAllConnections() {
        return connectionRepository.findAll();
    }
}
