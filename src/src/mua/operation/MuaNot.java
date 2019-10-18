package src.mua.operation;

import src.mua.exception.MuaException;

@SuppressWarnings("serial")
public class MuaNot extends Operation {

	// not <bool>
	
	@Override
	public int getRequiredArgNum() {
		return 1;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		boolean a = toBoolean(getArgValueAt(0));
		boolean res = !a;
		return res;
	}

}
