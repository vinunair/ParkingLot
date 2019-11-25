package com.github.vinunair.parkinglot.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.vinunair.parkinglot.entity.ParkingLot;
import com.github.vinunair.parkinglot.entity.Slot;

@Component
@Scope("prototype")
public class LeaveParkingLotCommand implements Command{

	@Autowired
	private ParkingLot parkingLot;
	
	private Slot slot;
	
	public void setSlot(Slot slot) {
		this.slot = slot;
	}
	
	@Override
	public void execute() {
		Slot slot = parkingLot.exitParkingLot(this.slot);
		System.out.println("Slot number "+slot.getSlotNumber()+ " is free.");
	}

}
