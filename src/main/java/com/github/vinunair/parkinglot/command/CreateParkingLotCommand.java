package com.github.vinunair.parkinglot.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vinunair.parkinglot.entity.ParkingLot;

@Component
public class CreateParkingLotCommand implements Command{
	
	@Autowired
	private ParkingLot parkingLot;
	
	private Integer slots;
	
	@Override
	public void execute() {
		parkingLot.initializeParkingLot(slots);
		System.out.println("Created a parking lot with "+slots +" slots");
	}
	
	public void setSlots(int slots) {
		this.slots = slots;
	}

}
