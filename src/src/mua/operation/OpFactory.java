package src.mua.operation;

import src.mua.exception.MuaException;

public class OpFactory {
	
	private OpFactory() {}
	
	public static Operation getOpByName(String name) throws MuaException{
		
		Operation op = null;
		if(name.equals("make"))
			op = new MuaMake();
		else if(name.equals("thing"))
			op = new MuaThing();
		else if(name.charAt(0) == ':')
			op = getThingForShort(name);
		else if(name.equals("erase"))
			op = new MuaErase();
		else if(name.equals("isname"))
			op = new MuaIsName();
		
		else if(name.equals("print"))
			op = new MuaPrint();
		else if(name.equals("read"))
			op = new MuaRead();
		
		else if(name.equals("add"))
			op = new MuaAdd();
		else if(name.equals("sub"))
			op = new MuaSub();
		else if(name.equals("mul"))
			op = new MuaMul();
		else if(name.equals("div"))
			op = new MuaDiv();
		else if(name.equals("mod"))
			op = new MuaMod();
		
		else if(name.equals("eq"))
			op = new MuaEqual();
		else if(name.equals("gt"))
			op = new MuaGreaterThan();
		else if(name.equals("lt"))
			op = new MuaLessThan();
		
		else if(name.equals("and"))
			op = new MuaAnd();
		else if(name.equals("or"))
			op = new MuaOr();
		else if(name.equals("not"))
			op = new MuaNot();
		
		else if(name.equals("output"))
			op = new MuaOutput();
		else if(name.equals("stop"))
			op = new MuaStop();
		else if(name.equals("export"))
			op = new MuaExport();
		
		else if(name.equals("quit"))
			op = new MuaQuit();
		
		// not for command line
		else if(name.equals("  parse_list  "))
			op = new MuaParseList();
		
		return op;
	}

	private static Operation getThingForShort(String name) throws MuaException {
		
		Operation ret = new MuaThing();
		
		// deal with multi ":"
		Operation now = ret;
		Operation next = null;
		int idx;
		for(idx = 1; idx < name.length(); idx++) {
			if(name.charAt(idx) == ':') {
				next = new MuaThing();
				now.addArg(next);
				
				now = next;
				next = null;
			} else {
				break;
			}
		}
		
		if(idx < name.length()) {
			now.addArg(name.substring(idx));
		} else {
			throw new MuaException.ThingNullTarget();
		}
		
		return ret;
	}
}
