package com.github.vinunair.parkinglot.input;

import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.github.vinunair.parkinglot.command.Command;
import com.github.vinunair.parkinglot.util.StringToCommandConverter;
import com.github.vinunair.parkinglot.util.StringUtils;

@Component
public class InteractiveInputProcessor implements InputProcessor{

	@Autowired
	private StringToCommandConverter strCmdConverter;
	
	@Override
	public void process(Scanner scanner) {
		System.out.println("Input commands");
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String params[] = StringUtils.convertLineToArray(line);
			Command command = strCmdConverter.convert(params);
			command.execute();
		}
		
	}

}
