package com.example.demo.networkplanner.models;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "network_plans")
public class NetworkPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String algorithm;
    private Double totalCost;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "plan_points",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "point_id")
    )
    private List<NetworkPoint> points;

    public NetworkPlan() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public List<NetworkPoint> getPoints() {
        return points;
    }

    public void setPoints(List<NetworkPoint> points) {
        this.points = points;
    }
}
