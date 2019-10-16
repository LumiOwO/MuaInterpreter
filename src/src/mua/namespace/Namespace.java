package src.mua.namespace;

import java.util.Stack;

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

	public void bindName(String name, Object value) {
		curScope.bindName(name, value);
	}
	
	public void eraseName(String name) {
		curScope.eraseName(name);
	}
	
	public Object valueOfName(String name) {
		return curScope.valueOfName(name);
	}

	public boolean isName(String name) {
		return curScope.isName(name);
	}
	
}
