package src.mua.operation;

import src.mua.exception.MuaException;
import src.mua.namespace.Namespace;

public class MuaThing extends Operation {

	// thing <name>
	// :<name>
	
	@Override
	public int getRequiredArgNum() {
		return 1;
	}

	@Override
	public Object execute() throws MuaException {
		String name = toString(getArgValueAt(0));
		Object value = Namespace.getInstance().valueOfName(name);
		if(value == null)
			throw new MuaException.NotAName();
		
		return value;
	}

}
