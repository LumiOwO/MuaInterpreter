package src.mua.operation;

import java.util.ArrayList;
import java.util.Iterator;

import src.mua.exception.MuaException;
import src.mua.namespace.BindingTable;
import src.mua.namespace.Namespace;

public class MuaFunction extends Operation {

	private ArrayList<String> argNames = new ArrayList<String>();
	private ArrayList<Object> steps = new ArrayList<Object>();
	
	public MuaFunction(Object argNames, Object steps) throws MuaException {
		Iterator<Object> iter = toList(argNames).iterator();
		while(iter.hasNext()) {
			String name = toString(iter.next());
			this.argNames.add(name);
		}
		
		iter = toList(steps).iterator();
		while(iter.hasNext()) {
			this.steps.add(iter.next());
		}
	}
	
	@Override
	public int getRequiredArgNum() {
		return argNames.size();
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		
		Namespace namespace = Namespace.getInstance();
		
		BindingTable prevScope = namespace.getCurScope();
		namespace.enterScope(new BindingTable());
		for(int i=0; i<getRequiredArgNum(); i++) {
			namespace.bind(argNames.get(i), getArgValueAt(i));
		}
		execList(steps);
		Object ret = namespace.getOutput();
		namespace.enterScope(prevScope);
		
		return ret;
	}
	
}
