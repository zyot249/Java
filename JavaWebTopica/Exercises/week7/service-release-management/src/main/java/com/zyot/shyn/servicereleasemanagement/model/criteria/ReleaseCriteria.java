package com.zyot.shyn.servicereleasemanagement.model.criteria;

public class ReleaseCriteria {
    private String name;
    private String description;
    private String createdby;

    public ReleaseCriteria() {
    }

    public ReleaseCriteria(String name, String description, String createdby) {
        this.name = name;
        this.description = description;
        this.createdby = createdby;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }
}
