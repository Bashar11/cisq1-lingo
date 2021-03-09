package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundIsNotFinishedException;
import nl.hu.cisq1.lingo.words.domain.Word;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LingoGame {

    private Long id;
    private int score ;
    private List<Round> rounds = new ArrayList<>();
    private Player player;
    private boolean gameOver;
    private State gameState;
    private String  wordToguess;
    private int wordLength;


    public LingoGame() {}

    public LingoGame(Long id, Player player, boolean gameOver, String wordToguess) {
        rounds = new ArrayList<>();
    }

    public void startNewGame(String wordTouess) {
        Round round = new Round(wordTouess);
//        round.giveHintAtBegin();
        rounds.add(round);
    }

    private void addWordLength() {
        if(this.wordLength == 7) {
            this.wordLength = 5;
        }else {
            this.wordLength++;
        }
    }

    public void startNewRound(String wordTouess) {
        if (!rounds.stream().allMatch(Round::roundFinished)) {
            throw new RoundIsNotFinishedException();
        }
        addWordLength();
        Round round = new Round(wordTouess);
        round.giveHintAtBegin();
        rounds.add(round);
    }


    public int getWordLength() {
        int wordLength = 0;
        for (Round round : rounds) {
            wordLength = round.getWordLength();
        }
        return wordLength;
    }
    public void calculateScore(int attempt){
        this.score += 5 * ( 5 - attempt ) + 5;
    }


    public void won(){
        this.gameState = State.Won;
    }

    public void lost(){
        this.gameState = State.Lose;
    }

    public boolean gameHasEnded(){
        return this.gameState.equals(State.Won)|| this.gameState.equals(State.Lose);
    }
    public Round getLastRound(){
        return rounds.get(rounds.size() -1);
    }
}
