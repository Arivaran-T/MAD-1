package com.example.hairdo.model;

public class Service {
    public String id;
    public String name;
    public String _id;

    public Service() {
    }

    public Service(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
