package nl.hu.cisq1.lingo.trainer.application;


import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.data.TrainerRepository;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;
import nl.hu.cisq1.lingo.trainer.domain.enums.State;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Import(CiTestConfiguration.class)
public class TrainerServiceIntegrationTest {

    @Autowired
    TrainerGameService gameService;
    @Autowired
    TrainerRepository repository;
    @Autowired
    WordService wordService;


    @Test
    @DisplayName("new Round is saved")
    void newRoundIsSavedTest() {
        LingoGame game = gameService.startGame();
        gameService.newRound(game.getId());
        game = gameService.getGame(game.getId());
        assertEquals(1, game.getRounds().size());
    }


    @Test
    @DisplayName("starting a game with a round and returning some attributes")
    void startNewGameAndRound() {
        wordService = mock(WordService.class);
        when(wordService.provideRandomWord(5)).thenReturn("staar");
        gameService = new TrainerGameService(wordService, repository);
        LingoGame game = gameService.startGame();
        gameService.newRound(game.getId());
        assertEquals(gameService.getGame(game.getId()).getScore(), 0);
        assertEquals(gameService.getGame(game.getId()).getGameState(), State.PLAYING);
        assertEquals(gameService.getGame(game.getId()).getLastRound().getLastHint(), "s....");
        assertEquals(gameService.getGame(game.getId()).getLastRound().getFeedbacks().size(), 0);


    }

    @Test
    @DisplayName("The program throws a game not found exception when starting an not existing game")
    void gameNotFound() {

        LingoGame gameEntityOptional = new LingoGame();

        wordService = mock(WordService.class);
        when(wordService.provideRandomWord(5)).thenReturn("apple");
        when(wordService.provideRandomWord(6)).thenReturn("apples");
        when(wordService.provideRandomWord(7)).thenReturn("appless");
        repository = mock(TrainerRepository.class);
        when(repository.findById(0L)).thenReturn(Optional.of(gameEntityOptional));


        gameService = new TrainerGameService(wordService, repository);
        gameService.startGame();
        assertThrows(GameNotFoundException.class, () -> gameService.newRound(1L));

    }


    @Test
    @DisplayName("guessTest correct")
    void guessTest() {
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(Mockito.anyInt())).thenReturn("klaar");
        gameService = new TrainerGameService(wordService, repository);
        LingoGame gameId = gameService.startGame();
        gameService.newRound(gameId.getId());
        gameService.makeGuess(gameId.getId(), "klaar");
        assertTrue(gameService.getGame(gameId.getId()).getLastRound().isRoundFinished());
    }


    @Test
    @DisplayName("An wrong attempt has been made wat cause that round is not finished")
    void RoundNotFinishedWithWrongAttempt() {
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(Mockito.anyInt())).thenReturn("klaar");
        gameService = new TrainerGameService(wordService, repository);
        LingoGame gameId = gameService.startGame();
        gameService.newRound(gameId.getId());
        gameService.makeGuess(gameId.getId(), "klars");
        assertFalse(gameService.getGame(gameId.getId()).getLastRound().isRoundFinished());
    }
}




