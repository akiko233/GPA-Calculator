package Assignment2;

/**
 * weight of the assessment in a row of the file does not add up to 100.
 * Also, it is possible that the total grade of a student is more than 100.
 */
public class InvalidTotalException extends RuntimeException {
    /**
     * This is constructor with error message
     * @param message
     */
    public InvalidTotalException(String message) {
        super(message);
    } // end of constructor with error message
} // end of InvalidTotalException
