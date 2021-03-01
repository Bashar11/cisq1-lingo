package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;


public class FeedbackTest {



    @Test
    @DisplayName("word is guessed if all letters are correct ")
    void wordIsGuessed(){
        String attempt = "klaar";
        List<Mark> marks = List.of(Correct, Correct, Correct, Correct, Correct);
        Feedback feedback = new Feedback(attempt, marks);
        assertTrue(feedback.wordCorrect());

    }


    @Test
    @DisplayName("word is not guessed if all letters are not correct ")
    void wordIsNotGuessed(){
        String attempt = "klaar";
        List<Mark> marks = List.of(Correct, Correct, Correct, Correct, Absent);
        Feedback feedback = new Feedback(attempt, marks);
        assertFalse(feedback.wordCorrect());

    }


    @Test
    @DisplayName("guess invalid if all letters is invalid ")
    void wordGuessedInvalid(){
        String attempt = "klaar";
        List<Mark> marks = List.of(Invalid, Invalid, Invalid, Invalid);
        Feedback feedback = new Feedback(attempt, marks);
        assertTrue(feedback.guessInValid());

    }


    @Test
    @DisplayName("valid if no letter is invalid ")
    void wordGuessedvalid(){
        String attempt = "klaar";
        List<Mark> marks = List.of(Correct, Correct, Correct, Correct, Absent);
        Feedback feedback = new Feedback(attempt, marks);
        assertTrue(feedback.guessValid());

    }

    @Test
    @DisplayName("Invalid feedback")
    void invalidFeedback(){
        assertThrows(InvalidFeedbackException.class,
                () -> new Feedback("klaar", List.of(Correct)));
    }


    static Stream<Arguments> provideHintExamples(){

        return Stream.of(
                Arguments.of("klaar",List.of("k",".",".",".","."), List.of(Correct,Absent,Correct,Correct,Correct),List.of("k",".","a","a","r")));
    }

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("giving a hint when a letter is wrong")
    void testHint(String word, List<String> prevHint, List<Mark>marks, List<String> hint){
        Feedback feedback = new Feedback(marks);
        assertEquals(hint,feedback.giveHint(word, prevHint));

    }
}
