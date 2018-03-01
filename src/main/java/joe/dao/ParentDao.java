package joe.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ParentDao {

    @Autowired
    private SessionFactory sessionFactory ;

    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }
}
