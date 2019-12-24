package src.mua.operation;

import src.mua.exception.MuaException;
import src.mua.namespace.Namespace;

public class MuaSave extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 1;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		String path = toString(getArgValueAt(0));
		Namespace.getInstance().save(path);
		return null;
	}

}
