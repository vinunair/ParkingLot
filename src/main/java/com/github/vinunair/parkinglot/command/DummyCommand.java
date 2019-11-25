package com.github.vinunair.parkinglot.command;

public class DummyCommand implements Command {

	@Override
	public void execute() {
		System.out.println("Didnt match any command.");
		
	}

}
