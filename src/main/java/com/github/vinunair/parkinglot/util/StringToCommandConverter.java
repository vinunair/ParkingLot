package com.github.vinunair.parkinglot.util;



import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.github.vinunair.parkinglot.command.Command;
import com.github.vinunair.parkinglot.command.CreateParkingLotCommand;
import com.github.vinunair.parkinglot.command.DisplayRegistrationNumberByColourCommand;
import com.github.vinunair.parkinglot.command.DisplaySlotNumberByColourCommand;
import com.github.vinunair.parkinglot.command.DisplaySlotNumberByRegistrationCommand;
import com.github.vinunair.parkinglot.command.DummyCommand;
import com.github.vinunair.parkinglot.command.IssueTicketCommand;
import com.github.vinunair.parkinglot.command.LeaveParkingLotCommand;
import com.github.vinunair.parkinglot.command.ParkingLotStatusCommand;
import com.github.vinunair.parkinglot.entity.Slot;
import com.github.vinunair.parkinglot.entity.Vehicle;

@Component
public class StringToCommandConverter {

	@Autowired
	private CreateParkingLotCommand createParkingLotCmd;
	@Autowired
	private ObjectFactory<IssueTicketCommand> issueTicketCmdFactory;
	@Autowired
	private ObjectFactory<LeaveParkingLotCommand> leaveCmdFactory;
	@Autowired
	private ObjectFactory<ParkingLotStatusCommand> statusCmdFactory;
	@Autowired
	private ObjectFactory<DisplayRegistrationNumberByColourCommand> regNumByColorCmdFactory;
	@Autowired
	private ObjectFactory<DisplaySlotNumberByColourCommand> slotNumByColorCmdFactory;
	@Autowired
	private ObjectFactory<DisplaySlotNumberByRegistrationCommand> slotNumByRegCmdFactory;
	
	public  Command convert( String ... params) {
      
		Command cmd = new DummyCommand();
		if("create_parking_lot".equals(params[0])) {
			createParkingLotCmd.setSlots(Integer.parseInt(params[1]));
			cmd = createParkingLotCmd;
		}
		else if("park".equals(params[0])) {
			String registrationNumber = params[1];
			String colour = params[2];
			Vehicle vehicle = new Vehicle(registrationNumber, colour);
			IssueTicketCommand issueTicketCmd = issueTicketCmdFactory.getObject();
			issueTicketCmd.setVehicle(vehicle);
			cmd = issueTicketCmd;
		}
		else if("leave".equals(params[0])) {
			Integer slotNum = Integer.parseInt(params[1]);
			Slot slot = new Slot(slotNum);
			LeaveParkingLotCommand leaveCmd = leaveCmdFactory.getObject();
			leaveCmd.setSlot(slot);
			cmd = leaveCmd;
		}
		else if("status".equals(params[0])) {
			ParkingLotStatusCommand statusCmd=statusCmdFactory.getObject();
			cmd = statusCmd;
		}
		else if("registration_numbers_for_cars_with_colour".equals(params[0])){
			DisplayRegistrationNumberByColourCommand regNumByColorCmd = regNumByColorCmdFactory.getObject();
			regNumByColorCmd.setColour(params[1]);
			cmd = regNumByColorCmd;
		}
		else if("slot_numbers_for_cars_with_colour".equals(params[0])) {
			DisplaySlotNumberByColourCommand slotNumByColorCmd=slotNumByColorCmdFactory.getObject();
			slotNumByColorCmd.setColour(params[1]);
			cmd = slotNumByColorCmd;
		}
		else if("slot_number_for_registration_number".equals(params[0])) {
			DisplaySlotNumberByRegistrationCommand slotNumByRegCmd=slotNumByRegCmdFactory.getObject();
			slotNumByRegCmd.setRegistration(params[1]);
			cmd = slotNumByRegCmd;
		}
		return cmd;
	}
	
	
	
}
