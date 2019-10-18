package src.mua.operation;

import src.mua.exception.MuaException;

@SuppressWarnings("serial")
public class MuaQuit extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 0;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		System.exit(0);
		return null;
	}

}
