package com.danil.crud.model;

import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
@SQLDelete(sql = "UPDATE posts SET status = 'DELETED' WHERE id = ?")
@Where(clause = "status != 'DELETED'")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "created")
    private Long created;

    @Column(name = "updated")
    private Long updated;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PostStatus status;

    @ManyToMany(targetEntity = Label.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "post_label_relationship", joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "label_id", referencedColumnName = "id"))
    private Set<Label> labels;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public Set<Label> getLabels() {
        return labels;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }

    public void addLabel(Label label) {
        this.labels.add(label);
    }

    public void removeLabel(int labelId) {
        this.labels.removeIf(e -> e.getId() == labelId);
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public void delete() {
        this.status = PostStatus.DELETED;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ID: " + this.id + ", ");
        builder.append("content: " + this.content + ", ");
        builder.append("created: " + this.created + ", ");
        builder.append("updated: " + this.updated + ", ");
        builder.append("status: " + this.status + ", ");
        builder.append("labels: [ ");
        for (Label label : labels) {
            builder.append("{" + label + "}, ");
        }
        builder.append("]");
        return builder.toString();
    }

    public boolean isDeleted() {
        return this.status == PostStatus.DELETED;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Post))
            return false;
        Post other = (Post) o;

        boolean idEquals = this.id == null && other.id == null || this.id.equals(other.id);
        boolean contentEquals = this.content == null && other.content == null
                || this.content != null && this.content.equals(other.content);
        boolean createdEquals = this.created == null && other.created == null
                || this.created != null && this.created.equals(other.created);
        boolean updatedEquals = this.updated == null && other.updated == null
                || this.updated != null && this.updated.equals(other.updated);
        boolean labelsEquals = this.labels == null && other.labels == null
                || this.labels != null && this.labels.equals(other.labels);
        boolean statusEquals = this.status == other.status;

        return idEquals && contentEquals && createdEquals && updatedEquals && labelsEquals && statusEquals;
    }
}
