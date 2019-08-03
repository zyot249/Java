package com.zyot.shyn.servicereleasemanagement.model.criteria;

public class ServiceCriteria {
    private String name;
    private String environment;
    private String namespace;
    private String oldversion;
    private String newversion;
    private String releaseid;

    public ServiceCriteria() {
    }

    public ServiceCriteria(String name, String environment, String namespace, String oldversion, String newversion, String releaseid) {
        this.name = name;
        this.environment = environment;
        this.namespace = namespace;
        this.oldversion = oldversion;
        this.newversion = newversion;
        this.releaseid = releaseid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getOldversion() {
        return oldversion;
    }

    public void setOldversion(String oldversion) {
        this.oldversion = oldversion;
    }

    public String getNewversion() {
        return newversion;
    }

    public void setNewversion(String newversion) {
        this.newversion = newversion;
    }

    public String getReleaseid() {
        return releaseid;
    }

    public void setReleaseid(String releaseid) {
        this.releaseid = releaseid;
    }
}
