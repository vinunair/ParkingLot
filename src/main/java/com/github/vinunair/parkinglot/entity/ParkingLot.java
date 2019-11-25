package com.github.vinunair.parkinglot.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vinunair.parkinglot.exception.ParkingLotFullException;
import com.github.vinunair.parkinglot.service.ParkingLotService;

@Component
public class ParkingLot {
 
	@Autowired
	ParkingLotService parkingLotService;
	
	private Slot[] slots;
	private PriorityQueue<Slot> slotQueue;
	
	 
	private Map<String,Set<Slot>> mTicketsByCarColour = new HashMap<String,Set<Slot>>(); 
	private Map<String,Slot> mSlotByRegistrationNum = new HashMap<String,Slot>();
	
	public void initializeParkingLot(int n) {
		this.slots = new Slot[n];
		this.slotQueue = new PriorityQueue<>();
		
		for(int i = 0 ; i < n ; i++) {
			slots[i] = new Slot(i+1);
			slotQueue.add(slots[i]);
		}
	}
	
	public Slot issueTicket(Vehicle vehicle) throws ParkingLotFullException {
		Slot slot = parkingLotService.issueTicket(vehicle, slotQueue);
		groupTicketsByCarColour(slot);
		mSlotByRegistrationNum.put(vehicle.getRegistrationNumber(), slot);
		
		return slot;
	}
	
	public Slot exitParkingLot(Slot slot) {
		 int slotNumber = slot.getSlotNumber();
		 Slot currentParkingSlot = slots[slotNumber-1];
		 
		 if(currentParkingSlot.isAvailable())
			 return slot;
		 
		 slot =parkingLotService.exitParkingLot(currentParkingSlot, slotQueue);
		 removeSlotByCarColour(slot);
		 mSlotByRegistrationNum.remove(slot.getVehicle().getRegistrationNumber());
		 return slot;
	}

	public int getAvailableSlots() {
		return slotQueue.size();
	}
	
	public Set<String> getRegistrationNumberByColour(String colour) {
		Set<String> registrationNumbers = new HashSet<>();
		if(mTicketsByCarColour.get(colour)!=null) {
			Set<Slot> slots =  mTicketsByCarColour.get(colour);
			for(Slot slot: slots) {
				registrationNumbers.add(slot.getVehicle().getRegistrationNumber());
			}
		}
		return registrationNumbers;
	}
	
	public Set<String> getSlotNumberByColour(String colour) {
		Set<String> slotNumbers = new HashSet<>();
		if(mTicketsByCarColour.get(colour)!=null) {
			Set<Slot> slots =  mTicketsByCarColour.get(colour);
			for(Slot slot: slots) {
				slotNumbers.add(String.valueOf(slot.getSlotNumber()));
			}
		}
		return slotNumbers;
	}
	
	public List<Slot> getOccupiedSlots() {
		List<Slot> occupiedSlot = new ArrayList() ;
		for(Slot slot: slots) {
			if(!slot.isAvailable())
				occupiedSlot.add(slot);
		}
		
		Collections.sort(occupiedSlot);
		return occupiedSlot;
	}
	
	public Integer getSlotNumberByRegistrationNumber(String registrationNumber) {
		Integer slotNum = null;
		if(mSlotByRegistrationNum.get(registrationNumber)!=null) {
			
			slotNum = mSlotByRegistrationNum.get(registrationNumber).getSlotNumber();
		}
		return slotNum; 
	}
	
	private void groupTicketsByCarColour(Slot slot) {
		String colour = slot.getVehicle().getColour();
		if(mTicketsByCarColour.get(colour)!=null) {
			Set<Slot> slotSet = mTicketsByCarColour.get(colour);
			slotSet.add(slot);
		}
		else {
			Set<Slot> slotSet = new HashSet<Slot>();
			slotSet.add(slot);
			mTicketsByCarColour.put(colour, slotSet);
		}
	}
	
	private void removeSlotByCarColour(Slot slot) {
		String colour = slot.getVehicle().getColour();
		Set<Slot> slots = mTicketsByCarColour.get(colour);
		if(slots !=null) {
			slots.remove(slot);
		}
	}
	
}
