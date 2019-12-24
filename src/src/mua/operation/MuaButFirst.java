package src.mua.operation;

import java.util.ArrayList;

import src.mua.exception.MuaException;

public class MuaButFirst extends Operation {

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
			ret = str.substring(1, str.length());
			
		} else if(obj instanceof ArrayList) {
			ArrayList<Object> list = toList(obj);
			if(list.isEmpty())
				throw new MuaException.EmptyListWord();
			ArrayList<Object> newList = new ArrayList<Object>();
			for(int i=1; i<list.size(); i++)
				newList.add(list.get(i));
			
			ret = newList;
			
		} else {
			throw new MuaException.TypeConvert();
		}
		
		return ret;
	}

}
