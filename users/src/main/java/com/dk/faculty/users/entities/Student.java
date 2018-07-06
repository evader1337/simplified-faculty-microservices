package com.dk.faculty.users.entities;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

@Entity
public class Student extends User {

    @ElementCollection
    private List<Integer> subjectIds;

    public List<Integer> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(List<Integer> subjects) {
        this.subjectIds = subjects;
    }

    public void addSubject(Integer subject) {
        if (!subjectIds.contains(subject)) {
            subjectIds.add(subject);
        }
    }

    public boolean hasSubject(Integer subject) {
        return subjectIds.contains(subject);
    }

    public void removeSubject(Integer subject) {
        for(int i = 0; i< subjectIds.size(); i++) {
            if(subjectIds.get(i) == subject) {
                subjectIds.remove(i);
            }
        }
    }
}
