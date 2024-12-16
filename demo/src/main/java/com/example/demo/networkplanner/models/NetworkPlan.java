package com.example.demo.networkplanner.models;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "network_plans")
public class NetworkPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String algoritmo;
    private Double costoTotal;

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

    public String getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(String algoritmo) {
        this.algoritmo = algoritmo;
    }

    public Double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(Double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public List<NetworkPoint> getPoints() {
        return points;
    }

    public void setPoints(List<NetworkPoint> points) {
        this.points = points;
    }
}
