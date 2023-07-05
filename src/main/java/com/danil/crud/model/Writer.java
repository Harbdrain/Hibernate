package com.danil.crud.model;

import java.util.List;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "writers")
@SQLDelete(sql = "UPDATE writers SET status = 'DELETED' WHERE id = ?")
@Where(clause = "status != 'DELETED'")
public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WriterStatus status;

    @OneToMany(targetEntity = Post.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private List<Post> posts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public WriterStatus getStatus() {
        return status;
    }

    public void setStatus(WriterStatus status) {
        this.status = status;
    }

    public void delete() {
        this.status = WriterStatus.DELETED;
    }

    public boolean isDeleted() {
        return this.status == WriterStatus.DELETED;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ID: " + this.id + ", ");
        builder.append("firstName: " + this.firstName + ", ");
        builder.append("lastName: " + this.lastName + ", ");
        builder.append("status: " + this.status + ", ");
        builder.append("posts: [ ");
        for (Post post : posts) {
            builder.append("{" + post + "}, ");
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Writer))
            return false;
        Writer other = (Writer) o;
        boolean idEquals = this.id == null && other.id == null || this.id != null && this.id.equals(other.id);
        boolean firstNameEquals = this.firstName == null && other.firstName == null
                || this.firstName != null && this.firstName.equals(other.firstName);
        boolean lastNameEquals = this.lastName == null && other.lastName == null
                || this.lastName != null && this.lastName.equals(other.lastName);
        boolean postsEquals = this.posts == null && other.posts == null || this.posts.equals(other.posts);
        boolean statusEquals = this.status == other.status;

        return idEquals && firstNameEquals && lastNameEquals && postsEquals && statusEquals;
    }

}
