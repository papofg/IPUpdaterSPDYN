package es.aocana.updater.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.aocana.updater.entity.AppUser;
import es.aocana.updater.service.UserDetailsServiceImpl;

@Controller
public class UserController {
	
	@Autowired
    private UserDetailsServiceImpl userDetailsService;
	
	@Autowired 
	private ApplicationContext applicationContext;
	
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model) {
		getUsersList(model);
        return "users";
    }
    
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("theUser") AppUser theUser, Model model) {
    	if (theUser != null)
    	{
    		theUser.setEncrytedPassword(applicationContext.getBean(BCryptPasswordEncoder.class).encode(theUser.getEncrytedPassword()));
			theUser.setEnabled(true);
    		userDetailsService.updateUser(theUser);
    	}
    	getUsersList(model);
        return "users";
    }
    
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public String deleteUser(@ModelAttribute("theUser") AppUser theUser, Model model) {
    	if (theUser != null)
    	{
    		userDetailsService.deleteUser(theUser);
    	}
    	getUsersList(model);
        return "users";
    }
    
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("theUser") AppUser theUser, Model model) {
    	if (theUser != null)
    	{
			theUser.setEncrytedPassword(applicationContext.getBean(BCryptPasswordEncoder.class).encode(theUser.getEncrytedPassword()));
			theUser.setEnabled(true);
    		userDetailsService.createUser(theUser);
    	}
    	getUsersList(model);
        return "users";
    }
    
    
    
    private void getUsersList(Model model)
    {
    	List<AppUser> userList = userDetailsService.findAll();
    	model.addAttribute("userList", userList);
    }
}
