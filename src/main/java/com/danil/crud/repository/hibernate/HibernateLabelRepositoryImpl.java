package com.danil.crud.repository.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.danil.crud.model.Label;
import com.danil.crud.repository.LabelRepository;
import com.danil.crud.utils.RepositoryUtils;

public class HibernateLabelRepositoryImpl implements LabelRepository {
    @Override
    public Label create(Label label) {
        Session session = RepositoryUtils.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(label);
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

        return label;
    }

    @Override
    public List<Label> getAll() {
        Session session = RepositoryUtils.getSession();
        Transaction transaction = null;

        List<Label> labels = new ArrayList<>();

        try {
            transaction = session.beginTransaction();
            labels = session.createSelectionQuery("FROM Label", Label.class).list();
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

        return labels;
    }

    @Override
    public Label getById(Integer id) {
        Label label = null;
        Session session = RepositoryUtils.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            label = session.get(Label.class, id);
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

        return label;
    }

    @Override
    public Label update(Label label) {
        Session session = RepositoryUtils.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createMutationQuery("UPDATE Label label SET label.name = :name WHERE label.id=:id")
                    .setParameter("name", label.getName()).setParameter("id", label.getId()).executeUpdate();
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

        return label;
    }

    @Override
    public void deleteById(Integer id) {
        Session session = RepositoryUtils.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createMutationQuery("UPDATE Label label SET label.status = DELETED WHERE label.id=:id")
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
