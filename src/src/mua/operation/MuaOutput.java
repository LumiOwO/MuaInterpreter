package src.mua.operation;

import src.mua.exception.MuaException;

public class MuaOutput extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 1;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		return getArgValueAt(0);
	}

}
