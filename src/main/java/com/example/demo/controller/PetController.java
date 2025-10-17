package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.model.Pet;
import com.example.demo.repository.PetRepository;

@RestController
@RequestMapping("/pets")
@CrossOrigin(origins = "*") // permite acesso do front
public class PetController {

    private final PetRepository petRepository;

    public PetController(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @GetMapping
    public List<Pet> listar() {
        return petRepository.findAll();
    }

    @PostMapping
    public Pet adicionar(@RequestBody Pet pet) {
        return petRepository.save(pet);
    }

    @PutMapping("/{id}")
    public Pet atualizar(@PathVariable Long id, @RequestBody Pet petAtualizado) {
        return petRepository.findById(id).map(pet -> {
            pet.setNome(petAtualizado.getNome());
            pet.setEspecie(petAtualizado.getEspecie());
            pet.setIdade(petAtualizado.getIdade());
            pet.setDescricao(petAtualizado.getDescricao());
            return petRepository.save(pet);
        }).orElseThrow(() -> new RuntimeException("Pet não encontrado"));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        petRepository.deleteById(id);
    }
}
