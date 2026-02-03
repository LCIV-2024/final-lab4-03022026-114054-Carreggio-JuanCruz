package com.example.demobase.controller;

import com.example.demobase.dto.GameDTO;
import com.example.demobase.dto.GameResponseDTO;
import com.example.demobase.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
@Tag(name = "Partidas", description = "API para gestión de partidas del juego Hangman")
public class GameController {
    
    private final GameService gameService;
    
    @PostMapping("/start/{playerId}")
    @Operation(summary = "Iniciar nueva partida")
    public ResponseEntity<GameResponseDTO> startGame(@PathVariable Long playerId) {
        return ResponseEntity.ok(gameService.startGame(playerId));
    }
    
    @PostMapping("/guess")
    @Operation(summary = "Realizar un intento de adivinar letra")
    public ResponseEntity<GameResponseDTO> makeGuess(@RequestBody Map<String, Object> request) {
        Long playerId = Long.valueOf(request.get("idJugador").toString());
        Character letra = request.get("letra").toString().charAt(0);
        
        GameResponseDTO result = gameService.makeGuess(playerId, letra);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping
    @Operation(summary = "Obtener todas las partidas")
    public ResponseEntity<List<GameDTO>> getAllGames() {
        return ResponseEntity.ok(gameService.getAllGames());
    }
    
    @GetMapping("/player/{playerId}")
    @Operation(summary = "Obtener partidas de un jugador")
    public ResponseEntity<List<GameDTO>> getGamesByPlayer(@PathVariable Long playerId) {
        return ResponseEntity.ok(gameService.getGamesByPlayer(playerId));
    }
}

