package src.mua.namespace;

import java.util.HashMap;

public class BindingTable {
	// value type: double, boolean, string, ArrayList
	// variable name: " " + string
	
	private HashMap<String, Object> nameTable = new HashMap<String, Object>();
	private HashMap<String, Object> funcTable = new HashMap<String, Object>();
	
	public void bindName(String name, Object value) {
		nameTable.put(name, value);
	}
	
	public void eraseName(String name) {
		nameTable.remove(name);
	}
	
	public Object valueOfName(String name) {
		return nameTable.get(name);
	}

	public boolean isName(String name) {
		return nameTable.containsKey(name);
	}
	
	
}
