package src.mua.operation;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import src.mua.Main;
import src.mua.exception.MuaException;

public class MuaReadList extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 0;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		Scanner in = Main.std_in;
		
		if(in.hasNextLine()) {
			String line = in.nextLine();
			ArrayList<Object> ret = new ArrayList<Object>();
			
			ByteArrayInputStream stream = new ByteArrayInputStream(line.getBytes());
			Scanner scanner = new Scanner(stream);
			while(scanner.hasNext()) 
				ret.add(scanner.next());
			scanner.close();
			
			return ret;
		} else
			throw new MuaException.EmptyInput();
	}

}
