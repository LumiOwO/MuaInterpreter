package src.mua.operation;

import src.mua.exception.MuaException;
import src.mua.namespace.Namespace;

public class MuaIsName extends Operation {

	// isname <word>
	
	@Override
	public int getRequiredArgNum() {
		return 1;
	}

	@Override
	public Object execute() throws MuaException {
		String name = toString(getArgValueAt(0));
		return Namespace.getInstance().isName(name);
	}

}
