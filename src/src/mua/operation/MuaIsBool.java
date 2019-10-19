package src.mua.operation;

import src.mua.exception.MuaException;

@SuppressWarnings("serial")
public class MuaIsBool extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 1;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		boolean ret = true;
		try {
			toBoolean(getArgValueAt(0));
		} catch (MuaException.TypeConvert e) {
			ret = false;
		}
		return ret;
	}

}