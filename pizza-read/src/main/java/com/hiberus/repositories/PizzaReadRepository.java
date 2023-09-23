package com.hiberus.repositories;


import com.hiberus.models.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaReadRepository extends JpaRepository<Pizza, Long> {
}
