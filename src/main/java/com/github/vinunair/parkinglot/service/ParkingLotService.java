package com.github.vinunair.parkinglot.service;

import java.util.PriorityQueue;
import com.github.vinunair.parkinglot.entity.Slot;
import com.github.vinunair.parkinglot.entity.Vehicle;
import com.github.vinunair.parkinglot.exception.ParkingLotFullException;

public interface ParkingLotService {

	public Slot issueTicket(Vehicle vehicle,PriorityQueue<Slot> slotQueue) throws ParkingLotFullException;
	public Slot exitParkingLot(Slot slot,PriorityQueue<Slot> slotQueue);
}
