package motorcycle.repository;

import motorcycle.model.Participant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collection;
import java.util.List;

public class ParticipantHibernateRepository implements IRepository<Participant, Integer> {
    private final SessionFactory sessionFactory;

    public ParticipantHibernateRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Participant participant) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.persist(participant);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                throw ex;
            }
        }
    }

    @Override
    public void delete(Participant participant) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.remove(participant);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                throw ex;
            }
        }
    }

    @Override
    public void update(Participant participant, Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                participant.setID(id);
                session.merge(participant);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                throw ex;
            }
        }
    }

    @Override
    public Participant findById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Participant.class, id);
        }
    }

    @Override
    public Iterable<Participant> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Participant> query = session.createQuery("from Participant", Participant.class);
            return query.list();
        }
    }

    @Override
    public Collection<Participant> getAll() {
        return (Collection<Participant>) findAll();
    }
}