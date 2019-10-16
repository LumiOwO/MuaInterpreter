package src.mua.exception;

public abstract class MuaException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public abstract String getErrorInfo();
	public abstract boolean isFatal();
	
	public void printErrorInfo() {
		String preFix = isFatal()? "Fatal Error: ": "Error: ";
		System.out.println(preFix + getErrorInfo());
	}
	
	// instances of exception
	public static final class Unknown extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "Unknown error. Quit.";
		}
		
		@Override
		public boolean isFatal() {
			return true;
		}

	}
	
	public static final class TypeConvert extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "Literal cannot be converted to the desired type.";
		}
		
		@Override
		public boolean isFatal() {
			return false;
		}

	}
	
	public static final class Compare extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "Values cannot be compared as value type mismatch. ";
		}

		@Override
		public boolean isFatal() {
			return false;
		}
		
	}
	
	public static final class OpNotSupport extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "An unsupported operation was attempted.";
		}

		@Override
		public boolean isFatal() {
			return false;
		}
		
	}
	
	public static final class ArgToNullOp extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "Argument is passing in an null operation.";
		}

		@Override
		public boolean isFatal() {
			return false;
		}
		
	}
	
	public static final class InvalidName extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "Variable name does not match the pattern.";
		}

		@Override
		public boolean isFatal() {
			return false;
		}
		
	}
	
	public static final class NotAName extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "Target of the operation \"thing\" or \":\" is not a name.";
		}

		@Override
		public boolean isFatal() {
			return false;
		}
		
	}
	
	public static final class EmptyInput extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "Standard input stream does not detect any input.";
		}

		@Override
		public boolean isFatal() {
			return true;
		}
		
	}
}
