package src.mua.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

import src.mua.exception.MuaException;
import src.mua.namespace.Namespace;
import src.mua.operation.MuaParseList;
import src.mua.operation.MuaStop;
import src.mua.operation.OpFactory;
import src.mua.operation.Operation;

public class Parser{

	private InputStream stream;
	private Scanner scanner;
	
	private Stack<Operation> opStack = new Stack<Operation>();
	
	private boolean defaultOP = true;
	
	public void parseLine(String line) throws MuaException {
		
		String uncomment = line.split("//")[0];
		stream = new ByteArrayInputStream(uncomment.getBytes());
		scanner = new Scanner(stream);
		
		while(scanner.hasNext()) {
			parseString(scanner.next());
			popStack();
		}
	}

	private void popStack() throws MuaException {
		
		while(inProcess() && opStack.peek().fullOfArgs()) {
			Operation op = opStack.pop();
			if(inProcess()) {
				addArgToTop(op);
			} else {
				Object res = op.execute();
				if(defaultOP && res != null) {
					addArgToTop(res);
				}
			}
		}

	}

	public boolean inProcess() {
		return !opStack.isEmpty();
	}
	
	private void addArgToTop(Object arg) throws MuaException{
		if(!inProcess()) {
			opStack.push(OpFactory.getOpByName("print"));
		}
			
		opStack.peek().addArg(arg);
	}

	private void parseString(String string) throws MuaException {
		
		int begin = -1;
		for(int i=0; i<string.length(); i++) {
			char now = string.charAt(i);
			if(now == '[') {
				if(begin >= 0) {
					parseItem(string.substring(begin, i));
					begin = -1;
				}
				// begin parse list
				opStack.push(OpFactory.getOpByName("  parse_list  "));
				
			} else if(now == ']') {
				if(begin >= 0) {
					parseItem(string.substring(begin, i));
					begin = -1;
				}
				// end parse list
				if(isParsingList()) {
					((MuaParseList)opStack.peek()).setListFull();
					popStack();
				} else {
					throw new MuaException.ListToken();
				}
				
			} else if(begin < 0) {
				begin = i;
			}
		}
		
		if(begin >= 0) {
			parseItem(string.substring(begin));
			begin = -1;
		}
			
	}
	
	private void parseItem(String string) throws MuaException {
		
//		System.out.println(string);
		char type_char = string.charAt(0);
		if((type_char >= '0' && type_char <= '9') 
				|| (type_char == '-'))
			parseNumber(string);
		else if(string.equals("true") || string.equals("false"))
			parseBool(string);
		else if(isParsingList() || type_char == '\"')
			parseWord(isParsingList()? string: string.substring(1));
		else
			parseName(string);
	}

	private void parseNumber(String string) throws MuaException {
		
		double parsed = Double.valueOf(string);
		addArgToTop(parsed);
	}

	private void parseWord(String string) throws MuaException {
		
		addArgToTop(string);
	}

	private void parseBool(String string) throws MuaException {
		
		boolean parsed = Boolean.valueOf(string);
		addArgToTop(parsed);
	}

	private void parseName(String string) throws MuaException {
		
		Operation op = OpFactory.getOpByName(string);
		if(op == null)
			op = Namespace.getInstance().getFunction(string);
		
		if(op != null) {
			opStack.push(op);
		} else {
			throw new MuaException.OpNotSupport();
		}
	}
	
	private boolean isParsingList() {
		return inProcess() && opStack.peek() instanceof MuaParseList;
	}

	public void execList(ArrayList<Object> list) throws MuaException {
		defaultOP = false;
		
		Iterator<Object> iter = list.iterator();
		while(iter.hasNext()) {
			Object item = iter.next();
			
			if(item instanceof ArrayList
					|| item instanceof Double
					|| item instanceof Boolean) {
				if(inProcess())
					addArgToTop(item);
				else
					throw new MuaException.OpNotSupport();
				
			} else if(item instanceof String) {
				String string = (String)item;
				Operation op = OpFactory.getOpByName(string);
				if(op == null)
					op = Namespace.getInstance().getFunction(string);
				
				if(op instanceof MuaStop)
					break;
				else if(op != null)
					opStack.push(op);
				else if(inProcess()) {
					addArgToTop(string);
				} else {
					throw new MuaException.OpNotSupport();
				}
			}
			
			popStack();
		}
	}

}
