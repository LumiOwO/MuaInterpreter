package src.mua.operation;

import java.util.ArrayList;

import src.mua.exception.MuaException;

@SuppressWarnings("serial")
public class MuaIf extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 3;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		
		boolean cond = toBoolean(getArgValueAt(0));
		ArrayList<Object> branch_true = toList(getArgValueAt(1));
		ArrayList<Object> branch_false = toList(getArgValueAt(2));
		
		return execList(cond? branch_true: branch_false);
	}

}
