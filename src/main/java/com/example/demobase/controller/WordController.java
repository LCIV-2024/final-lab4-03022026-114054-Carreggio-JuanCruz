package com.example.demobase.controller;

import com.example.demobase.dto.WordDTO;
import com.example.demobase.service.WordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/words")
@RequiredArgsConstructor
@Tag(name = "Palabras", description = "API para consultar palabras del juego")
public class WordController {
    
    private final WordService wordService;
    
    @GetMapping
    @Operation(summary = "Obtener lista de todas las palabras con su estado de uso")
    public ResponseEntity<List<WordDTO>> getAllWords() {
        return ResponseEntity.ok(wordService.getAllWords());
    }
}

