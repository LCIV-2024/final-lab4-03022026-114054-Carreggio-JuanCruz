package com.example.demobase.service;

import com.example.demobase.dto.ScoreboardDTO;
import com.example.demobase.model.Game;
import com.example.demobase.model.Player;
import com.example.demobase.repository.GameRepository;
import com.example.demobase.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScoreboardService {
    
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    
    public List<ScoreboardDTO> getScoreboard() {
        return playerRepository.findAll().stream()
                .map(this::calculatePlayerStats)
                .sorted((a, b) -> b.getPuntajeTotal().compareTo(a.getPuntajeTotal()))
                .collect(Collectors.toList());
    }
    
    public ScoreboardDTO getScoreboardByPlayer(Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado con id: " + playerId));
        return calculatePlayerStats(player);
    }
    
    private ScoreboardDTO calculatePlayerStats(Player player) {
        List<Game> games = gameRepository.findByJugador(player);
        
        int puntajeTotal = games.stream()
                .mapToInt(Game::getPuntaje)
                .sum();
        
        long partidasGanadas = games.stream()
                .filter(g -> "GANADO".equals(g.getResultado()))
                .count();
        
        long partidasPerdidas = games.stream()
                .filter(g -> "PERDIDO".equals(g.getResultado()))
                .count();
        
        return new ScoreboardDTO(
                player.getId(),
                player.getNombre(),
                puntajeTotal,
                (long) games.size(),
                partidasGanadas,
                partidasPerdidas
        );
    }
}

