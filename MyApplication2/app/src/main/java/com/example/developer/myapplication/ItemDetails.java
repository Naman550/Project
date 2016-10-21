package com.example.developer.myapplication;

/**
 * Created by Developer on 20-Oct-16.
 */
public class ItemDetails {

    private String group;
    private String name;
    private int ndbno;

    public int getNdbno(){
        return ndbno;
    }
    public String getName(){
        return name;
    }

    public String getGroup(){
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNdbno(int ndbno) {
        this.ndbno = ndbno;
    }

    @Override
    public String toString() {
        return " group= "+group + "\n Name= " + name + "\n ndbno= " + ndbno;
    }
}
