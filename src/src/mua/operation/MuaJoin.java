package src.mua.operation;

import java.util.ArrayList;
import java.util.Iterator;

import src.mua.exception.MuaException;

public class MuaJoin extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 2;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		ArrayList<Object> a = toList(getArgValueAt(0));
		
		ArrayList<Object> ret = new ArrayList<Object>();
		Iterator<Object> iter = a.iterator();
		while(iter.hasNext())
			ret.add(iter.next());
		ret.add(getArgValueAt(1));
		
		return ret;
	}

}
