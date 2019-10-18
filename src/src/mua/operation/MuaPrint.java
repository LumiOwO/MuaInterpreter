package src.mua.operation;

import java.util.ArrayList;

import src.mua.exception.MuaException;

public class MuaPrint extends Operation {

	// print <value>
	
	@Override
	public int getRequiredArgNum() {
		return 1;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		Object value = getArgValueAt(0);
		printResult(value);
		return null;
	}

	private void printResult(Object res) throws MuaException {
		print(res);
		System.out.println();
	}
	
	private void print(Object res) throws MuaException {
		if(res == null)
			System.out.print("null");
		else if(res instanceof String)
			System.out.print(toString(res));
		else if(res instanceof Double)
			System.out.print(toDouble(res));
		else if(res instanceof Boolean)
			System.out.print(toBoolean(res));
		else if(res instanceof ArrayList)
			print(toList(res));
		else if(res instanceof Operation)
			print(((Operation)res).execute());
	}
	
	private void print(ArrayList<Object> res) throws MuaException {
		System.out.print("[");
		for(int i=0; i<res.size(); i++) {
			if(i != 0) System.out.print(", ");
			print(res.get(i));
		}
		System.out.print("]");
	}
}
