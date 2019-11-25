package com.github.vinunair.parkinglot;


import java.io.File;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.github.vinunair.parkinglot.input.InteractiveInputProcessor;
import com.github.vinunair.parkinglot.input.NonInteractiveInputProcessor;

@SpringBootApplication
public class Application implements CommandLineRunner{

	@Autowired
	private InteractiveInputProcessor cmdInputProcessor;
	@Autowired
	private NonInteractiveInputProcessor fileInputProcessor;

	public static void main(String args[]) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(args.length > 0 ) {
			if(args[0].equals("file")) {
				File inputFile = new File(args[1]);
				Scanner scanner = new Scanner(inputFile);
				fileInputProcessor.process(scanner);
		 }
		
		else {
			Scanner scanner = new Scanner(System.in);
			cmdInputProcessor.process(scanner);
		}
		}
	}
		
	}
	
	

