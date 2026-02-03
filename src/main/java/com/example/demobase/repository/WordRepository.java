package com.example.demobase.repository;

import com.example.demobase.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    
    Optional<Word> findByPalabra(String palabra);
    
    @Query(value = "SELECT * FROM words WHERE utilizada = false ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Word> findRandomWord();
    
    @Query("SELECT w FROM Word w ORDER BY w.id")
    java.util.List<Word> findAllOrdered();
}

