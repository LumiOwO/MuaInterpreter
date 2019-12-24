package src.mua.operation;

import src.mua.exception.MuaException;

public class MuaWait extends Operation {

	@Override
	public int getRequiredArgNum() {
		return 1;
	}

	@Override
	protected Object exec_leaf() throws MuaException {
		double time = toDouble(getArgValueAt(0));
		synchronized(this) {
			try {
				wait((long)time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
