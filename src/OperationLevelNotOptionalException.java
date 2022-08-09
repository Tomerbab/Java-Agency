public class OperationLevelNotOptionalException extends RuntimeException {
	
	public OperationLevelNotOptionalException (String msg){
		super(msg);
	}
	
		public OperationLevelNotOptionalException() {
		super();
	}
}