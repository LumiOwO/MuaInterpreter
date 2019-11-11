package src.mua.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

import src.mua.exception.MuaException;
import src.mua.namespace.Namespace;
import src.mua.operation.MuaParseExpr;
import src.mua.operation.MuaParseList;
import src.mua.operation.OpFactory;
import src.mua.operation.Operation;

public class Parser{

	private InputStream stream;
	private Scanner scanner;
	
	private Stack<Operation> opStack = new Stack<Operation>();
	
	private boolean defaultOP = true;
	
	public void parseLine(String line) throws MuaException {
		defaultOP = true;
		
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
		analyseListToken(string);
	}

	private void analyseListToken(String string) throws MuaException {
		
		int begin = -1;
		for(int i=0; i<string.length(); i++) {
			char now = string.charAt(i);
			
			if(now == '[' || now == ']') {
				parseList(string, begin, i);
				begin = -1;
				
				if(now == '[') {
					opStack.push(OpFactory.getOpByName("  parse_list  "));
				} else if(isParsingList()) {
					((MuaParseList)opStack.peek()).setListFull();
					popStack();
				} else {
					throw new MuaException.BracketMismatch();
				}
				
			} else if(begin < 0) {
				begin = i;
			}
		}
		
		parseList(string, begin, string.length());	
	}
	
	private boolean isParsingList() {
		return inProcess() && opStack.peek() instanceof MuaParseList;
	}
	
	private void parseList(String string, int begin, int end) throws MuaException {
		if(begin < 0)
			return;
		else if(isParsingList())
			parseWord(string.substring(begin, end));
		else
			analyseExprToken(string.substring(begin, end));
	}

	private void analyseExprToken(String string) throws MuaException {
		int begin = -1;
		for(int i=0; i<string.length(); i++) {
			char now = string.charAt(i);
			
			if(now == '(' || now == ')') {
				parseExpr(string, begin, i);
				begin = -1;
				
				if(now == '(') {
					opStack.push(OpFactory.getOpByName("  parse_expression  "));
				} else if(isParsingExpr()) {
					((MuaParseExpr)opStack.peek()).setExprFull();
					popStack();
				} else {
					throw new MuaException.BracketMismatch();
				}
				
			} else if(begin < 0) {
				begin = i;
			}
		}
		
		parseExpr(string, begin, string.length());
	}

	private boolean isParsingExpr() {
		return inProcess() && opStack.peek() instanceof MuaParseExpr;
	}

	private void parseExpr(String string, int begin, int end) throws MuaException {
		if(begin < 0)
			return;
		else if(isParsingExpr())
			parseWord(string.substring(begin, end));
		else
			parseLiteral(string.substring(begin, end));
	}

	private void parseLiteral(String string) throws MuaException {
		
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
	
	public void execList(ArrayList<Object> list) throws MuaException {
		defaultOP = false;
		
		Iterator<Object> iter = list.iterator();
		while(iter.hasNext() && !Namespace.getInstance().hasStopped()) {
			Object item = iter.next();
			
			if(item instanceof ArrayList) {
				if(inProcess())
					addArgToTop(item);
				else
					throw new MuaException.OpNotSupport();
				
			} else if(item instanceof String) {
				parseString((String)item);
			}
			
			popStack();
		}
	}

}
