package src.mua.namespace;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Stack;

import src.mua.exception.MuaException;
import src.mua.operation.MuaFunction;

public class Namespace {

	// Singleton
	private static Namespace instance;

	// members
	private BindingTable global = new BindingTable();
	private Stack<BindingTable> scopeStack = new Stack<BindingTable>();
	
	private BindingTable curScope;
	
	private Namespace() {
		curScope = global;
	}
	
	public static Namespace getInstance() {
		if(instance == null) {
			instance = new Namespace();
		}
		return instance;
	}

	public void bind(String name, ArrayList<Object> list){
		curScope.bind(name, list);
	}
	
	public void bind(String name, Object value) {
		curScope.bind(name, value);
	}
	
	public void eraseName(String name) {
		curScope.eraseName(name);
	}
	
	public Object valueOfName(String name) {

		Object ret = null;
		ListIterator<BindingTable> iter = scopeStack.listIterator(scopeStack.size());
		while(ret == null && iter.hasPrevious()) {
			ret = iter.previous().valueOfName(name);
		}
		
		if(ret == null)
			ret = global.valueOfName(name);
		return ret;
	}
	
	public MuaFunction getFunction(String name) throws MuaException {
		
		MuaFunction ret = null;
		ListIterator<BindingTable> iter = scopeStack.listIterator(scopeStack.size());
		while(ret == null && iter.hasPrevious()) {
			ret = iter.previous().getFunction(name);
		}
		
		if(ret == null)
			ret = global.getFunction(name);
		return ret;
	}

	public boolean isName(String name) {
		return curScope.isName(name) || global.isName(name);
	}

	public void enterNewScope() {
		scopeStack.push(new BindingTable());
		curScope = scopeStack.peek();
	}

	public void exitCurrentScope() {
		scopeStack.pop();
		if(scopeStack.isEmpty())
			curScope = global;
		else
			curScope = scopeStack.peek();
	}

	public void export() {
		if(curScope != global)
			global.putAll(curScope);
	}
	
}
