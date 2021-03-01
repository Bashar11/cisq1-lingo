package nl.hu.cisq1.lingo.trainer.domain.exception;

public class InvalidFeedbackException extends RuntimeException {
    public InvalidFeedbackException(Integer feedback, int attempt) {
        super(" length is wrong " + feedback+ ", " + attempt );
    }
}
