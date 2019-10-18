package src.mua.namespace;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import src.mua.operation.MuaFunction;

public class BindingTable {
	// value type: double, boolean, string, ArrayList
	// variable name: " " + string
	
	private HashMap<String, Object> nameTable = new HashMap<String, Object>();
	private HashMap<String, MuaFunction> funcTable = new HashMap<String, MuaFunction>();
	
	public void bind(String name, Object value) {
		nameTable.put(name, value);
	}
	
	public void bind(String name, MuaFunction func) {
		funcTable.put(name, func);
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
	
	public MuaFunction getFunction(String name) {
		return cloneFunc(funcTable.get(name));
	}

	private MuaFunction cloneFunc(MuaFunction muaFunction) {
		
		MuaFunction ret = null;
		if(muaFunction != null) try {
			ByteArrayOutputStream out_byte = new ByteArrayOutputStream();
			ObjectOutputStream out_obj = new ObjectOutputStream(out_byte);
			out_obj.writeObject(muaFunction);
 
			ByteArrayInputStream in_byte = new ByteArrayInputStream(out_byte.toByteArray());
			ObjectInputStream in_obj = new ObjectInputStream(in_byte);
			ret = (MuaFunction) in_obj.readObject();
			
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
