package nl.hu.cisq1.lingo.trainer.domain;


import nl.hu.cisq1.lingo.trainer.domain.exception.RoundFinishedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;


public class GameTest {


    @Test
    @DisplayName("round is started and is the first word")
    void startRoundTest(){
        LingoGame game = new LingoGame();

        game.startNewRound("klaar");
        assertEquals(game.getRounds().size(), 1);
    }

    @Test
    @DisplayName("game is started and it has a round")
    void gettingLastRound(){
        Round round = new Round("woord");
        LingoGame game = new LingoGame();
        game.getRounds().add(round);

        assertEquals(game.getLastRound(),round);
    }

    @Test
    @DisplayName("can not start a round because it's not equla to the first word")
    void startRoundTestFailed(){
        LingoGame game = new LingoGame();

        game.startNewRound("klaar");
        assertNotEquals(game.getRounds().size(), 2);
    }


    @Test
    @DisplayName("set the next word length to 6")
    void WordToGuessIncreased5(){
        LingoGame game = new LingoGame();
        game.startNewRound("baard");
        game.guess("baard");
        assertEquals(game.provideNextWordLength(), 6);
    }

    @Test
    @DisplayName("game is started and it has a round")
    void gameState(){
        LingoGame game= new LingoGame();
        game.startNewRound("klaar");
        assertEquals(game.getGameState(),(State.PLAYING));
    }

    @Test
    @DisplayName("set the next word length to 7")
    void WordToGuessIncreased6(){
        LingoGame game = new LingoGame();
        game.startNewRound("warden");
        game.guess("warden");
        assertEquals(game.provideNextWordLength(), 7);
    }

    @Test
    @DisplayName("set the next word length to 5")
    void WordToGuessIncreased7(){
        LingoGame game = new LingoGame();
        game.startNewRound("woorden");
        game.guess("woorden");
        assertEquals(game.provideNextWordLength(), 5);
    }


    @Test
    @DisplayName("calculating score, score is 25 after the first attempt when word is correctly guessed")
    void calculatingScore(){
        LingoGame game = new LingoGame();
        game.startNewRound("woorden");
        game.guess("woorden");
        assertEquals(game.getScore(),25);
    }

    @Test
    @DisplayName("calculating score, score is 20 after the second attempt when word is correctly guessed")
    void calculatingScoreWithTwoAttempts(){
        LingoGame game = new LingoGame();
        game.startNewRound("woorden");
        game.guess("woordes");
        game.guess("woorden");
        assertEquals(game.getScore(),20);
    }

    @Test
    @DisplayName("Testing the string of a word that should be guessed in the game object")
    void stringCheck() {
        LingoGame game = new LingoGame();
        game.startNewRound("slaan");

        assertEquals(game.getLastRound().getWordToGuess(),"slaan");
    }

    @Test
    @DisplayName("Testing the string of a word that should be guessed in the game object")
    void wordLengthCheck() {
        LingoGame game = new LingoGame();
        game.startNewRound("slaan");

        assertEquals(game.getLastRound().getLength(),5);
    }


    @Test
    @DisplayName("Testing the string of a word that should be guessed in the game object")
    void gameId() {
        LingoGame game = new LingoGame();
        game.setId(1L);

        assertEquals(game.getId(),1);
    }

    @Test
    @DisplayName("Testing the string of a word that should be guessed in the game object")
    void wordToguess() {
        LingoGame game = new LingoGame();
        game.setWordToGuess("finito");

        assertEquals(game.getWordToGuess(),"finito");
    }

    @Test
    @DisplayName("round is not finished after the first attempt")
    void CheckingRoundFinished(){
        LingoGame game = new LingoGame();
        Round round = new Round();
        game.getRounds().add(round);
        game.startNewRound("woorden");
        game.guess("woorder");

        assertFalse(game.isRoundFinished());
    }


    @Test
    @DisplayName("Round can't be started when player is eleminated")
    void gameCannotBeStarted() {
        LingoGame game = new LingoGame();
        game.setGameState(State.ELIMINATED);
        ;

        assertThrows(RoundFinishedException.class,()-> game.startNewRound("klaar"));
    }




}
