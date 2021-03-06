package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.TrainerRepository;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class TrainerGameService {

    private final WordService wordService;
    private final TrainerRepository gameRepository;

    public TrainerGameService(WordService wordService, TrainerRepository gameRepository) {
        this.wordService = wordService;
        this.gameRepository = gameRepository;
    }

    public LingoGame startGame() {
        LingoGame game = new LingoGame();
        this.gameRepository.save(game);
        return game;

    }

    public LingoGame newRound(Long gameId) {
        LingoGame game = getGame(gameId);
        String wordToGuess = wordService.provideRandomWord(game.provideNextWordLength());
        game.startNewRound(wordToGuess);
        gameRepository.save(game);
        return game;
    }

    public LingoGame makeGuess(Long id, String attempt) {

        LingoGame game = getGame(id);
        game.guess(attempt);
        gameRepository.save(game);
        return game;
    }

    public LingoGame getGame(long gameId) {
        return gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
    }


}
