package com.example.demobase.service;

import com.example.demobase.dto.PlayerDTO;
import com.example.demobase.model.Player;
import com.example.demobase.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {
    
    private final PlayerRepository playerRepository;
    
    public List<PlayerDTO> getAllPlayers() {
        return playerRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public PlayerDTO getPlayerById(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado con id: " + id));
        return toDTO(player);
    }
    
    @Transactional
    public PlayerDTO createPlayer(PlayerDTO playerDTO) {
        if (playerDTO.getFecha() == null) {
            playerDTO.setFecha(LocalDate.now());
        }
        Player player = toEntity(playerDTO);
        Player saved = playerRepository.save(player);
        return toDTO(saved);
    }
    
    @Transactional
    public PlayerDTO updatePlayer(Long id, PlayerDTO playerDTO) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado con id: " + id));
        
        player.setNombre(playerDTO.getNombre());
        if (playerDTO.getFecha() != null) {
            player.setFecha(playerDTO.getFecha());
        }
        
        Player updated = playerRepository.save(player);
        return toDTO(updated);
    }
    
    @Transactional
    public void deletePlayer(Long id) {
        if (!playerRepository.existsById(id)) {
            throw new RuntimeException("Jugador no encontrado con id: " + id);
        }
        playerRepository.deleteById(id);
    }
    
    private PlayerDTO toDTO(Player player) {
        return new PlayerDTO(player.getId(), player.getNombre(), player.getFecha());
    }
    
    private Player toEntity(PlayerDTO dto) {
        return new Player(dto.getId(), dto.getNombre(), dto.getFecha());
    }
}

