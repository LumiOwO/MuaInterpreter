package src.mua.operation;

import src.mua.exception.MuaException;

public class MuaOr extends Operation {

	// or <bool> <bool>
	
	@Override
	public int getRequiredArgNum() {
		return 2;
	}

	@Override
	public Object execute() throws MuaException {
		boolean a = toBoolean(getArgValueAt(0));
		boolean b = toBoolean(getArgValueAt(1));
		boolean res = a || b;
		return res;
	}

}
