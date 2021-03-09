package nl.hu.cisq1.lingo.trainer.domain.exception;

public class RoundIsNotFinishedException extends RuntimeException {
    public RoundIsNotFinishedException() {
        super("Round is not finished yet");
    }
}
