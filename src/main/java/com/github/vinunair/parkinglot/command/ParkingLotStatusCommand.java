package com.github.vinunair.parkinglot.command;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.vinunair.parkinglot.entity.ParkingLot;
import com.github.vinunair.parkinglot.entity.Slot;

@Component
@Scope("prototype")
public class ParkingLotStatusCommand implements Command{

	@Autowired
	private ParkingLot parkingLot;
	
	@Override
	public void execute() {
		List<Slot> occupiedSlots =  parkingLot.getOccupiedSlots();
		if(!occupiedSlots.isEmpty()) {
			System.out.println("Slot No.	Registration Number 		Colour");
			for(Slot slot:occupiedSlots) {
				System.out.println(slot.getSlotNumber() +"	"+slot.getVehicle().getRegistrationNumber()+"		"+slot.getVehicle().getColour());
			}
		} else {
			System.out.println("Parking Lot is empty");
		}
	}

}
