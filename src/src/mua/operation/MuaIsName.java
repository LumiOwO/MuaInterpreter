package src.mua.operation;

import src.mua.exception.MuaException;
import src.mua.namespace.Namespace;

@SuppressWarnings("serial")
public class MuaIsName extends Operation {

	// isname <word>
	
	@Override
	public int getRequiredArgNum() {
		return 1;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		String name = toString(getArgValueAt(0));
		return Namespace.getInstance().isName(name);
	}

}
