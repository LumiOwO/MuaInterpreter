package src.mua.operation;

import src.mua.exception.MuaException;

public class MuaNot extends Operation {

	// not <bool>
	
	@Override
	public int getRequiredArgNum() {
		return 1;
	}

	@Override
	public Object execute() throws MuaException {
		boolean a = toBoolean(getArgValueAt(0));
		boolean res = !a;
		return res;
	}

}
