package src.mua.operation;

import java.util.ArrayList;

import src.mua.exception.MuaException;
import src.mua.namespace.Namespace;
import src.mua.parser.Parser;

public abstract class Operation {
	
	public static final int INFINITY = (int)1e6;
	
	private ArrayList<Object> argList = new ArrayList<Object>();
	
	public abstract int getRequiredArgNum();
	protected abstract Object exec_leaf() throws MuaException;
	
	// execute entrance
	public Object execute() throws MuaException {
		for(int i=0; i<argList.size(); i++) {
			Object nowArg = argList.get(i);
			if(nowArg instanceof Operation) {
				Object res = ((Operation)nowArg).execute();
				if(res != null)
					argList.set(i, res);
				else
					throw new MuaException.NullAsArg();
			}
		}
		
		return this.exec_leaf();
	}
	
	public void addArg(Object arg) {
		argList.add(arg);
	}
	
	public Object getArgValueAt(int i) {
		return argList.get(i);
	}
	
	public int getNowArgNum() {
		return argList.size();
	}
	
	public boolean fullOfArgs() {
		return getNowArgNum() == getRequiredArgNum();
	}
	
	public Object execList(ArrayList<Object> list) throws MuaException {
		new Parser().execList(list);
		return Namespace.getInstance().getListRes();
	}

	// static utilities
	public static Boolean toBoolean(Object value) throws MuaException {
		
		boolean ret;
		if(value instanceof Boolean)
			ret = (Boolean)value;
		else if(value instanceof String) {
			try {
				ret = Boolean.valueOf((String)value);
			} catch(Exception e) {
				throw new MuaException.TypeConvert();
			}
		}
		else
			throw new MuaException.TypeConvert();
		
		return ret;
	}
	
	public static Double toDouble(Object value) throws MuaException {
		
		double ret;
		if(value instanceof Double)
			ret = (Double)value;
		else if(value instanceof String) {
			try {
				ret = Double.valueOf((String)value);
			} catch(NumberFormatException e) {
				throw new MuaException.TypeConvert();
			}
		}
		else
			throw new MuaException.TypeConvert();
		
		return ret;
	}
	
	public static String toString(Object value) throws MuaException {
		
		String ret;
		if(value instanceof String)
			ret = (String)value;
		else
			throw new MuaException.TypeConvert();
		
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Object> toList(Object value) throws MuaException {
		
		ArrayList<Object> ret;
		if(value instanceof ArrayList)
			ret = (ArrayList<Object>)value;
		else
			throw new MuaException.TypeConvert();
		return ret;
	}
	
	public static int compare(Object a, Object b) throws MuaException {
		
		int res;
		
		if(a instanceof Double && b instanceof Double)
			res = Double.compare(toDouble(a), toDouble(b));
		else if(a instanceof String && b instanceof String)
			res = toString(a).compareTo(toString(b));
		else if(a instanceof Boolean && b instanceof Boolean)
			res = (toBoolean(a)).compareTo(toBoolean(b));
		
		else if(a instanceof String && b instanceof Double)
			res = Double.compare(toDouble(a), toDouble(b));
		else if(a instanceof String && b instanceof Boolean)
			res = (toBoolean(a)).compareTo(toBoolean(b));
		
		else if(a instanceof Double && b instanceof String)
			res = Double.compare(toDouble(a), toDouble(b));
		else if(a instanceof Boolean && b instanceof String)
			res = (toBoolean(a)).compareTo(toBoolean(b));
		
		else
			throw new MuaException.Compare();
		
		return res;
	}
	
	public static String getString(Object obj) throws MuaException
	{
		StringBuffer buffer = new StringBuffer();
		if(obj == null) {
			buffer.append("null");
		} else if(obj instanceof String) {
			buffer.append(toString(obj));
		} else if(obj instanceof Double) {
			double value = toDouble(obj);
			buffer.append(value);
		} else if(obj instanceof Boolean) {
			boolean value = toBoolean(obj);
			buffer.append(value);
		} else if(obj instanceof ArrayList) {
			buffer.append(getString_List(toList(obj)));
		}
		
		return buffer.toString();
	}
	
	private static String getString_List(ArrayList<Object> list) throws MuaException
	{
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i<list.size(); i++) {
			if(i != 0) buffer.append(" ");
			Object obj = list.get(i);
			boolean isList = obj instanceof ArrayList;
			
			if(isList) buffer.append("[");
			buffer.append(getString(obj));
			if(isList) buffer.append("]");
		}
		return buffer.toString();
	}

}
