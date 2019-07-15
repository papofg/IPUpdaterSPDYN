package es.aocana.updater.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.aocana.updater.entity.AppUser;

 
@Repository
public class AppUserDAO {
 
    @Autowired
    private EntityManager entityManager;
 
    public AppUser findUserAccount(String userName) {
        try {
            String sql = "Select e from " + AppUser.class.getName() + " e " //
                    + " Where e.userName = :userName ";
 
            Query query = entityManager.createQuery(sql, AppUser.class);
            query.setParameter("userName", userName);
 
            return (AppUser) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public void createUser(AppUser theUser)
	{
		this.entityManager.persist(theUser);
	}
	
	public void updateUser(AppUser theUser)
	{
		this.entityManager.merge(theUser);
	}
	
	public void deleteUser(AppUser theUser)
	{
		this.entityManager.remove(this.entityManager.contains(theUser) ? theUser : this.entityManager.merge(theUser));
	}
	
	public List<AppUser> findAll() {
        try {
            String sql = "Select e from " + AppUser.class.getName() + " e " ;
            Query query = entityManager.createQuery(sql, AppUser.class);
            List<AppUser> lista = (List<AppUser>) query.getResultList();
            return lista;
        } catch (NoResultException e) {
            return null;
        }
    }
 
}