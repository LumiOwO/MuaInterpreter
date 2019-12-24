package src.mua.operation;

import java.util.ArrayList;

import src.mua.exception.MuaException;

public class MuaWord extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 2;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		String a = toString(getArgValueAt(0));
		Object b = getArgValueAt(1);
		if(b instanceof ArrayList)
			throw new MuaException.TypeConvert();
		
		return a + getString(b);
	}

}
