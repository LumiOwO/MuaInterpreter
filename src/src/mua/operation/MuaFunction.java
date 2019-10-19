package src.mua.operation;

import java.util.ArrayList;
import java.util.Iterator;

import src.mua.exception.MuaException;
import src.mua.namespace.Namespace;

@SuppressWarnings("serial")
public class MuaFunction extends Operation {

	private ArrayList<String> argNames = new ArrayList<String>();
	private ArrayList<Object> steps = new ArrayList<Object>();
	
	private String funcName;
	
	public MuaFunction(String name) {
		funcName = name;
	}
	
	public String getFuncName() {
		return funcName;
	}

	public void setArgsName(Object argNames) throws MuaException {
		Iterator<Object> iter = toList(argNames).iterator();
		while(iter.hasNext()) {
			String name = toString(iter.next());
			this.argNames.add(name);
		}
	}

	public void setSteps(Object steps) throws MuaException {
		Iterator<Object> iter = toList(steps).iterator();
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
		if(steps.isEmpty())
			setSteps(namespace.getFunction(funcName).steps);
		
		namespace.enterNewScope();
		for(int i=0; i<getRequiredArgNum(); i++) {
			namespace.bind(argNames.get(i), getArgValueAt(i));
		}
		Object ret = execList(steps);
		namespace.exitCurrentScope();
		
		return ret;
	}
	
}
