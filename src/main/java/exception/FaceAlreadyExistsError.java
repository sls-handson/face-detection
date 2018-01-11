package exception;

public class FaceAlreadyExistsError extends RuntimeException {

	private final String message;

	public FaceAlreadyExistsError(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
