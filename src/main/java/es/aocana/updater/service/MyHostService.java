package es.aocana.updater.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.aocana.updater.dao.MyHostsDAO;
import es.aocana.updater.entity.MyHosts;

@Service
public class MyHostService {

	@Autowired
    private MyHostsDAO hostDAO;
	
	
	public List<MyHosts> findAll()
	{
		return hostDAO.findAll();
	}
	
	@Transactional
	public void updateHost(MyHosts theHost)
	{
		hostDAO.updateHost(theHost);
	}
	
	@Transactional
	public void createHost(MyHosts theHost)
	{
		hostDAO.createHost(theHost);
	}
	
	@Transactional
	public void deleteHost(MyHosts theHost)
	{
		hostDAO.deleteHost(theHost);
	}
}
