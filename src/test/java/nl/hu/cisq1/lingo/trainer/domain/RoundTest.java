package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.RoundFinishedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.enums.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class RoundTest {




    @Test
    @DisplayName("round is finished based on a right attempt")
    void roundIsFinishedByGuess() {
        Round round = new Round("klaar");
        round.guess("klaar");
        assertTrue(round.isRoundFinished());
    }

    @Test
    @DisplayName("round is finished based on a right attempt")
    void roundIsFinishedIsWonMethode() {
        Round round = new Round("klaar");
        round.guess("klaar");
        assertTrue(round.isWon());
    }

    @Test
    @DisplayName("round is not finished based when there are 4 attempt made")
    void roundNotFinishedByWrongGuess() {
        Round round = new Round("klaar");
        round.guess("klaas");
        round.guess("klaes");
        round.guess("klaws");
        round.guess("klass");
        assertFalse(round.isRoundFinished());
    }

    @Test
    @DisplayName("round is not finished based when there are 5 wrong attempts made")
    void roundIsFinishedBy5WrongGuess() {
        Round round = new Round("klaar");
        round.guess("klaas");
        round.guess("klaes");
        round.guess("klaws");
        round.guess("klass");
        round.guess("klars");
        assertTrue(round.isRoundFinished());
    }


    static Stream<Arguments> provideInitHintExamples() {

        return Stream.of(
                Arguments.of("klaar", "k...."));
    }

    @ParameterizedTest
    @MethodSource("provideInitHintExamples")
    @DisplayName("giving a hint when a letter is wrong")
    void testHint(String wordToGuess, String prevHint) {
        Round round = new Round(wordToGuess);
        assertEquals(prevHint, round.giveHintAtBegin());

    }


    @Test
    @DisplayName("Make a guess and check if last hint is set")
        //already fully tested in FeedbackTest
    void makeGuessAndLastHintGetsSet() {
        Round round = new Round("banana");
        round.guess("banana");
        assertEquals("banana", round.getLastHint());
    }

    @Test
    @DisplayName("adding feedback to feedbacks list and checking if it exists")
    void RoundTEst() {
        Round round = new Round("woord");
        Feedback feedback = new Feedback("waard", List.of(CORRECT, ABSENT, ABSENT, CORRECT, CORRECT));
        round.getFeedbacks().add(feedback);
        assertEquals(feedback, round.getLastFeedback());
    }


    @Test
    @DisplayName("Testing the string of a word that should be guessed")
    void stringCheck() {
        Round round = new Round("slaan");
        assertEquals(round.getWordToGuess(), "slaan");
    }

    @Test
    @DisplayName("Testing the number of a attempts that have been made")
    void attemptsCheck() {
        Round round = new Round("slaan");
        round.guess("staar");
        round.guess("slark");
        assertEquals(round.getAttempts(), 2);
    }

    @Test
    @DisplayName("Testing the number of a attempts that have been made")
    void idCheck() {
        Round round = new Round("slaan");
        round.guess("staar");
        round.guess("slark");
        round.setId(1L);
        assertEquals(round.getId(), 1);
    }


    @Test
    @DisplayName("throws exception error when round is finished with more than 5 attempts")
    void roundIsFinishedException() {
        Round round = new Round("klaar");
        round.guess("klaas");
        round.guess("klaes");
        round.guess("klaws");
        round.guess("klass");
        round.guess("klars");
        assertThrows(RoundFinishedException.class, () -> round.guess("klaar"));
    }


}