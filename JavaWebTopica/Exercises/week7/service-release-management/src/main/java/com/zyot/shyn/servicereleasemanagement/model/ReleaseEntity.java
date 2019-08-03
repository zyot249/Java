package com.zyot.shyn.servicereleasemanagement.model;

import com.zyot.shyn.servicereleasemanagement.model.criteria.ReleaseCriteria;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "RELEASE", schema = "PUBLIC")
public class ReleaseEntity {
    private String id;
    private String name;
    private String description;
    private Timestamp createdat;
    private String createdby;

    public ReleaseEntity() {
    }

    public ReleaseEntity(ReleaseCriteria releaseCriteria) {
        this.setName(releaseCriteria.getName());
        this.setDescription(releaseCriteria.getDescription());
        this.setCreatedat(new Timestamp(new Date().getTime()));
        this.setCreatedby(releaseCriteria.getCreatedby());
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
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "CREATEDAT")
    public Timestamp getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Timestamp createdat) {
        this.createdat = createdat;
    }

    @Basic
    @Column(name = "CREATEDBY")
    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReleaseEntity that = (ReleaseEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(createdat, that.createdat) &&
                Objects.equals(createdby, that.createdby);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, createdat, createdby);
    }
}
