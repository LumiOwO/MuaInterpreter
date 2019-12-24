package src.mua.operation;

import java.util.ArrayList;

import src.mua.exception.MuaException;

public class MuaList extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 2;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		ArrayList<Object> ret = new ArrayList<Object>();
		ret.add(getArgValueAt(0));
		ret.add(getArgValueAt(1));
		return ret;
	}

}
