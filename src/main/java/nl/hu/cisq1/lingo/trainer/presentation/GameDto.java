package nl.hu.cisq1.lingo.trainer.presentation;

import lombok.Getter;
import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.enums.State;

import java.util.List;


@Getter


public class GameDto {

    private final Long id;
    private final double score;
    private final State state;
    private final int attempts;
    private final String lastHint;
    private final List<Feedback> feedbacks;

    public GameDto(LingoGame game) {
        this.id = game.getId();
        this.score = game.getScore();
        this.state = game.getGameState();
        Round lastRound = game.getLastRound();
        this.attempts = lastRound.getAttempts();
        this.lastHint = lastRound.getLastHint();
        this.feedbacks = lastRound.getFeedbacks();
    }
}
