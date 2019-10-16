package src.mua.operation;

import src.mua.exception.MuaException;
import src.mua.namespace.Namespace;

public class MuaMake extends Operation {
	
	// make <name> <value>

	@Override
	public int getRequiredArgNum() {
		return 2;
	}

	@Override
	public Object execute() throws MuaException {
		String name = toString(getArgValueAt(0));
		Object value = getArgValueAt(1);
		
		if(isValid(name))
			Namespace.getInstance().bindName(name, value);
		else
			throw new MuaException.InvalidName();
		
		return null;
	}
	
	private boolean isValid(String name) {
		boolean valid = true;
		for(int i=0; valid && i<name.length(); i++) {
			char now = name.charAt(i);
			if(i == 0) {
				valid = valid && (
						now == '_' 
					|| (now >= 'a' && now <= 'z') 
					|| (now >= 'A' && now <= 'Z')
					);
			} else {
				valid = valid && (
						now == '_' 
					|| (now >= 'a' && now <= 'z') 
					|| (now >= 'A' && now <= 'Z')
					|| (now >= '0' && now <= '9')
					);
			}
		}
		return valid;
	}

}
