package com.github.vinunair.parkinglot.service;

import java.util.PriorityQueue;
import org.springframework.stereotype.Service;
import com.github.vinunair.parkinglot.entity.Slot;
import com.github.vinunair.parkinglot.entity.Vehicle;
import com.github.vinunair.parkinglot.exception.ParkingLotFullException;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {

	@Override
	public Slot issueTicket(Vehicle vehicle, PriorityQueue<Slot> slotQueue) throws ParkingLotFullException {
		if(slotQueue.isEmpty())
			throw new ParkingLotFullException();
		
		Slot slot =slotQueue.poll();
		slot.setAvailable(false);
		slot.setVehicle(vehicle);
		return slot;
	}

	@Override
	public Slot exitParkingLot(Slot slot, PriorityQueue<Slot> slotQueue) {
		slot.setAvailable(true);
		slotQueue.add(slot);
		return slot;
	}

}
