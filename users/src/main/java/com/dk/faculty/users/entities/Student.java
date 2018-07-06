package com.dk.faculty.users.entities;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

@Entity
public class Student extends User {

    @ElementCollection
    private List<Integer> subjects;

    public List<Integer> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Integer> subjects) {
        this.subjects = subjects;
    }

    public void addSubject(Integer subject) {
        if (!subjects.contains(subject)) {
            subjects.add(subject);
        }
    }

    public boolean hasSubject(Integer subject) {
        return subjects.contains(subject);
    }

    public void removeSubject(Integer subject) {
        for(int i = 0; i<subjects.size();i++) {
            if(subjects.get(i) == subject) {
                subjects.remove(i);
            }
        }
    }
}
