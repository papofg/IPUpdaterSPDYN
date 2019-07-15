package es.aocana.updater.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.aocana.updater.entity.MyHosts;
import es.aocana.updater.service.MyHostService;
import es.aocana.updater.utils.WebUtils;

@Controller
public class HostController {

	@Autowired
    private MyHostService hostManager;
	
	@RequestMapping(value = "/hosts", method = RequestMethod.GET)
    public String hosts(Model model) {
    	getHostsList(model);
        return "hosts";
    }
    
    @RequestMapping(value = "/updateHost", method = RequestMethod.POST)
    public String updateHost(@ModelAttribute("theHost") MyHosts theHost, Model model) {
    	if (theHost != null)
    	{
    		hostManager.updateHost(theHost);
    	}
    	getHostsList(model);
        return "hosts";
    }
    
    @RequestMapping(value = "/deleteHost", method = RequestMethod.POST)
    public String deleteHost(@ModelAttribute("theHost") MyHosts theHost, Model model) {
    	if (theHost != null)
    	{
    		hostManager.deleteHost(theHost);
    	}
    	getHostsList(model);
        return "hosts";
    }
    
    @RequestMapping(value = "/createHost", method = RequestMethod.POST)
    public String createHost(@ModelAttribute("theHost") MyHosts theHost, Model model) {
    	if (theHost != null)
    	{
    		hostManager.createHost(theHost);
    	}
    	getHostsList(model);
        return "hosts";
    }
    
    private void getHostsList(Model model)
    {
    	List<MyHosts> hostList = hostManager.findAll();
    	model.addAttribute("hostList", hostList);
    }
    
    @Scheduled(fixedDelay = 600000)
    public void updateIpJOB() 
    {
    	String resultadoActualizacion = "OK";
    	SimpleDateFormat sdf = null;
        try 
		{
			sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date fecha=new Date();
            System.out.println("START Job UpdateIpJob. Execution time: "+sdf.format(fecha));
            
            String ipPublica = WebUtils.obtenerIpPublica();
            List<MyHosts> dnsHosts = hostManager.findAll();
            for (MyHosts myHosts : dnsHosts) 
            {
            	if(!ipPublica.equals(myHosts.getIpAddress()) && WebUtils.enviarPeticionActualizaDynDNS(myHosts, ipPublica))
            	{
            		myHosts.setIpAddress(ipPublica);
            		hostManager.updateHost(myHosts);
            	}
			}
			
		} 
		catch (Throwable e) 
		{
			resultadoActualizacion = "KO";
			e.printStackTrace();
		}
		finally 
		{
			Date fecha=new Date();
            System.out.println("END Job UpdateIpJob. Execution time: "+sdf.format(fecha));
            System.out.println("RESULT Job UpdateIpJob. Execution time: "+resultadoActualizacion);
		}
        
    }
}



