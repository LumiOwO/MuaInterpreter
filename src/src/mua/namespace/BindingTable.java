package src.mua.namespace;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import src.mua.exception.MuaException;
import src.mua.operation.MuaFunction;
import src.mua.operation.Operation;
import src.mua.parser.Parser;

public class BindingTable {
	// value type: double, boolean, string, ArrayList
	// variable name: " " + string
	
	private HashMap<String, Object> nameTable = new HashMap<String, Object>();
	
	private Object output = null;
	private Object list_res = null;
	private boolean stopSignal = false;
	
	public Object getOutput() {
		return output;
	}

	public void setOutput(Object output) {
		this.output = output;
	}
	
	public void bind(String name, Object value) {
		nameTable.put(name, value);
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
		
		Object obj = nameTable.get(name);
		
		MuaFunction ret = null;
		if(obj != null) try {
			ArrayList<Object> list = Operation.toList(obj);
			if(list.size() != 2)
				throw new MuaException.InvalidFunc();
			else {
				ret = new MuaFunction(list.get(0), list.get(1));
			} 
				
		} catch(MuaException.TypeConvert e) {
			throw new MuaException.InvalidFunc();
		}
		
		return ret;
	}

	public boolean hasStopped() {
		return stopSignal;
	}

	public void stopFuncExec() {
		stopSignal = true;
	}

	public void setListRes(Object value) {
		this.list_res = value;
	}
	
	public Object getListRes() {
		return list_res;
	}

	public void save(String path) throws MuaException {
		try {
			File writename = new File(path);  
            writename.createNewFile();
            
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            Iterator<Entry<String, Object>> iter = nameTable.entrySet().iterator();
            while(iter.hasNext()) {
            	HashMap.Entry<String, Object> entry = iter.next();
            	String key = entry.getKey();
            	Object val = entry.getValue();
            	boolean isList = val instanceof ArrayList;
            	boolean isWord = val instanceof String;
            	
            	StringBuffer buffer = new StringBuffer();
            	if(isList) buffer.append('[');
            	if(isWord) buffer.append('\"');
            	buffer.append(Operation.getString(val));
            	if(isList) buffer.append(']');
            	
            	out.write("make \"" + key + " " + buffer.toString() + "\n");
            }
            
            out.flush();
			out.close();
		} catch (MuaException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void load(String path) throws MuaException {
		try {
            BufferedReader in = new BufferedReader(
            					new InputStreamReader(
            					new FileInputStream(new File(path))));
            Parser parser = new Parser();
            while(true) {
            	String line = in.readLine();
            	if(line == null)
            		break;
            	parser.parseLine(line);
            }
            in.close();
            
		} catch (MuaException e) {
			throw e;
		} catch (FileNotFoundException e) {
			throw new MuaException.FileNotFound();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void erall() {
		nameTable.clear();
	}

	public void poall() {
		Iterator<Entry<String, Object>> iter = nameTable.entrySet().iterator();
		while(iter.hasNext()) {
			System.out.print(iter.next().getKey() + ' ');
		}
		System.out.println();
	}
}
