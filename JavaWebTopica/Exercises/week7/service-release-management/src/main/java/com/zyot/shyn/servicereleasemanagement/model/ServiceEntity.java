package com.zyot.shyn.servicereleasemanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "SERVICE", schema = "PUBLIC")
public class ServiceEntity {
    private String id;
    private String name;
    private String environment;
    private String namespace;
    private String oldversion;
    private String newversion;
    private ReleaseEntity releaseByReleaseid;

    public ServiceEntity() {
    }

    public ServiceEntity(String name, String environment, String namespace, String oldversion, String newversion, ReleaseEntity releaseByReleaseid) {
        this.name = name;
        this.environment = environment;
        this.namespace = namespace;
        this.oldversion = oldversion;
        this.newversion = newversion;
        this.releaseByReleaseid = releaseByReleaseid;
    }

    @Id
    @GenericGenerator(name = "id_generator", strategy = "com.zyot.shyn.servicereleasemanagement.utils.IdGenerator")
    @GeneratedValue(generator = "id_generator")
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "ENVIRONMENT")
    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @Basic
    @Column(name = "NAMESPACE")
    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Basic
    @Column(name = "OLDVERSION")
    public String getOldversion() {
        return oldversion;
    }

    public void setOldversion(String oldversion) {
        this.oldversion = oldversion;
    }

    @Basic
    @Column(name = "NEWVERSION")
    public String getNewversion() {
        return newversion;
    }

    public void setNewversion(String newversion) {
        this.newversion = newversion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceEntity that = (ServiceEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(environment, that.environment) &&
                Objects.equals(namespace, that.namespace) &&
                Objects.equals(oldversion, that.oldversion) &&
                Objects.equals(newversion, that.newversion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, environment, namespace, oldversion, newversion);
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RELEASEID", referencedColumnName = "ID", nullable = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    public ReleaseEntity getReleaseByReleaseid() {
        return releaseByReleaseid;
    }

    public void setReleaseByReleaseid(ReleaseEntity releaseByReleaseid) {
        this.releaseByReleaseid = releaseByReleaseid;
    }
}
