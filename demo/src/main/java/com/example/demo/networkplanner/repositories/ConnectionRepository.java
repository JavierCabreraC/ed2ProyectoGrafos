package com.example.demo.networkplanner.repositories;

import com.example.demo.networkplanner.models.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {}
