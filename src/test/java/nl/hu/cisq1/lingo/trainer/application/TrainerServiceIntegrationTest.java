package nl.hu.cisq1.lingo.trainer.application;


import nl.hu.cisq1.lingo.trainer.data.TrainerRepository;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;
import nl.hu.cisq1.lingo.trainer.domain.State;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundFinishedException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TrainerServiceIntegrationTest {

    @Autowired
    TrainerGameService gameService;
    @Autowired
    TrainerRepository repository;

    private WordService wordService;

//    @Test
//    @DisplayName("new Round is saved")
//    void newRoundIsSavedTest() {
//        LingoGame game = gameService.startGame();
//        gameService.newRound(game.getId());
//        game = gameService.getGame(game.getId());
//        assertEquals(1, game.getRounds().size());
//    }

    @BeforeEach
    void before() {
        // Test game

        Optional<LingoGame> gameEntityOptional = Optional.of(new LingoGame());

        // Mocks
        wordService = mock(WordService.class);
        when(wordService.provideRandomWord(5)).thenReturn("apple");
        when(wordService.provideRandomWord(6)).thenReturn("apples");
        when(wordService.provideRandomWord(7)).thenReturn("appless");
        repository = mock(TrainerRepository.class);
        when(repository.findById(0L)).thenReturn(gameEntityOptional);

        // Trainer
        gameService = new TrainerGameService(wordService, repository);
        gameService.startGame();
    }

    @Test
    @DisplayName("throws exception if game is not found")
    void gameNotFoundException() {
        assertThrows(GameNotFoundException.class, () -> gameService.newRound(1L));
        assertThrows(RuntimeException.class, () -> gameService.makeGuess(1L, ""));

    }
//
//    @Test
//    @DisplayName("throws exception if trying to guess while game is lost")
//    void lostGameException() {
//        // Lose on purpose
//        gameService.newRound(0L);
//        gameService.makeGuess(0L, "pizza");
//        gameService.makeGuess(0L, "pizza");
//        gameService.makeGuess(0L, "pizza");
//        gameService.makeGuess(0L, "pizza");
//        gameService.makeGuess(0L, "pizza");
//
//        // Must throw exception
//        assertThrows(RuntimeException.class, () -> gameService.makeGuess(0L, "pizza"));
//    }

//    @Test
//    @DisplayName("guessTest correct")
//    void guessTest() {
//        WordService wordService= Mockito.mock(WordService.class);
//        Mockito.when(wordService.provideRandomWord(Mockito.anyInt())).thenReturn("klaar");
//        gameService=new TrainerGameService(wordService, repository);
//        LingoGame gameId=gameService.startGame();
//        gameService.newRound(gameId.getId());
//        gameService.makeGuess(gameId.getId(),"klaar");
//        assertTrue(gameService.getGame(gameId.getId()).getLastRound().isRoundFinished());
//    }
}




