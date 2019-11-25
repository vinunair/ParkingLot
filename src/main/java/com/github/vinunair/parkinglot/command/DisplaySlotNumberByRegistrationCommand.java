package com.github.vinunair.parkinglot.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.vinunair.parkinglot.entity.ParkingLot;

@Component
@Scope("prototype")
public class DisplaySlotNumberByRegistrationCommand implements Command{

	@Autowired
	private ParkingLot parkingLot;
	
	private String registrationNumber;
	
	@Override
	public void execute() {
		Integer slotNumber = parkingLot.getSlotNumberByRegistrationNumber(registrationNumber);
		if(slotNumber!=null)
			System.out.println(slotNumber);
		else
			System.out.println("Not found");
	}
	
	public void setRegistration(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
}
