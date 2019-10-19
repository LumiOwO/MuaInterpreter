package src.mua.operation;

import java.util.Scanner;

import src.mua.Main;
import src.mua.exception.MuaException;

@SuppressWarnings("serial")
public class MuaRead extends Operation {

	// read
	
	@Override
	public int getRequiredArgNum() {
		return 0;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		Scanner in = Main.std_in;
		
		if(in.hasNext())
			return in.next();
		else
			throw new MuaException.EmptyInput();
	}

}
