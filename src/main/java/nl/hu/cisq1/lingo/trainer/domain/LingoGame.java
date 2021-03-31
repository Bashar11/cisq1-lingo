package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.enums.State;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundFinishedException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundIsNotFinishedException;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "lingo")
@Entity
public class LingoGame {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private double score;
    @OneToMany
    @JoinColumn(name = "game")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private final List<Round> rounds = new ArrayList<>();
    @Column
    private State gameState = State.STARTING;
    @Column
    private String wordToGuess;
    @Column
    private int length;


    public boolean isRoundFinished() {
        Round lastRound = this.getLastRound();
        return lastRound.isRoundFinished();
    }


    public Integer provideNextWordLength() {
        if (gameState == State.STARTING || gameState == State.ELIMINATED) {
            return 5;
        }

        Integer wordLength = getLastRound().getWordLength();
        if (wordLength == 5) {
            return this.length = 6;
        } else if (wordLength == 6) {
            return this.length = 7;
        } else if (wordLength == 7) {
            return length = 5;
        }

        return wordLength;
    }


    public void startNewRound(String wordToGuess) {
        if (gameState != State.PLAYING) {

            Round round = new Round(wordToGuess);
            rounds.add(round);
            gameState = State.PLAYING;

        } else {
            throw new RoundIsNotFinishedException();
        }

    }


    public void guess(String attempt) {
        try {
            Round round = this.getLastRound();
            round.guess(attempt);

            if (round.isWon()) {
                calculateScore(getLastRound().getFeedbacks().size());
                gameState = State.WAITING_FOR_ROUND;
            }
        } catch (RoundFinishedException e) {
            this.gameState = State.ELIMINATED;
        }

    }


    public void calculateScore(int attempt) {
        this.score += 5 * (5 - attempt) + 5;
    }

    public Round getLastRound() {
        if (rounds.isEmpty()) {
            return new Round();
        }
        return rounds.get(rounds.size() - 1);
    }


}
