package src.mua.operation;

import java.util.ArrayList;

import src.mua.exception.MuaException;

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
		
		int times = (int)num;
		Object ret = null;
		for(int i=0; i<times; i++) {
			ret = execList(list);
		}
		
		return ret;
	}

}
