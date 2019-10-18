package src.mua.operation;

import java.util.ArrayList;

import src.mua.exception.MuaException;

public class MuaParseList extends Operation {

	// use for parse list
	private int requiredArgNum = INFINITY;
	
	@Override
	public int getRequiredArgNum() {
		return requiredArgNum;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		
		ArrayList<Object> res = new ArrayList<Object>();
		for(int i=0; i<getNowArgNum(); i++)
			res.add(getArgValueAt(i));
		
		return res;
	}
	
	@Override
	public Object execute() throws MuaException {
		return this.exec_leaf();
	}

	public void setListFull(boolean oneMore) {
		requiredArgNum = oneMore? getNowArgNum() + 1: getNowArgNum();
	}

}
