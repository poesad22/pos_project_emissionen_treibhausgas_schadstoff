package org.example.emissionen.repository;

import org.example.emissionen.pojo.Atom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtomRepo extends JpaRepository<Atom, String> {
}
