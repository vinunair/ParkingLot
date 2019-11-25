package com.github.vinunair.parkinglot.input;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.vinunair.parkinglot.command.Command;
import com.github.vinunair.parkinglot.util.StringToCommandConverter;
import com.github.vinunair.parkinglot.util.StringUtils;

@Component
public class NonInteractiveInputProcessor implements InputProcessor{

	@Autowired
	private StringToCommandConverter strCmdConverter;
	
	@Override
	public void process(Scanner scanner) {
		
		List<Command> listCommand = new ArrayList<Command>();
		
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String params[] = StringUtils.convertLineToArray(line);
			Command command = strCmdConverter.convert(params);
			listCommand.add(command);
		}
		
		for(Command cmd : listCommand)
			cmd.execute();
	}

}
