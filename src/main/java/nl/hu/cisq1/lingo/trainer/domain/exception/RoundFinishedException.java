package nl.hu.cisq1.lingo.trainer.domain.exception;

public class RoundFinishedException extends RuntimeException {
    public RoundFinishedException() {

        super("Round is finished");
    }
}
