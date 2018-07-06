package com.dk.faculty.users.entities;

import javax.persistence.Entity;

@Entity
public class Professor extends User {
    private Integer cabinetId;

    public Integer getCabinetId() {
        return cabinetId;
    }

    public void setCabinetId(Integer cabinet) {
        this.cabinetId = cabinet;
    }


}
