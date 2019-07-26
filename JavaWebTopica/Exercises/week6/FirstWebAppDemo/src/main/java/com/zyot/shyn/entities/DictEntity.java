package com.zyot.shyn.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dict")
public class DictEntity {
    private int id;
    private String word;
    private String meaning;
    private String type;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "word")
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Basic
    @Column(name = "meaning")
    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DictEntity that = (DictEntity) o;
        return id == that.id &&
                Objects.equals(word, that.word) &&
                Objects.equals(meaning, that.meaning) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, word, meaning, type);
    }
}
