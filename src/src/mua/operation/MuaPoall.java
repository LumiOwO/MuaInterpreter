package src.mua.operation;

import src.mua.exception.MuaException;
import src.mua.namespace.Namespace;

public class MuaPoall extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 0;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		Namespace.getInstance().poall();
		return null;
	}

}
