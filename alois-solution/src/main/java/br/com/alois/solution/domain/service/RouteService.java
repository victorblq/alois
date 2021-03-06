package br.com.alois.solution.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.alois.domain.entity.route.Route;
import br.com.alois.solution.domain.repository.IRouteRepository;
import br.com.alois.solution.domain.repository.IStepRepository;

@Service
public class RouteService 
{
	//=====================================ATTRIBUTES=======================================

	//======================================================================================
		
	//=====================================INJECTIONS=======================================
	@Autowired
	IRouteRepository routeRepository;
	
	@Autowired
	IStepRepository stepRepository;
	//======================================================================================

	//=====================================BEHAVIOUR========================================
	public List<Route> listRoutesByPatientId(Long patientId) 
	{
		return this.routeRepository.findByPatientId(patientId);
	}

	public Route insertRoute(Route route) 
	{
		Assert.notNull(route);
		Assert.notNull(route.getName());
		Assert.notNull(route.getSteps());
		
		return this.routeRepository.save(route);
	}

	public Route updateRoute(Route route) 
	{
		Assert.notNull(route);
		Assert.notNull(route.getSteps());
		
		return this.routeRepository.save(route);
	}

	public void deleteRoute(Route route) 
	{
		this.routeRepository.delete(route);
	}

	//======================================================================================
}
