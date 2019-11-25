package com.github.vinunair.parkinglot;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.github.vinunair.parkinglot.entity.ParkingLot;
import com.github.vinunair.parkinglot.entity.Slot;
import com.github.vinunair.parkinglot.entity.Vehicle;
import com.github.vinunair.parkinglot.exception.ParkingLotFullException;

import java.util.Set;

import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingLotTest {
	
	@Autowired
	private ParkingLot parkingLot;
	
	@Test
	public void shouldIssueTicketSuccessfully() throws ParkingLotFullException {
	   parkingLot.initializeParkingLot(2);
	   Vehicle vehicle =  createVehicle("KA-01-HH-1234", "White");
	   parkingLot.issueTicket(vehicle);
	   Assert.assertEquals(1, parkingLot.getAvailableSlots());
	}
	
	@Test(expected=ParkingLotFullException.class)
	public void shouldReturnParkingLotFullException() throws ParkingLotFullException {
		 parkingLot.initializeParkingLot(1);
		   Vehicle vehicle = createVehicle("KA-01-HH-1234", "White");
		   parkingLot.issueTicket(vehicle);
		   parkingLot.issueTicket(vehicle);
	}
	
	@Test
	public void shouldHandleEntryAndExitSuccessfully() throws ParkingLotFullException {
		   parkingLot.initializeParkingLot(2);
		   Vehicle vehicle =  createVehicle("KA-01-HH-1234", "White");
		   Slot slot = parkingLot.issueTicket(vehicle);
		   parkingLot.exitParkingLot(slot);
		   Assert.assertEquals(2, parkingLot.getAvailableSlots());
	}
	
	@Test
	public void shouldAssignNearestSlotToTheEntry() throws ParkingLotFullException {
		parkingLot.initializeParkingLot(4);
		Vehicle vehicle1 =  createVehicle("KA-01-HH-1234", "White");
		Vehicle vehicle2 =  createVehicle("KA-01-HH-9999", "White");
		Vehicle vehicle3 =  createVehicle("KA-01-BB-0001", "Black");
		Vehicle vehicle4 =  createVehicle("KA-01-HH-7777", "Red");
		
		Slot slot1 = parkingLot.issueTicket(vehicle1);
		Slot slot2 = parkingLot.issueTicket(vehicle2);
		Slot slot3 = parkingLot.issueTicket(vehicle3);
		Slot slot4 = parkingLot.issueTicket(vehicle4);
		
		parkingLot.exitParkingLot(slot2);
		parkingLot.exitParkingLot(slot4);
		
		Vehicle vehicle5 =  createVehicle("KA-01-HH-2701", "Blue");
		Slot slot5 = parkingLot.issueTicket(vehicle5);
		Assert.assertEquals(Integer.valueOf(2), slot5.getSlotNumber());
		
	}
	
	@Test
	public void shouldReturnRegistrationNumbersOfParkedCarsByColour() throws ParkingLotFullException {
		parkingLot.initializeParkingLot(3);
		Vehicle vehicle1 =  createVehicle("KA-01-HH-1234", "White");
		Vehicle vehicle2 =  createVehicle("KA-01-HH-9999", "White");
		Vehicle vehicle3 =  createVehicle("KA-01-BB-0001", "Black");
		
		Slot slot1 = parkingLot.issueTicket(vehicle1);
		Slot slot2 = parkingLot.issueTicket(vehicle2);
		Slot slot3 = parkingLot.issueTicket(vehicle3);
		
		Set<String> registrationNumbers = parkingLot.getRegistrationNumberByColour("White");
		Assert.assertEquals(2, registrationNumbers.size());
		Assert.assertTrue(registrationNumbers.contains("KA-01-HH-1234"));
		Assert.assertTrue(registrationNumbers.contains("KA-01-HH-9999"));
		
		parkingLot.exitParkingLot(slot2);
		
		registrationNumbers = parkingLot.getRegistrationNumberByColour("White");
		Assert.assertEquals(1, registrationNumbers.size());
		Assert.assertTrue(registrationNumbers.contains("KA-01-HH-1234"));
		
	}
	
	@Test
	public void shouldReturnSlotNumbersOfParkedCarsByColour() throws ParkingLotFullException {
		
		parkingLot.initializeParkingLot(3);
		Vehicle vehicle1 =  createVehicle("KA-01-HH-1234", "White");
		Vehicle vehicle2 =  createVehicle("KA-01-HH-9999", "White");
		Vehicle vehicle3 =  createVehicle("KA-01-BB-0001", "Black");
		
		Slot slot1 = parkingLot.issueTicket(vehicle1);
		Slot slot2 = parkingLot.issueTicket(vehicle2);
		Slot slot3 = parkingLot.issueTicket(vehicle3);
		
		Set<String> slotNumbers = parkingLot.getSlotNumberByColour("White");
		Assert.assertEquals(2, slotNumbers.size());
		Assert.assertTrue(slotNumbers.contains("1"));
		Assert.assertTrue(slotNumbers.contains("2"));
		
		parkingLot.exitParkingLot(slot1);
		
		slotNumbers = parkingLot.getSlotNumberByColour("White");
		Assert.assertEquals(1, slotNumbers.size());
		Assert.assertTrue(slotNumbers.contains("2"));
	}
	
	@Test
	public void shouldReturnSlotNumberByRegistrationNumber() throws ParkingLotFullException {
		
		parkingLot.initializeParkingLot(3);
		Vehicle vehicle1 =  createVehicle("KA-01-HH-1234", "White");
		Vehicle vehicle2 =  createVehicle("KA-01-HH-9999", "White");
		Vehicle vehicle3 =  createVehicle("KA-01-BB-0001", "Black");
		
		Slot slot1 = parkingLot.issueTicket(vehicle1);
		Slot slot2 = parkingLot.issueTicket(vehicle2);
		Slot slot3 = parkingLot.issueTicket(vehicle3);
		
		Integer slotNumber = parkingLot.getSlotNumberByRegistrationNumber("KA-01-BB-0001");
		Assert.assertTrue(3 == slotNumber);
	}
	
	@Test
	public void shouldReturnOccupiedSlots() throws ParkingLotFullException {
		parkingLot.initializeParkingLot(3);
		
		Vehicle vehicle1 =  createVehicle("KA-01-HH-1234", "White");
		Vehicle vehicle2 =  createVehicle("KA-01-HH-9999", "White");
		Vehicle vehicle3 =  createVehicle("KA-01-BB-0001", "Black");
		
		Slot slot1 = parkingLot.issueTicket(vehicle1);
		Slot slot2 = parkingLot.issueTicket(vehicle2);
		Slot slot3 = parkingLot.issueTicket(vehicle3);
		
		Assert.assertEquals(3,parkingLot.getOccupiedSlots().size());
		
		parkingLot.exitParkingLot(slot3);
		
		Assert.assertEquals(2,parkingLot.getOccupiedSlots().size());
		
		slot3 = parkingLot.issueTicket(vehicle3);
		
		Assert.assertEquals(3,parkingLot.getOccupiedSlots().size());
	}
	
	public Vehicle createVehicle(String registrationNumber, String colour) {
		   Vehicle vehicle = new Vehicle(registrationNumber,colour);
		   return vehicle;
	}
	
}
