package src.mua.operation;

import src.mua.exception.MuaException;

public class MuaMod extends Operation {

	// mod <number> <number>
	
	@Override
	public int getRequiredArgNum() {
		return 2;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		double a = toDouble(getArgValueAt(0));
		double b = toDouble(getArgValueAt(1));
		double res = a % b;
		return res;
	}

}
