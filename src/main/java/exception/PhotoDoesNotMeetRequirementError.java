package exception;

public class PhotoDoesNotMeetRequirementError extends RuntimeException {

	private final String message;

	public PhotoDoesNotMeetRequirementError(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
