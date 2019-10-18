package src.mua.namespace;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import src.mua.exception.MuaException;
import src.mua.operation.MuaFunction;

public class BindingTable {
	// value type: double, boolean, string, ArrayList
	// variable name: " " + string
	
	private HashMap<String, Object> nameTable = new HashMap<String, Object>();
	private HashMap<String, ArrayList<Object>> funcTable = new HashMap<String, ArrayList<Object>>();
	
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
		
		ArrayList<Object> list = cloneList(funcTable.get(name));
		MuaFunction ret = null;
		if(list != null) {
			ret = new MuaFunction(name);
			ret.setArgsName(list.get(0));
			ret.setSteps(list.get(1));
		}
		
		return ret;
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Object> cloneList(ArrayList<Object> list) {
		
		ArrayList<Object> ret = null;
		if(list != null) try {
			ByteArrayOutputStream out_byte = new ByteArrayOutputStream();
			ObjectOutputStream out_obj = new ObjectOutputStream(out_byte);
			out_obj.writeObject(list);
 
			ByteArrayInputStream in_byte = new ByteArrayInputStream(out_byte.toByteArray());
			ObjectInputStream in_obj = new ObjectInputStream(in_byte);
			ret = (ArrayList<Object>) in_obj.readObject();
			
		} catch (Exception e) {
			e.printStackTrace();
		};
		
		return ret;
	}

	public void putAll(BindingTable table) {
		nameTable.putAll(table.nameTable);
		funcTable.putAll(table.funcTable);
	}

	
}
