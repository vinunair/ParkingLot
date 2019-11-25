package com.github.vinunair.parkinglot.command;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.vinunair.parkinglot.entity.ParkingLot;

@Component
@Scope("prototype")
public class DisplayRegistrationNumberByColourCommand implements Command{

	@Autowired
	private ParkingLot parkingLot;
	
	private String colour;
	
	@Override
	public void execute() {
		Set<String> registrationNumber = parkingLot.getRegistrationNumberByColour(colour);
		if(!registrationNumber.isEmpty())
			System.out.println(String.join(",", registrationNumber));
		else
			System.out.println("Not found");
	}
	
	public void setColour(String colour) {
		this.colour = colour;
	}
}
