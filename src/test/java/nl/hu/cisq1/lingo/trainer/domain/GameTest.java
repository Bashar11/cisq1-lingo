package nl.hu.cisq1.lingo.trainer.domain;


import nl.hu.cisq1.lingo.trainer.domain.enums.State;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundIsNotFinishedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class GameTest {


    @Test
    @DisplayName("round is started and is the first word")
    void startRoundTest() {
        LingoGame game = new LingoGame();

        game.startNewRound("klaar");
        assertEquals(1, game.getRounds().size());
    }

    @Test
    @DisplayName("game is started and it has a round")
    void gettingLastRound() {
        Round round = new Round("woord");
        LingoGame game = new LingoGame();
        game.getRounds().add(round);

        assertEquals(game.getLastRound(), round);
    }

    @Test
    @DisplayName("startNewRound playing Test")
    void startNewRoundStartedRound() {
        LingoGame game = new LingoGame();
        game.startNewRound("klaar");

        assertThrows(RoundIsNotFinishedException.class, () -> game.startNewRound("staart"));
    }

    @Test
    @DisplayName("can not start a round because it's not equal to the first word")
    void startRoundTestFailed() {
        LingoGame game = new LingoGame();

        game.startNewRound("klaar");
        assertNotEquals(2, game.getRounds().size());
    }


    @Test
    @DisplayName("game is started and it has a round")
    void gameState() {
        LingoGame game = new LingoGame();
        game.startNewRound("klaar");
        assertEquals(game.getGameState(), (State.PLAYING));
    }


    @Test
    @DisplayName("calculating score, score is 25 after the first attempt when word is correctly guessed")
    void calculatingScore() {
        LingoGame game = new LingoGame();
        game.startNewRound("woorden");
        game.guess("woorden");
        assertEquals(25, game.getScore());
    }

    @Test
    @DisplayName("calculating score, score is 20 after the second attempt when word is correctly guessed")
    void calculatingScoreWithTwoAttempts() {
        LingoGame game = new LingoGame();
        game.startNewRound("woorden");
        game.guess("woordes");
        game.guess("woorden");
        assertEquals(20, game.getScore());
    }

    @Test
    @DisplayName("Testing the string of a word that should be guessed in the game object")
    void stringCheck() {
        LingoGame game = new LingoGame();
        game.startNewRound("slaan");

        assertEquals("slaan",game.getLastRound().getWordToGuess());
    }

    @Test
    @DisplayName("Testing the string of a word that should be guessed in the game object")
    void wordLengthCheck() {
        LingoGame game = new LingoGame();
        game.startNewRound("slaan");

        assertEquals(5, game.getLastRound().getWordLength());
    }


    @Test
    @DisplayName("Testing the string of a word that should be guessed in the game object")
    void gameId() {
        LingoGame game = new LingoGame();
        game.setId(1L);

        assertEquals(1, game.getId());
    }

    @Test
    @DisplayName("Testing the string of a word that should be guessed in the game object")
    void wordToguess() {
        LingoGame game = new LingoGame();
        game.setWordToGuess("finito");

        assertEquals("finito",game.getWordToGuess() );
    }

    @Test
    @DisplayName("round is not finished after the first attempt")
    void CheckingRoundFinished() {
        LingoGame game = new LingoGame();
        Round round = new Round();
        game.getRounds().add(round);
        game.startNewRound("woorden");
        game.guess("woorder");

        assertFalse(game.isRoundFinished());
    }

    @ParameterizedTest
    @MethodSource("provideNextWordLength")
    @DisplayName("Provides next word length")
    void provideNextWordLength(String word, int expectedResult) {
        LingoGame lingoGame = new LingoGame();

        lingoGame.startNewRound(word);
        assertEquals(expectedResult, lingoGame.provideNextWordLength());
    }

    static Stream<Arguments> provideNextWordLength() {
        return Stream.of(
                Arguments.of("pizza", 6),
                Arguments.of("waarde", 7),
                Arguments.of("woorden", 5)
        );
    }


}
