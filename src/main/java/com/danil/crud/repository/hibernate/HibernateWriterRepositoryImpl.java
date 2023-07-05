package com.danil.crud.repository.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.danil.crud.model.Post;
import com.danil.crud.model.Writer;
import com.danil.crud.repository.WriterRepository;
import com.danil.crud.utils.RepositoryUtils;

import jakarta.persistence.EntityGraph;

public class HibernateWriterRepositoryImpl implements WriterRepository {
    @Override
    public Writer create(Writer writer) {
        Session session = RepositoryUtils.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(writer);
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

        return writer;
    }

    @Override
    public List<Writer> getAll() {
        Session session = RepositoryUtils.getSession();
        Transaction transaction = null;

        List<Writer> writers = new ArrayList<>();

        try {
            transaction = session.beginTransaction();
            EntityGraph<Post> postGraph = session.createEntityGraph(Post.class);
            postGraph.addAttributeNodes("content");
            postGraph.addAttributeNodes("created");
            postGraph.addAttributeNodes("updated");
            postGraph.addAttributeNodes("status");
            postGraph.addSubgraph("labels").addAttributeNodes("name");
            postGraph.addSubgraph("labels").addAttributeNodes("status");

            writers = session.createSelectionQuery("SELECT w FROM Writer w LEFT JOIN FETCH w.posts", Writer.class).setHint("jakarta.persistence.fetchgraph", postGraph).list();

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

        return writers;
    }

    @Override
    public Writer getById(Integer id) {
        Writer writer = null;
        Session session = RepositoryUtils.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            EntityGraph<Post> postGraph = session.createEntityGraph(Post.class);
            postGraph.addAttributeNodes("content");
            postGraph.addAttributeNodes("created");
            postGraph.addAttributeNodes("updated");
            postGraph.addAttributeNodes("status");
            postGraph.addSubgraph("labels").addAttributeNodes("name");
            postGraph.addSubgraph("labels").addAttributeNodes("status");

            writer = session
                    .createSelectionQuery("SELECT w FROM Writer w LEFT JOIN FETCH w.posts WHERE w.id = :id", Writer.class)
                    .setParameter("id", id).setHint("jakarta.persistence.fetchgraph", postGraph).getSingleResultOrNull();

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

        return writer;
    }

    @Override
    public Writer getFirstLastNameById(Integer id) {
        Writer writer = null;
        Session session = RepositoryUtils.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            writer = session
                    .createSelectionQuery("SELECT w FROM Writer w WHERE w.id = :id", Writer.class)
                    .setParameter("id", id).getSingleResultOrNull();
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

        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        Session session = RepositoryUtils.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(writer);
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

        return writer;
    }

    @Override
    public void deleteById(Integer id) {
        Session session = RepositoryUtils.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createMutationQuery("UPDATE Writer w SET w.status = DELETED WHERE w.id=:id")
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
