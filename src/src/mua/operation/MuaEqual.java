package src.mua.operation;

import src.mua.exception.MuaException;

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
		
		boolean ret;
		try {
			ret = compare(a, b) == 0;
		} catch (MuaException.Compare e) {
			ret = false;
		}
		return ret;
	}

}
