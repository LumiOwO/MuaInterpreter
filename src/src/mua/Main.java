package src.mua;

import java.util.Scanner;

import src.mua.exception.MuaException;
import src.mua.parser.Parser;

public class Main {
	
	public static Scanner std_in = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		// ctrl for prompt
		boolean hasPrompt = true;
		
		String prompt = "Mua> ";
		if(hasPrompt) System.out.print(prompt);
		
		// pre-defined name
		Parser parser = new Parser();
		try {
			parser.parseLine("make \"pi 3.14159");
			parser.parseLine("make \"run [[x] [repeat 1 :x]]");
		} catch (MuaException e) {
			e.printStackTrace();
		}
		
		// main loop
		parser = new Parser();
		while(std_in.hasNextLine()) try {
			String line = std_in.nextLine();
			parser.parseLine(line);
			
		} catch (MuaException e) {
			e.printErrorInfo();
			if(e.isFatal()) {
				e.printStackTrace();
				std_in.close();
				break;
			} else {
				parser = new Parser();
			}
			
		} finally {
			prompt = parser.inProcess()? ".... ": "Mua> ";
			if(hasPrompt) System.out.print(prompt);
		}
		
	}

}
