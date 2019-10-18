package src.mua.operation;

import src.mua.exception.MuaException;
import src.mua.namespace.Namespace;

@SuppressWarnings("serial")
public class MuaExport extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 0;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		Namespace.getInstance().export();
		return null;
	}

}
