package src.mua.operation;

import src.mua.exception.MuaException;

public class MuaIsList extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 1;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		boolean ret = true;
		try {
			toList(getArgValueAt(0));
		} catch (MuaException.TypeConvert e) {
			ret = false;
		}
		return ret;
	}

}
