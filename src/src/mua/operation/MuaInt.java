package src.mua.operation;

import src.mua.exception.MuaException;

public class MuaInt extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 1;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		double num = toDouble(getArgValueAt(0));
		int res = (int)num;
		return (double)res;
	}

}
