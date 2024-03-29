package src.mua.operation;

import src.mua.exception.MuaException;

public class MuaPrint extends Operation {

	// print <value>
	
	@Override
	public int getRequiredArgNum() {
		return 1;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		Object value = getArgValueAt(0);
		System.out.println(getString(value));
		return null;
	}

}
	
