package src.mua.operation;

import src.mua.exception.MuaException;

public class MuaRandom extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 1;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		double num = toDouble(getArgValueAt(0));
		if(num < 0)
			throw new MuaException.Random();
		
		return Math.random() * num;
	}

}
