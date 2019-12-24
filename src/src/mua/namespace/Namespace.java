package src.mua.namespace;

import src.mua.exception.MuaException;
import src.mua.operation.MuaFunction;

public class Namespace {

	// Singleton
	private static Namespace instance;

	// members
	private BindingTable global = new BindingTable();
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
	
	public Object getOutput() {
		return curScope.getOutput();
	}

	public void setOutput(Object output) {
		curScope.setOutput(output);
	}

	public void bind(String name, Object value) {
		curScope.bind(name, value);
	}
	
	public void eraseName(String name) {
		curScope.eraseName(name);
	}
	
	public Object valueOfName(String name) {
		Object ret = curScope.valueOfName(name);
		if(ret == null && !inGlobal())
			ret = global.valueOfName(name);
		return ret;
	}
	
	public MuaFunction getFunction(String name) throws MuaException {
		MuaFunction ret = curScope.getFunction(name);
		if(ret == null && !inGlobal())
			ret = global.getFunction(name);
		return ret;
	}

	public boolean isName(String name) {
		return curScope.isName(name) || global.isName(name);
	}

	public void enterScope(BindingTable scope) {
		curScope = scope;
	}

	public void enterGlobal() {
		curScope = global;
	}
	
	public BindingTable getCurScope() {
		return curScope;
	}

	public void export(String name) {
		if(!inGlobal() && curScope.isName(name)) {
			global.bind(name, curScope.valueOfName(name));
		}
	}

	public boolean hasStopped() {
		return curScope.hasStopped();
	}
	
	public void stopFuncExec() {
		if(!inGlobal())
			curScope.stopFuncExec();
	}
	
	public boolean inGlobal() {
		return curScope == global;
	}

	public void setListRes(Object value) {
		curScope.setListRes(value);
	}
	
	public Object getListRes() {
		return curScope.getListRes();
	}

	public void save(String path) throws MuaException {
		curScope.save(path);
	}
	
	public void load(String path) throws MuaException {
		curScope.load(path);
	}
	
	public void erall() {
		curScope.erall();
	}
	
	public void poall() {
		curScope.poall();
	}
	
}
