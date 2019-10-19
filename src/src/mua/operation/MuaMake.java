package src.mua.operation;

import java.util.ArrayList;

import src.mua.exception.MuaException;
import src.mua.namespace.Namespace;
import src.mua.parser.Parser;

@SuppressWarnings("serial")
public class MuaMake extends Operation {
	
	// make <name> <value>
	
	@Override
	public int getRequiredArgNum() {
		return 2;
	}
	
	@Override
	public Object execute() throws MuaException {
		
		String name = toString(getArgValueAt(0));
		Object value = getArgValueAt(1);
		
		if(!isValid(name))
			throw new MuaException.InvalidName();
		else if(isFuncDefine(value)) {
//			System.out.println("func");
			bindFunction(name, toList(value));
		} else {
//			System.out.println("name");
			if(value instanceof ArrayList)
				value = new Parser().compactList(toList(value));
			
			super.execute();
		}
		
		return null;
	}
	
	@Override
	protected Object exec_leaf() throws MuaException {
		
		String name = toString(getArgValueAt(0));
		Object value = getArgValueAt(1);
		bindName(name, value);
		
		return null;
	}
	
	private void bindFunction(String name, ArrayList<Object> list) {
		Namespace.getInstance().bind(name, list);	
	}

	private void bindName(String name, Object value) {
		
		Namespace.getInstance().bind(name, value);		
	}

	private boolean isValid(String name) {
		boolean valid = true;
		for(int i=0; valid && i<name.length(); i++) {
			char now = name.charAt(i);
			if(i == 0) {
				valid = valid && (
						now == '_' 
					|| (now >= 'a' && now <= 'z') 
					|| (now >= 'A' && now <= 'Z')
					);
			} else {
				valid = valid && (
						now == '_' 
					|| (now >= 'a' && now <= 'z') 
					|| (now >= 'A' && now <= 'Z')
					|| (now >= '0' && now <= '9')
					);
			}
		}
		return valid;
	}
	
	private boolean isFuncDefine(Object value) throws MuaException {
		boolean ret;
		if(!(value instanceof ArrayList))
			ret = false;
		else {
			ArrayList<Object> list = toList(value);
			if(list.size() != 2)
				ret = false;
			else if(!( (list.get(0) instanceof ArrayList) && (list.get(1) instanceof ArrayList) ))
				ret = false;
			else {
				ArrayList<Object> argNames = toList(list.get(0));
				ArrayList<Object> steps = toList(list.get(1));
				if(!allWord(argNames))
					ret = false;
				else if(!existOp(steps))
					ret = false;
				else {
					String funcName = toString(getArgValueAt(0));
					steps = new Parser().compactList(steps, funcName, argNames);
					
					ret = allOp(steps);
				}
			}
		}
		return ret;
	}

}
