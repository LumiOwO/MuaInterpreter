package src.mua.operation;

import src.mua.exception.MuaException;
import src.mua.namespace.Namespace;

public class MuaErase extends Operation {

	// erase <name>
	
	@Override
	public int getRequiredArgNum() {
		return 1;
	}

	@Override
	public Object execute() throws MuaException {
		String name = toString(getArgValueAt(0));
		Namespace.getInstance().eraseName(name);
		return null;
	}

}
