package src.mua.operation;

import src.mua.exception.MuaException;
import src.mua.namespace.Namespace;

public class MuaStop extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 0;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		Namespace namespace = Namespace.getInstance();
		if(!namespace.inGlobal())
			namespace.stopFuncExec();
		
		return null;
	}

}
