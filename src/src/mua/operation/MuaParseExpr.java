package src.mua.operation;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

import src.mua.exception.MuaException;

public class MuaParseExpr extends Operation{

	// use for parse expression
	private int requiredArgNum = INFINITY;
	
	private Stack<Double> numStack;
	private Stack<Character> opStack;
	private ArrayList<Object> listBuf;
	
	private boolean isPrevOperator;
	private int sign;
	
	@Override
	public int getRequiredArgNum() {
		return requiredArgNum;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		isPrevOperator = true;
		sign = 1;
		
		numStack = new Stack<Double>();
		opStack = new Stack<Character>();
		listBuf = new ArrayList<Object>();
		
		for(int i=0; i<getRequiredArgNum(); i++) {
			Object obj = getArgValueAt(i);
			if(obj instanceof Double) {
				listBuf.add(obj);
				isPrevOperator = false;
			} else if(obj instanceof String) {
				analyseToken(toString(obj));
			} else {
				throw new MuaException.TokenInExpr();
			}
		}
		if(!listBuf.isEmpty()) {
			pushListRes();
		}
		while(!opStack.isEmpty())
			computeTop();
		
		if(numStack.isEmpty())
			throw new MuaException.EmptyExpr();
		else if(numStack.size() != 1)
			throw new MuaException.OperandMismatch();
		
		return numStack.pop();
	}

	private void pushListRes() throws MuaException {
		double num = toDouble(execList(listBuf)); 
		numStack.push(num * sign);
		sign = 1;
		listBuf.clear();
	}

	public void setExprFull() {
		requiredArgNum = getNowArgNum();
	}
	
	private void analyseToken(String string) throws MuaException {
		int begin = -1;
		for(int i=0; i<string.length(); i++) {
			char now = string.charAt(i);
			
			if(!isOperator(now)) {
				if(begin < 0) begin = i;
				isPrevOperator = false;
			} else {
				if(isPrevOperator) {
					if(now == '+') {
						sign *= 1;
					} else if(now == '-') {
						sign *= -1;
					} else {
						throw new MuaException.OperandMismatch();
					}
				} else {
					if(begin >= 0) {
						listBuf.add(string.substring(begin, i));
					}
					begin = -1;
					pushOperator(now);
				}
				
				isPrevOperator = true;
			}
		}
		if(begin >= 0) {
			listBuf.add(string.substring(begin, string.length()));
		}
	}
	
	private boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
	}
	
	private void pushOperator(char now) throws MuaException {
		
		if(!listBuf.isEmpty())
			pushListRes();
		while(!opStack.isEmpty()) {
			char prev = opStack.peek();
			if(priority(now) <= priority(prev))
				computeTop();
			else
				break;
		}
		opStack.push(now);
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
