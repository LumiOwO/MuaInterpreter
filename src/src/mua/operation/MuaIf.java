package src.mua.operation;

import java.util.ArrayList;
import java.util.Iterator;

import src.mua.exception.MuaException;
import src.mua.parser.Parser;

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
		
		branch_true = new Parser().compactList(branch_true);
		branch_false = new Parser().compactList(branch_false);
		
		if(!allOp(branch_true) || !allOp(branch_false))
			throw new MuaException.ListNotFullOp();
		
		ArrayList<Object> exec = cond? branch_true: branch_false;
		Iterator<Object> iter = exec.iterator();
		while(iter.hasNext()) {
			Operation op = (Operation)iter.next();
			op.execute();
		}
		
		return null;
	}

}
