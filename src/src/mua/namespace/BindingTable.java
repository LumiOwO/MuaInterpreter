package src.mua.namespace;

import java.util.ArrayList;
import java.util.HashMap;

import src.mua.exception.MuaException;
import src.mua.operation.MuaFunction;

public class BindingTable {
	// value type: double, boolean, string, ArrayList
	// variable name: " " + string
	
	private HashMap<String, Object> nameTable = new HashMap<String, Object>();
	private HashMap<String, ArrayList<Object>> funcTable = new HashMap<String, ArrayList<Object>>();
	
	private Object output = null;
	private boolean stopSignal = false;
	private boolean isFunc = false;
	
	public Object getOutput() {
		return output;
	}

	public void setOutput(Object output) {
		this.output = output;
	}
	
	public void bind(String name, Object value) {
		nameTable.put(name, value);
	}
	
	public void bind(String name, ArrayList<Object> list) {
		funcTable.put(name, list);
	}
	
	public void eraseName(String name) {
		nameTable.remove(name);
	}

	public boolean isName(String name) {
		return nameTable.containsKey(name);
	}
	
	public Object valueOfName(String name) {
		return nameTable.get(name);
	}
	
	public MuaFunction getFunction(String name) throws MuaException {
		
		ArrayList<Object> list = funcTable.get(name);
		
		MuaFunction ret = null;
		if(list != null) {
			ret = new MuaFunction(list.get(0), list.get(1));
		}
		
		return ret;
	}

	public void putAll(BindingTable table) {
		nameTable.putAll(table.nameTable);
		funcTable.putAll(table.funcTable);
	}

	public boolean hasStopped() {
		return stopSignal;
	}

	public void stopFuncExec() {
		stopSignal = true;
	}
	
	public boolean isFunction() {
		return isFunc;
	}
	
	public void markFunc() {
		isFunc = true;
	}
	
}
