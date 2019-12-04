package src.mua.operation;

import java.util.ArrayList;

import src.mua.exception.MuaException;
import src.mua.namespace.Namespace;

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
		
		execList(cond? branch_true: branch_false);
		return Namespace.getInstance().getOutput();
	}

}
