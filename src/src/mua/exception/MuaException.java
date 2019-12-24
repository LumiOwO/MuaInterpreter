package src.mua.exception;

public abstract class MuaException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public abstract String getErrorInfo();
	public abstract boolean isFatal();
	
	public void printErrorInfo() {
		String preFix = isFatal()? "Fatal Error: ": "Error: ";
		System.out.println(preFix + getErrorInfo());
//		printStackTrace();
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
			return "An unsupported operation or an unimplemented function was attempted.";
		}

		@Override
		public boolean isFatal() {
			return false;
		}
		
	}
	
	public static final class NullAsArg extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "Some arguments of the operation are null values.";
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
	
	public static final class ThingNullTarget extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "No target for the operation \":\".";
		}

		@Override
		public boolean isFatal() {
			return false;
		}
		
	}
	
	public static final class FuncDefine extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "Some operations do not have enough parameters.";
		}

		@Override
		public boolean isFatal() {
			return false;
		}
		
	}
	
	public static final class BracketMismatch extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "The right bracket ] or ) does not match the list/expression.";
		}

		@Override
		public boolean isFatal() {
			return false;
		}
		
	}
	
	public static final class RepeatTimes extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "The repeat times is not an integer.";
		}

		@Override
		public boolean isFatal() {
			return false;
		}
		
	}
	
	public static final class ListExecute extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "Raw value cannot be a member in the executing list.";
		}

		@Override
		public boolean isFatal() {
			return false;
		}
		
	}
	
	public static final class Random extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "The bound for random number must be positive.";
		}

		@Override
		public boolean isFatal() {
			return false;
		}
		
	}
	
	public static final class TokenInExpr extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "Some characters are not supported in the expression.";
		}

		@Override
		public boolean isFatal() {
			return false;
		}
		
	}
	
	public static final class EmptyExpr extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "The expression is empty.";
		}

		@Override
		public boolean isFatal() {
			return false;
		}
		
	}
	
	public static final class OperandMismatch extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "Number of operators and operands do not match.";
		}

		@Override
		public boolean isFatal() {
			return false;
		}
		
	}
	
	public static final class InvalidFunc extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "The format of attempted name is not valid for function.";
		}

		@Override
		public boolean isFatal() {
			return false;
		}
		
	}
	
	public static final class EmptyListWord extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "The list or word is empty.";
		}

		@Override
		public boolean isFatal() {
			return false;
		}
		
	}
	
	public static final class FileNotFound extends MuaException {

		private static final long serialVersionUID = 1L;

		@Override
		public String getErrorInfo() {
			return "Target file is not found.";
		}

		@Override
		public boolean isFatal() {
			return false;
		}
		
	}
	
}
