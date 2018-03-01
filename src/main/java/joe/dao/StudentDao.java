package joe.dao;

import joe.entities.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StudentDao extends ParentDao {
    public void save(Student s){
        getSession().save(s);
    }

    @Transactional
    public void delete(){
        String hql = "delete from Student ";
        getSession().createQuery(hql).executeUpdate();
    }

    public List<Student> getStudents(){
        String hql = "from Student ";
        return getSession().createQuery(hql).list();
    }

}
