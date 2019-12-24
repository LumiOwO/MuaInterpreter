package src.mua.operation;

import java.util.ArrayList;
import java.util.Iterator;

import src.mua.exception.MuaException;

public class MuaSentence extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 2;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		Object a = getArgValueAt(0);
		Object b = getArgValueAt(1);
		
		ArrayList<Object> ret = new ArrayList<Object>();
		Iterator<Object> iter;
		if(a instanceof ArrayList) {
			iter = toList(a).iterator();
			while(iter.hasNext()) {
				ret.add(iter.next());
			}
		} else {
			ret.add(a);
		}
		
		if(b instanceof ArrayList) {
			iter = toList(b).iterator();
			while(iter.hasNext()) {
				ret.add(iter.next());
			}
		} else {
			ret.add(b);
		}
		
		return ret;
	}

}
