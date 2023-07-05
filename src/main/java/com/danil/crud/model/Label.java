package com.danil.crud.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "labels")
@SQLDelete(sql = "UPDATE labels SET status = 'DELETED' WHERE id = ?")
@Where(clause = "status != 'DELETED'")
public class Label implements Comparable<Label> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LabelStatus status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LabelStatus getStatus() {
        return this.status;
    }

    public void setStatus(LabelStatus status) {
        this.status = status;
    }

    public void delete() {
        this.status = LabelStatus.DELETED;
    }

    public boolean isDeleted() {
        return this.status == LabelStatus.DELETED;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + ", " + "name: " + this.name + ", " + "status: " + this.status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Label))
            return false;
        Label other = (Label) o;
        boolean idEquals = this.id == null && other.id == null || this.id != null && this.id.equals(other.id);
        boolean nameEquals = this.name == null && other.name == null
                || this.name != null && this.name.equals(other.name);
        return idEquals && nameEquals && this.status == other.status;
    }

    @Override
    public int compareTo(Label arg0) {
        return this.id - arg0.id;
    }
}
