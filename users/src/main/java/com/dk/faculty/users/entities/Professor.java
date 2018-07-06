package com.dk.faculty.users.entities;

import javax.persistence.Entity;

@Entity
public class Professor extends User {
    private Integer cabinet;

    public Integer getCabinet() {
        return cabinet;
    }

    public void setCabinet(Integer cabinet) {
        this.cabinet = cabinet;
    }


}
