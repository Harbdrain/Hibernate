package com.danil.crud.repository.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.danil.crud.model.Post;
import com.danil.crud.repository.PostRepository;
import com.danil.crud.utils.RepositoryUtils;

public class HibernatePostRepositoryImpl implements PostRepository {
    @Override
    public Post create(Post post) {
        Session session = RepositoryUtils.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(post);
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }

        return post;
    }

    @Override
    public List<Post> getAll() {
        Session session = RepositoryUtils.getSession();
        Transaction transaction = null;

        List<Post> posts = new ArrayList<>();

        try {
            transaction = session.beginTransaction();
            posts = session.createSelectionQuery("SELECT p FROM Post p LEFT JOIN FETCH p.labels", Post.class).list();
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }

        return posts;
    }

    @Override
    public Post getById(Integer id) {
        Post post = null;
        Session session = RepositoryUtils.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            post = session.createSelectionQuery("FROM Post p LEFT JOIN FETCH p.labels l WHERE p.id = :id", Post.class).setParameter("id", id).getSingleResultOrNull();
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }

        return post;
    }

    @Override
    public Post getContentById(Integer id) {
        Post post = null;
        Session session = RepositoryUtils.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            post = session.createSelectionQuery("FROM Post p WHERE p.id = :id", Post.class).setParameter("id", id).getSingleResultOrNull();
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }

        return post;
    }

    @Override
    public Post update(Post post) {
        Session session = RepositoryUtils.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(post);
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }

        return post;
    }

    @Override
    public void deleteById(Integer id) {
        Session session = RepositoryUtils.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createMutationQuery("UPDATE Post p SET p.status = DELETED WHERE p.id=:id")
                    .setParameter("id", id).executeUpdate();
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

}
