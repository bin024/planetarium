package com.planetarium.planetarium.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "planets")
public class Planet {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    /*  has to be written in all lowercase else the column name isn't identified
     *  this took me 30 minutes to debug
     *  make sure to make it capital I when creating table
     *  point being: for 
     *  name = "attribute"
     *  make sure attribute EXACTLY matches column name in database
     *  also keep in mind, in PostgreSQL, any column name not placed in double quotes
     *  is automatically made all lowercase, so even if you specified ownerId when creating table
     *  PostgreSQL made it ownerid by default, only way to fix it is to make it "ownerId" upon 
     *  creation, so long story short just write out the name attribute in lowercase by default
     */
    @Column(name = "ownerid")
    private int ownerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
    
}
