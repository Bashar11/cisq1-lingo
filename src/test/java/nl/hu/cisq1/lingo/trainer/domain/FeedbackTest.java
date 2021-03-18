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
    void wordIsGuessed() {
        String attempt = "klaar";
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT);
        Feedback feedback = new Feedback(attempt, marks);
        assertTrue(feedback.isWordGuessed());

    }


    @Test
    @DisplayName("word is not guessed if all letters are not correct ")
    void wordIsNotGuessed() {
        String attempt = "klaar";
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT);
        Feedback feedback = new Feedback(attempt, marks);
        assertFalse(feedback.isWordGuessed());

    }


    @Test
    @DisplayName("guess invalid if all letters is invalid ")
    void wordGuessedInvalid() {
        String attempt = "klaar";
        List<Mark> marks = List.of(INVALID, INVALID, INVALID, INVALID, INVALID);
        Feedback feedback = new Feedback(attempt, marks);
        assertTrue(feedback.guessInValid());

    }


    @Test
    @DisplayName("valid if no letter is invalid ")
    void wordGuessedvalid() {
        String attempt = "klaar";
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT);
        Feedback feedback = new Feedback(attempt, marks);
        assertTrue(feedback.guessValid());

    }

    @Test
    @DisplayName("Invalid feedback")
    void invalidFeedback() {
        assertThrows(InvalidFeedbackException.class,
                () -> new Feedback("klaar", List.of(CORRECT)));
    }


    static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of("klaar", "k....", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT), "klaar"),
                Arguments.of("klaar", "k....", List.of(CORRECT, ABSENT, ABSENT, ABSENT, ABSENT), "k...."),
                Arguments.of("klaar", "k....", List.of(CORRECT, PRESENT, PRESENT, PRESENT, PRESENT), "k...."),
                Arguments.of("klaar", "k....", List.of(CORRECT, ABSENT, CORRECT, CORRECT, CORRECT), "k.aar"),
                Arguments.of("klaar", "k....", List.of(CORRECT, ABSENT, CORRECT, PRESENT, CORRECT), "k.a.r"),
                Arguments.of("klaar", "kl.a.", List.of(CORRECT, ABSENT, CORRECT, PRESENT, CORRECT), "klaar")
        );
    }

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("giving a hint based on previous hint and word")
    void testHint(String attempt, String prevHint, List<Mark> marks, String hint) {
        Feedback feedback = new Feedback(attempt, marks);
        assertEquals(hint, feedback.giveHint(attempt, prevHint));
    }


    static Stream<Arguments> provideFeedbackExamples() {
        return Stream.of(
                Arguments.of("klaar", "klaar", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT)),
                Arguments.of("woord", "wadde", List.of(CORRECT, ABSENT, PRESENT, ABSENT, ABSENT)),
                Arguments.of("woord", "worre", List.of(CORRECT, CORRECT, ABSENT, CORRECT, ABSENT)),
                Arguments.of("woord", "wordo", List.of(CORRECT, CORRECT, PRESENT, PRESENT, PRESENT)),
                Arguments.of("woord", "waaod", List.of(CORRECT, ABSENT, ABSENT, PRESENT, CORRECT)),
                Arguments.of("woord", "%$#@!", List.of(INVALID, INVALID, INVALID, INVALID, INVALID)),
                Arguments.of("paard", "attaa", List.of(PRESENT, ABSENT, ABSENT, PRESENT, ABSENT))

        );
    }

    @ParameterizedTest
    @MethodSource("provideFeedbackExamples")
    @DisplayName("guessing a right word in a round")
    void testFeedback(String wordToGuess, String attempt, List<Mark> marks) {
        assertEquals(Feedback.basedOn(wordToGuess, attempt), new Feedback(attempt, marks));
    }


    @Test
    @DisplayName("checking the length of the attempt's length")
    void attemptsLength() {
        String attempt = "klaar";
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT);
        Feedback feedback = new Feedback(attempt, marks);
        assertEquals(feedback.getAttempt().length(),5);

    }

    @Test
    @DisplayName("Testing the id of feedback ")
    void feedbackId() {
        String attempt = "klaar";
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT);
        Feedback feedback = new Feedback(attempt, marks);
        feedback.setId(1L);
        assertEquals(feedback.getId(),1);

    }

    @Test
    @DisplayName("testing whether the hashcode the same is ")
    void feedbackHashcode() {
        String attempt = "klaar";
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT);
        Feedback feedback = new Feedback(attempt, marks);
        Feedback feedback1 = new Feedback(attempt,marks);
        assertEquals(feedback.hashCode(), feedback1.hashCode());

    }

    @Test
    @DisplayName("seeting the marks and attempt in the empty constructor ")
    void addingAttemptAndMarksToAnEmptyConstructor() {
        String attempt = "klaar";
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT);
        Feedback feedback = new Feedback();
        feedback.setAttempt(attempt);
        feedback.setMarks(marks);
        assertEquals(feedback.getAttempt(),attempt);
        assertEquals(feedback.getMarks(),marks);

    }


    @Test
    @DisplayName("setting the round in feedback ")
    void roundInfeedbackCheck() {
        String attempt = "klaar";
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT);
        Feedback feedback = new Feedback(attempt, marks);
        Round round = new Round();
        feedback.setRound(round);
        assertEquals(feedback.getRound(),round);

    }

}
