package com.example.demobase.controller;

import com.example.demobase.dto.ScoreboardDTO;
import com.example.demobase.service.ScoreboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scoreboard")
@RequiredArgsConstructor
@Tag(name = "Puntajes", description = "API para consultar puntajes y estadísticas")
public class ScoreboardController {
    
    private final ScoreboardService scoreboardService;
    
    @GetMapping
    @Operation(summary = "Obtener grilla de puntajes de todos los jugadores")
    public ResponseEntity<List<ScoreboardDTO>> getScoreboard() {
        return ResponseEntity.ok(scoreboardService.getScoreboard());
    }
    
    @GetMapping("/player/{playerId}")
    @Operation(summary = "Obtener puntajes de un jugador específico")
    public ResponseEntity<ScoreboardDTO> getScoreboardByPlayer(@PathVariable Long playerId) {
        return ResponseEntity.ok(scoreboardService.getScoreboardByPlayer(playerId));
    }
}

