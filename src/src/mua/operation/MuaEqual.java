package src.mua.operation;

import src.mua.exception.MuaException;

@SuppressWarnings("serial")
public class MuaEqual extends Operation {
	
	// eq <number|word> <number|word>

	@Override
	public int getRequiredArgNum() {
		return 2;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		Object a = getArgValueAt(0);
		Object b = getArgValueAt(1);
		
		return compare(a, b) == 0;
	}

}
