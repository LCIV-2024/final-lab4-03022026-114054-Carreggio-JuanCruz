package com.example.demobase.repository;

import com.example.demobase.model.Game;
import com.example.demobase.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    
    List<Game> findByJugador(Player jugador);
    
    @Query("SELECT g FROM Game g WHERE g.jugador.id = :playerId ORDER BY g.fechaPartida DESC")
    List<Game> findByJugadorId(@Param("playerId") Long playerId);
}

