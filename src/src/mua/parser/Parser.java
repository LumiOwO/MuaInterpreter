package src.mua.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Stack;

import src.mua.exception.MuaException;
import src.mua.namespace.Namespace;
import src.mua.operation.MuaFunction;
import src.mua.operation.MuaParseList;
import src.mua.operation.OpFactory;
import src.mua.operation.Operation;

public class Parser{

	private InputStream stream;
	private Scanner scanner;
	
	private Stack<Operation> opStack = new Stack<Operation>();
	
	public void parseLine(String line) throws MuaException {
		
		String uncomment = line.split("//")[0];
		stream = new ByteArrayInputStream(uncomment.getBytes());
		scanner = new Scanner(stream);
		
		while(scanner.hasNext()) {
			parseString(scanner.next());
			
			while(inProcess() && opStack.peek().fullOfArgs()) {
				
				Operation op = opStack.pop();
				
				if(op instanceof MuaParseList) {
					addArgToTop(op.execute());
				} else if(inProcess()) {
					addArgToTop(op);
				} else {
					Object res = op.execute();
					if(res != null) {
						addArgToTop(res);
					}
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
		
		if(string.isEmpty())
			return;
		
		char type_char = string.charAt(0);
		if(type_char == '[')
			parseList(string);
		else
			parseItem(string);
	}
	
	private void parseList(String string) throws MuaException {
		
		opStack.push(OpFactory.getOpByName("  parse_list  "));
		parseString(string.substring(1));
	}
	
	private void parseItem(String string) throws MuaException {
		
		string = chopListToken(string);
		if(string.isEmpty())
			return;
		
		char type_char = string.charAt(0);
		if((type_char >= '0' && type_char <= '9') 
				|| (type_char == '-'))
			parseNumber(string);
		else if(type_char == '\"')
			parseWord(string);
		else if(string.equals("true") || string.equals("false"))
			parseBool(string);
		else
			parseName(string);
	}

	private void parseNumber(String string) throws MuaException {
		
		double parsed = Double.valueOf(string);
		addArgToTop(parsed);
	}

	private void parseWord(String string) throws MuaException {
		
		String parsed = string.substring(1);
		addArgToTop(parsed);
	}

	private void parseBool(String string) throws MuaException {
		
		boolean parsed = Boolean.valueOf(string);
		addArgToTop(parsed);
	}

	private void parseName(String string) throws MuaException {
		
		Operation op = OpFactory.getOpByName(string);
		if(op == null)
			op = Namespace.getInstance().getFunction(string);
		
		if(isParsingList()) {
			if(op == null) op = new MuaFunction(string);
			addArgToTop(op);
			
		} else if(op != null) {
			opStack.push(op);
		} else {
			throw new MuaException.OpNotSupport();
		}
	}
	
	private boolean isParsingList() {
		return inProcess() && opStack.peek() instanceof MuaParseList;
	}

	private String chopListToken(String string) throws MuaException {
		
		ListIterator<Operation> iter = opStack.listIterator(opStack.size());
		
		int str_len = string.length();
		int idx = str_len;
		while(idx > 0 && string.charAt(idx - 1) == ']')
			idx --;
		String ret = string.substring(0, idx);
		
		while(idx < str_len && iter.hasPrevious()) {
			
			while(iter.hasPrevious()) {
				Operation op = iter.previous();
				if(op instanceof MuaParseList) {
					((MuaParseList)op).setListFull(idx != 0);
					idx ++;
					break;
				}
			}
		}
		
		if(idx < str_len && !iter.hasPrevious())
			throw new MuaException.ListToken();
		
		return ret;
	}

	public ArrayList<Object> compactList(ArrayList<Object> list) throws MuaException {
		return compactList(list, null, null);
	}
	
	public ArrayList<Object> compactList(ArrayList<Object> list, 
										String funcName, 
										Object argNames) throws MuaException {
		ArrayList<Object> ret = new ArrayList<Object>();
		
		Iterator<Object> iter = list.iterator();
		while(iter.hasNext()) {
			Object obj = iter.next();
			
			if(obj instanceof MuaFunction) {
				String name = ((MuaFunction)obj).getFuncName();
				MuaFunction func = Namespace.getInstance().getFunction(name);
				if(func == null && funcName != null && name.equals(funcName)) {
					func = (MuaFunction)obj;
					func.setArgsName(argNames);
				}
				
				if(func != null)
					opStack.push((Operation)Operation.clone(func));
				else
					throw new MuaException.OpNotSupport();
				
			} else if(obj instanceof Operation) {
				opStack.push((Operation)Operation.clone(obj));
			} else if(inProcess()) {
				addArgToTop(obj);
			} else {
				ret.add(obj);
			}
			
			while(inProcess() && opStack.peek().fullOfArgs()) {
				Operation op = opStack.pop();
				
				if(inProcess()) {
					addArgToTop(op);
				} else {
					ret.add(op);
				}
			}
		}
		
		if(inProcess())
			throw new MuaException.FuncDefine();
		
		return ret;
	}

}
