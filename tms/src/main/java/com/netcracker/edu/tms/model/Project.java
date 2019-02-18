package com.netcracker.edu.tms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "projects")

public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private BigInteger id;
    @Column(name = "creator_id")
    private BigInteger creatorId;
    @Column(name = "name")
    private String name;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if(obj==this){
            return true;
        }
        if(getClass()!=obj.getClass()){
            return false;
        }
        Project toCompare=(Project) obj;
        if(!id.equals(toCompare.getId())){
            return false;
        }
        if(!creatorId.equals(toCompare.getCreatorId())){
            return false;
        }
        if(!name.equals(toCompare.getName())){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 31 * hash + id.intValue();
        hash = 31 * hash + (name == null ? 0 : name.hashCode());
        hash = 31 * hash + (creatorId == null ? 0 : creatorId.hashCode());
        return hash;
    }
}
