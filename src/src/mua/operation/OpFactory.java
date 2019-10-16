package src.mua.operation;

public class OpFactory {
	
	private OpFactory() {}
	
	public static Operation getOpByName(String name){
		
		Operation op = null;
		if(name.equals("make"))
			op = new MuaMake();
		else if(name.equals("thing"))
			op = new MuaThing();
		else if(name.charAt(0) == ':') {
			op = new MuaThing();
			if(name.length() > 1)
				op.addArg(name.substring(1));
		}
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
		
		else if(name.equals("quit"))
			op = new MuaQuit();
		
		// not for command line
		else if(name.equals("  parse_list  "))
			op = new MuaParseList();
		
		return op;
	}
}
