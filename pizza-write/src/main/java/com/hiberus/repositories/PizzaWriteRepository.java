package com.hiberus.repositories;

import com.hiberus.models.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PizzaWriteRepository extends JpaRepository<Pizza, Long> {

    Boolean existsByName(String name);

}
