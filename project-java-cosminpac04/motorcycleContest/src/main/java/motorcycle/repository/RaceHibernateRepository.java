package motorcycle.repository;
import motorcycle.model.Race;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class RaceHibernateRepository implements IRepository<Race, Integer> {
    private final SessionFactory sessionFactory;

    public RaceHibernateRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Race race) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            session.persist(race);
            tx.commit();
        }
    }

    @Override
    public void delete(Race race) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            session.remove(race);
            tx.commit();
        }
    }

    @Override
    public void update(Race race, Integer id) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            race.setID(id);
            session.merge(race);
            tx.commit();
        }
    }

    @Override
    public Race findById(Integer id){
        try(Session session = sessionFactory.openSession()){
            return session.get(Race.class, id);
        }

    }

    @Override
    public List<Race> findAll() {
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("from Race", Race.class).list();
        }
    }

    @Override
    public List<Race> getAll(){
        return findAll();
    }
}
