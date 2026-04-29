package io.github.SavioRomario10.LibraryApi.repository;

import java.util.UUID;
import io.github.SavioRomario10.LibraryApi.model.Autor;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, UUID>{
}