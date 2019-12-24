package src.mua.operation;

import java.util.ArrayList;

import src.mua.exception.MuaException;

public class MuaFirst extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 1;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		Object obj = getArgValueAt(0);
		Object ret;
		if(obj instanceof String) {
			String str = toString(obj);
			if(str.isEmpty())
				throw new MuaException.EmptyListWord();
			ret = str.substring(0, 1);
			
		} else if(obj instanceof ArrayList) {
			ArrayList<Object> list = toList(obj);
			if(list.isEmpty())
				throw new MuaException.EmptyListWord();
			ret = list.get(0);
			
		} else {
			String str = getString(obj);
			ret = str.substring(0, 1);
		}
		
		return ret;
	}

}
