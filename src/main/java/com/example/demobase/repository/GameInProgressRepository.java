package com.example.demobase.repository;

import com.example.demobase.model.GameInProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameInProgressRepository extends JpaRepository<GameInProgress, Long> {
    
    @Query("SELECT g FROM GameInProgress g WHERE g.jugador.id = :playerId AND g.palabra.id = :wordId")
    Optional<GameInProgress> findByJugadorAndPalabra(@Param("playerId") Long playerId, @Param("wordId") Long wordId);
    
    @Query("SELECT g FROM GameInProgress g WHERE g.jugador.id = :playerId ORDER BY g.fechaInicio DESC")
    java.util.List<GameInProgress> findByJugadorIdOrderByFechaInicioDesc(@Param("playerId") Long playerId);
}

