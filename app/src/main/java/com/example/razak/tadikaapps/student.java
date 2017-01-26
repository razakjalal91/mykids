package com.example.razak.tadikaapps;

/**
 * Created by Ajak on 1/24/2017.
 */

public class student {
    private int id;
    private String fullname;


    public student(int id, String fullname){
        this.id = id;
        this.fullname = fullname;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String fullname){
        this.fullname = fullname;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.fullname;
    }

}
