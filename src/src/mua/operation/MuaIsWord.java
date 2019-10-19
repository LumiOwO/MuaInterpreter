package src.mua.operation;

import src.mua.exception.MuaException;

@SuppressWarnings("serial")
public class MuaIsWord extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 1;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		boolean ret = true;
		try {
			toString(getArgValueAt(0));
		} catch (MuaException.TypeConvert e) {
			ret = false;
		}
		return ret;
	}

}
