package src.mua.operation;

import java.util.EmptyStackException;
import java.util.Stack;

import src.mua.exception.MuaException;

public class MuaParseExpr extends Operation{

	// use for parse expression
	private int requiredArgNum = INFINITY;
	
	private Stack<Double> numStack;
	private Stack<Character> opStack;
	
	@Override
	public int getRequiredArgNum() {
		return requiredArgNum;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		numStack = new Stack<Double>();
		opStack = new Stack<Character>();
		
		for(int i=0; i<getRequiredArgNum(); i++) {
			Object obj = getArgValueAt(i);
			if(obj instanceof Double)
				numStack.push(toDouble(obj));
			else if(obj instanceof String)
				analyseToken(toString(obj));
			else
				throw new MuaException.TokenInExpr();
		}
		while(!opStack.isEmpty())
			computeTop();
		
		if(numStack.isEmpty())
			throw new MuaException.EmptyExpr();
		else if(numStack.size() != 1)
			throw new MuaException.OperandMismatch();
		
		return numStack.pop();
	}

	public void setExprFull() {
		requiredArgNum = getNowArgNum();
	}
	
	private void analyseToken(String string) throws MuaException {
		int begin = -1;
		for(int i=0; i<string.length(); i++) {
			char now = string.charAt(i);
			
			if(isOperand(now)) {
				if(begin < 0) begin = i;
			} else if(isOperator(now)) {
				pushOperand(string, begin, i);
				begin = -1;
				
				pushOperator(now);
			} else {
				throw new MuaException.TokenInExpr();
			}
		}
		pushOperand(string, begin, string.length());
	}
	
	private boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
	}
	
	private boolean isOperand(char c) {
		return c == '.' || (c >= '0' && c <= '9');
	}
	
	private void pushOperand(String string, int begin, int end) throws MuaException {
		if(begin < 0)
			return;
		else
			numStack.push(toDouble(string.substring(begin, end)));
	}

	private void pushOperator(char now) throws MuaException {
		
		while(!opStack.isEmpty()) {
			char prev = opStack.peek();
			if(priority(now) <= priority(prev))
				computeTop();
			else
				break;
		}
		opStack.push(now);
		
		if(numStack.isEmpty() && (now == '+' || now == '-'))
			numStack.push(0.0);
	}
	
	private int priority(char op) {
		int ret = 0;
		if(op == '+' || op == '-')
			ret = 1;
		else if(op == '*' || op == '/' || op == '%')
			ret = 2;
		return ret;
	}
	
	private void computeTop() throws MuaException {
		
		double a, b;
		try {
			b = numStack.pop();
			a = numStack.pop();
		} catch (EmptyStackException e) {
			throw new MuaException.OperandMismatch();
		}
		
		char op = opStack.pop();
		double res = 0;
		if(op == '+')
			res = a + b;
		else if(op == '-')
			res = a - b;
		else if(op == '*')
			res = a * b;
		else if(op == '/')
			res = a / b;
		else if(op == '%')
			res = a % b;
		
		numStack.push(res);
	}
}
