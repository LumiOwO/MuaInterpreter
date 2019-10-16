package src.mua.operation;

import src.mua.exception.MuaException;

public class MuaGreaterThan extends Operation {

	// gt <number|word> <number|word>

	@Override
	public int getRequiredArgNum() {
		return 2;
	}

	@Override
	public Object execute() throws MuaException {
		Object a = getArgValueAt(0);
		Object b = getArgValueAt(1);
		
		return compare(a, b) > 0;
	}

}
