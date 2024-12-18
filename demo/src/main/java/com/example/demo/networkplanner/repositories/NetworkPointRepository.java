package com.example.demo.networkplanner.repositories;

import com.example.demo.networkplanner.models.NetworkPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NetworkPointRepository extends JpaRepository<NetworkPoint, Long> {}
