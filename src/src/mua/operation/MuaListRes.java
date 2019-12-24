package src.mua.operation;

import src.mua.exception.MuaException;
import src.mua.namespace.Namespace;

public class MuaListRes extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 1;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		Object ret = getArgValueAt(0);
		Namespace.getInstance().setListRes(ret);
		return null;
	}

}
