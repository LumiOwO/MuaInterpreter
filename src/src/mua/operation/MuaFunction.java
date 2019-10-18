package src.mua.operation;

import java.util.ArrayList;
import java.util.Iterator;

import src.mua.exception.MuaException;
import src.mua.namespace.Namespace;

@SuppressWarnings("serial")
public class MuaFunction extends Operation {

	private ArrayList<String> argsName = new ArrayList<String>();
	private ArrayList<Operation> steps = new ArrayList<Operation>();
	
	public MuaFunction(ArrayList<Object> argsName, ArrayList<Object> steps) throws MuaException {
		
		Iterator<Object> iter = argsName.iterator();
		while(iter.hasNext()) {
			try {
				String name = toString(iter.next());
				this.argsName.add(name);
			} catch (MuaException.TypeConvert e) {
				throw new MuaException.FuncDefine();
			}
		}
		
		iter = steps.iterator();
		while(iter.hasNext()) {
			Operation op = (Operation)iter.next();
			this.steps.add(op);
		}
	}
	
	@Override
	public int getRequiredArgNum() {
		return argsName.size();
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		Namespace namespace = Namespace.getInstance();
		
		namespace.enterNewScope();
		for(int i=0; i<getRequiredArgNum(); i++) {
			namespace.bind(argsName.get(i), getArgValueAt(i));
		}
		Object ret = execSteps();
		namespace.exitCurrentScope();
		
		return ret;
	}

	private Object execSteps() throws MuaException {
		
		Object ret = null;
		Iterator<Operation> iter = steps.iterator();
		while(iter.hasNext()) {
			Operation op = iter.next();
			Object res = op.execute();
			if(op instanceof MuaOutput) {
				ret = res;
			} else if(op instanceof MuaStop) {
				break;
			}
		}
		return ret;
	}

}
