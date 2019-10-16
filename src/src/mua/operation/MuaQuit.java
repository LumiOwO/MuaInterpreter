package src.mua.operation;

import src.mua.exception.MuaException;

public class MuaQuit extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 0;
	}

	@Override
	public Object execute() throws MuaException {
		System.exit(0);
		return null;
	}

}
