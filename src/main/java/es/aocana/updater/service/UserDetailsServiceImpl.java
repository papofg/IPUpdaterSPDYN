package es.aocana.updater.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.aocana.updater.dao.AppUserDAO;
import es.aocana.updater.entity.AppUser;
 
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private AppUserDAO appUserDAO;
 

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser appUser = this.appUserDAO.findUserAccount(userName);
 
        if (appUser == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }
 
        System.out.println("Found User: " + appUser+" with Role: "+appUser.getRole().name());
        
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        GrantedAuthority authority = new SimpleGrantedAuthority(appUser.getRole().name());
        grantList.add(authority);
        

        UserDetails userDetails = (UserDetails) new User(appUser.getUserName(), //
                appUser.getEncrytedPassword(), grantList);
 
        return userDetails;
    }
    
    @Transactional
	public void updateUser(AppUser theUser)
	{
    	appUserDAO.updateUser(theUser);
	}
	
	@Transactional
	public void createUser(AppUser theUser)
	{
		appUserDAO.createUser(theUser);
	}
	
	@Transactional
	public void deleteUser(AppUser theUser)
	{
		appUserDAO.deleteUser(theUser);
	}
	
	public List<AppUser> findAll()
	{
		return appUserDAO.findAll();
	}
	

 
}
