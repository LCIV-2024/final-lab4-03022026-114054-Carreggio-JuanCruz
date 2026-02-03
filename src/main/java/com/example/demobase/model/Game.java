package com.example.demobase.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "games")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jugador", nullable = false)
    private Player jugador;
    
    @Column(nullable = false)
    private String resultado; // "GANADO" o "PERDIDO"
    
    @Column(nullable = false)
    private Integer puntaje;
    
    @Column(nullable = false)
    private LocalDateTime fechaPartida;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_palabra")
    private Word palabra;
}

