package src.mua.operation;

import java.util.ArrayList;

import src.mua.exception.MuaException;

public class MuaIsEmpty extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 1;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		boolean ret;
		
		Object value = getArgValueAt(0);
		if(value instanceof ArrayList)
			ret = toList(value).isEmpty();
		else if(value instanceof String)
			ret = toString(value).isEmpty();
		else
			throw new MuaException.TypeConvert();
		
		return ret;
	}

}
