package es.aocana.updater.dao;

import java.util.Base64;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.aocana.updater.entity.MyHosts;

@Repository
public class MyHostsDAO {

	@Autowired
    private EntityManager entityManager;
	

	public List<MyHosts> findAll() {
        try {
            String sql = "Select e from " + MyHosts.class.getName() + " e " ;
            Query query = entityManager.createQuery(sql, MyHosts.class);
            List<MyHosts> lista = (List<MyHosts>) query.getResultList();
            
            for (MyHosts myHosts : lista) {
            	String dataDecoded = new String(Base64.getDecoder().decode(myHosts.getEncodedLoginData()));
            	String[] data = dataDecoded.split(":");
            	myHosts.setUsername(data[0]);
            	myHosts.setPassword(data[1]);
			}
            
            return lista;
        } catch (NoResultException e) {
            return null;
        }
    }
	

	public void createHost(MyHosts theHost)
	{
		String data = theHost.getUsername()+":"+theHost.getPassword();
		String encodingLoginData = Base64.getEncoder().encodeToString(data.getBytes());
		theHost.setEncodedLoginData(encodingLoginData);
		
		this.entityManager.persist(theHost);
	}
	
	
	public void updateHost(MyHosts theHost)
	{
		String data = theHost.getUsername()+":"+theHost.getPassword();
		String encodingLoginData = Base64.getEncoder().encodeToString(data.getBytes());
		theHost.setEncodedLoginData(encodingLoginData);
		
		this.entityManager.merge(theHost);
	}
	
	public void deleteHost(MyHosts theHost)
	{
		this.entityManager.remove(this.entityManager.contains(theHost) ? theHost : this.entityManager.merge(theHost));
	}
}
