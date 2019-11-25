package com.github.vinunair.parkinglot.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.vinunair.parkinglot.entity.ParkingLot;
import com.github.vinunair.parkinglot.entity.Slot;
import com.github.vinunair.parkinglot.entity.Vehicle;
import com.github.vinunair.parkinglot.exception.ParkingLotFullException;

@Component
@Scope("prototype")
public class IssueTicketCommand implements Command {
	
	@Autowired
	private ParkingLot parkingLot;
	
	private Vehicle vehicle;
	
	@Override
	public void execute() {
		try {
			Slot slot = parkingLot.issueTicket(vehicle);
			System.out.println("Allocated slot number "+ slot.getSlotNumber());
			
		} catch (ParkingLotFullException e) {
			System.out.println("Sorry, parking lot is full");
		}
		
	}
	
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	
}
