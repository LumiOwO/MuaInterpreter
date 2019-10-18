package src.mua.operation;

import src.mua.exception.MuaException;

@SuppressWarnings("serial")
public class MuaAnd extends Operation {

	// and <bool> <bool>
	
	@Override
	public int getRequiredArgNum() {
		return 2;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		boolean a = toBoolean(getArgValueAt(0));
		boolean b = toBoolean(getArgValueAt(1));
		boolean res = a && b;
		return res;
	}

}
