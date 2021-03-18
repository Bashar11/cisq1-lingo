package nl.hu.cisq1.lingo.trainer.domain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundFinishedException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "lingo")
@Entity
public class LingoGame {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private double score;
    @OneToMany(mappedBy = "lingo")
    private final List<Round> rounds = new ArrayList<>();
    @Column
    private State gameState;
    @Column
    private String wordToGuess;
    @Column
    private int length;


    public boolean isRoundFinished() {
        Round lastRound = this.getLastRound();
        return lastRound.isRoundFinished();
    }


    public int provideNextWordLength() {
        int wordLength = getLastRound().getWordToGuess().length();
        if (wordLength == 5) {
            return this.length = 6;
        }

        if (wordLength == 7) {
            return this.length = 5;
        }

        return wordLength + 1;
    }

    public void startNewRound(String wordToGuess) {
        if (gameState != State.ELIMINATED ) {

                Round round = new Round(wordToGuess);
                rounds.add(round);
                gameState = State.PLAYING;
                provideNextWordLength();
        }
        else{
            throw new RoundFinishedException();
        }

    }

    public void guess(String attempt) {
        try {
            Round round = this.getLastRound();
            round.guess(attempt);
            if(round.getLastFeedback().isWordGuessed()){
                calculateScore(getLastRound().getFeedbacks().size());
                gameState = State.WAITING_FOR_ROUND;
            }
        }catch (RoundFinishedException e){
            this.gameState = State.ELIMINATED;
        };

    }


//    public Progress getProgress(double score,String lastHint) {
//        Round round = this.getLastRound();
//
//        return new Progress(
//                score,
//                round.getFeedbacks(),
//                round.getLastHint()
//        );
//    }

    public double calculateScore(int attempt) {
        this.score += 5 * (5 - attempt) + 5;
        return 0; }

    public Round getLastRound() {
        Round round = new Round();
        if(rounds.size() == 1){
            round = rounds.get(0);
        }else if(!rounds.isEmpty()){
            round = rounds.get(rounds.size() - 1);

        }
        return round;
    }

}
