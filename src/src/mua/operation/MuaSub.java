package src.mua.operation;

import src.mua.exception.MuaException;

public class MuaSub extends Operation {

	// sub <number> <number>
	
	@Override
	public int getRequiredArgNum() {
		return 2;
	}

	@Override
	public Object execute() throws MuaException {
		double a = toDouble(getArgValueAt(0));
		double b = toDouble(getArgValueAt(1));
		double res = a - b;
		return res;
	}

}
