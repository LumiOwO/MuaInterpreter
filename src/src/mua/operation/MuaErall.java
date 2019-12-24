package src.mua.operation;

import src.mua.exception.MuaException;
import src.mua.namespace.Namespace;

public class MuaErall extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 0;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		Namespace.getInstance().erall();
		return null;
	}

}
