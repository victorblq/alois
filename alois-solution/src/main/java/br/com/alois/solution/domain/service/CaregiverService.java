package br.com.alois.solution.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.alois.domain.entity.user.Caregiver;
import br.com.alois.domain.entity.user.Patient;
import br.com.alois.domain.entity.user.UserType;
import br.com.alois.solution.domain.repository.ICaregiverRepository;
import br.com.alois.solution.domain.repository.IPatientRepository;
import br.com.alois.solution.domain.repository.IUserRepository;

@Service
public class CaregiverService
{
	//=====================================ATTRIBUTES=======================================

	//======================================================================================
		
	//=====================================INJECTIONS=======================================
	@Autowired
	ICaregiverRepository caregiverRepository;

	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IPatientRepository patientRepository;
	//======================================================================================

	//=====================================BEHAVIOUR========================================

	//======================================================================================
	public Caregiver signup(Caregiver caregiver)
	{
		Assert.notNull(caregiver);
		
		caregiver.setUserType(UserType.CAREGIVER);
		
		if(this.userRepository.findByUsername(caregiver.getUsername()) == null)
		{
			return this.caregiverRepository.save(caregiver);
		}
		else
		{
			return null;
		}
	}

	public List<Caregiver> getCaregiverList() 
	{
		return this.caregiverRepository.findAll();
	}

	public void updateNotificationToken(String notificationToken, Long caregiverId) 
	{
		Caregiver caregiver = this.caregiverRepository.findOne(caregiverId);
		
		caregiver.setNotificationToken(notificationToken);
		
		this.caregiverRepository.save(caregiver);
	}

	public Caregiver addCaregiver(Caregiver caregiver) 
	{
		caregiver.setUserType(UserType.CAREGIVER);
		return this.caregiverRepository.save(caregiver);
	}
	
	public void deleteCaregiver(Long caregiverId)
	{
		List<Patient> caregiverPatients = this.patientRepository.listPatientsByCaregiverId(caregiverId);
		
		for(Patient patient: caregiverPatients)
		{
			this.patientRepository.delete(patient);
		}
		
		this.caregiverRepository.delete(caregiverId);
	}

	public Caregiver updateCaregiver(Caregiver caregiver) 
	{
		return this.caregiverRepository.save(caregiver);
	}
}
