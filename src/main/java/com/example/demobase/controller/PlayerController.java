package com.example.demobase.controller;

import com.example.demobase.dto.PlayerDTO;
import com.example.demobase.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
@Tag(name = "Jugadores", description = "API para gestión de jugadores")
public class PlayerController {
    
    private final PlayerService playerService;
    
    @GetMapping
    @Operation(summary = "Obtener todos los jugadores")
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        return ResponseEntity.ok(playerService.getAllPlayers());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener jugador por ID")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.getPlayerById(id));
    }
    
    @PostMapping
    @Operation(summary = "Crear nuevo jugador")
    public ResponseEntity<PlayerDTO> createPlayer(@RequestBody PlayerDTO playerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(playerService.createPlayer(playerDTO));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar jugador")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable Long id, @RequestBody PlayerDTO playerDTO) {
        return ResponseEntity.ok(playerService.updatePlayer(id, playerDTO));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar jugador")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}

