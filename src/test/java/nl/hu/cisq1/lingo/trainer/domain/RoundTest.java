package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.words.domain.Word;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class RoundTest {


    @Test
    @DisplayName("makes a round finished")
    void roundOver() {
        Word word = new Word("woord");
        Round round = new Round(word);

        round.getFeedbacks("waard", List.of(Correct, Correct, Correct, Correct, Correct));

        assertTrue(round.roundFinished());
    }






//    @Test
//    @DisplayName("increase round number when the word is correctly guessed")
//    void increaseRound() {
//        Word word = new Word("boord");
//        Feedback feedback = new Feedback("boord", List.of(Correct, Correct, Correct, Correct, Correct));
//        Round round = new Round(word.toString(), feedback);
//        assertTrue(round.increaseRound());
//    }

    @Test
    @DisplayName("Dont increase round number when the word is not correctly guessed")
    void dontIncreaseRound() {
        Word word = new Word("woord");
        Feedback feedback = new Feedback("boord", List.of(Absent, Absent, Absent, Absent, Absent));
        Round round = new Round(word.toString(), feedback);
        assertFalse(round.increaseRound());
    }

    @Test
    @DisplayName("increase turn number when the word is not guessed")
    void increaseAttempt() {
        Word word = new Word("klaar");
        Feedback feedback = new Feedback("ksaar", List.of(Correct,Absent,Correct,Correct,Correct));
        Round round = new Round(word.toString(), feedback);
        assertTrue(round.increaseAttempt(1));
    }

    @Test
    @DisplayName("do not increase turn number when the word is correctly guessed")
    void dontIncreaseAttempt() {
        Word word = new Word("boord");
        Round round = new Round(word);
        round.getFeedbacks("boord", List.of(Correct, Correct, Correct, Correct, Correct));

        assertFalse(round.increaseAttempt(1));
    }


    @Test
    @DisplayName("round is not finished based on guess attempt that has been made")
    void roundIsNotFinished() {
        Word word = new Word("woord");
        Feedback feedback = new Feedback("boord", List.of(Absent, Correct, Correct, Correct, Correct));

        Round round = new Round(word.toString(), feedback);

        assertFalse(round.roundFinished());
    }

    @Test
    @DisplayName("round is finished based on too many guesses")
    void roundIsNotFinishedTooManyAttempts() {

        Round round = new Round("woord");
        round.getFeedbacks("boord", List.of(Absent, Correct, Correct, Correct, Correct));
        round.getFeedbacks("boord", List.of(Absent, Correct, Correct, Correct, Correct));
        round.getFeedbacks("boord", List.of(Absent, Correct, Correct, Correct, Correct));
        round.getFeedbacks("boord", List.of(Absent, Correct, Correct, Correct, Correct));
        round.getFeedbacks("boord", List.of(Absent, Correct, Correct, Correct, Correct));
        round.getFeedbacks("boord", List.of(Absent, Correct, Correct, Correct, Correct));
        assertTrue(round.roundFinished());
    }


    static Stream<Arguments> provideInitHintExamples(){

        return Stream.of(
                Arguments.of("klaar","k...."));
    }

    @ParameterizedTest
    @MethodSource("provideInitHintExamples")
    @DisplayName("giving a hint when a letter is wrong")
    void testHint(String wordToGuess, String prevHint){
        Round round = new Round(wordToGuess);
        assertEquals(prevHint,round.giveHintAtBegin());

    }


    @Test
    @DisplayName("Make a guess and check if last hint is set") //already fully tested in FeedbackTest
    void makeGuessAndLastHintGetsSet() {
        Round round = new Round("banana");
        round.guess("banana");
        assertEquals("banana", round.getLastHint());
    }

    @Test
    void getFeedback() {
        Round round = new Round("banana");
        round.guess("sanana");
        assertEquals(List.of(new Feedback("sanana", List.of(Absent, Correct, Correct, Correct, Correct, Correct))), round.getFeedbacks());
    }








}