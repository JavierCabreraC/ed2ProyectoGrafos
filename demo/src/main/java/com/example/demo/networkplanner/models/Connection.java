package com.example.demo.networkplanner.models;

import jakarta.persistence.*;


@Entity
@Table(name = "connections")
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "point_a_id")
    private Long pointAId;

    @Column(name = "point_b_id")
    private Long pointBId;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "status")
    private String status;

    public Connection() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPointAId() {
        return pointAId;
    }

    public void setPointAId(Long pointAId) {
        this.pointAId = pointAId;
    }

    public Long getPointBId() {
        return pointBId;
    }

    public void setPointBId(Long pointBId) {
        this.pointBId = pointBId;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
