package nl.hu.cisq1.lingo.trainer.presentation;

import lombok.Getter;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.State;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class GameDto {

    private Long id;

    private int score;

    private State state;
    private String wordToGuess;

    private List<Round>rounds = new ArrayList<>();

    private int length;

    public GameDto(Long id) {
        this.id = id;
    }

    public GameDto(Long id, String wordToGuess){
        this.id = id;
        this.wordToGuess = wordToGuess;


    }
}
