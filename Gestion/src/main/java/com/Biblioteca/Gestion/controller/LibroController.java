package com.Biblioteca.Gestion.controller;

import com.Biblioteca.Gestion.model.Libro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/libros")
@CrossOrigin(origins = "*")
public class LibroController {

    private List<Libro> libros = new ArrayList<>();

    public LibroController() {
        libros.add(new Libro(1L, "978-3", "Cien años de soledad", "García Márquez", 1967, true));
    }

    @GetMapping
    public ResponseEntity<List<Libro>> obtenerTodos() {
        return ResponseEntity.ok(libros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerPorId(@PathVariable Long id) {
        return libros.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Libro>> buscar(@RequestParam String autor,
                                              @RequestParam(required = false) Integer anio) {
        List<Libro> resultado = libros.stream()
                .filter(l -> l.getAutor().equalsIgnoreCase(autor))
                .filter(l -> anio == null || l.getAnioPublicacion() == anio)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultado);
    }

    @PostMapping
    public ResponseEntity<Libro> crear(@RequestBody Libro libro) {
        libros.add(libro);
        return new ResponseEntity<>(libro, HttpStatus.CREATED);
    }
}