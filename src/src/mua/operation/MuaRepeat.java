package src.mua.operation;

import java.util.ArrayList;
import java.util.Iterator;

import src.mua.exception.MuaException;
import src.mua.parser.Parser;

@SuppressWarnings("serial")
public class MuaRepeat extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 2;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		double num = toDouble(getArgValueAt(0));
		ArrayList<Object> list = toList(getArgValueAt(1));
		if(num % 1 != 0)
			throw new MuaException.RepeatTimes();
		
		list = new Parser().compactList(list);
		if(!allOp(list))
			throw new MuaException.ListNotFullOp();
		
		int times = (int)num;
		Iterator<Object> iter;
		for(int i=0; i<times; i++) {
			iter = list.iterator();
			while(iter.hasNext()) {
				Operation op = Operation.clone((Operation)iter.next());
				op.execute();
			}
		}
		
		return null;
	}

}
