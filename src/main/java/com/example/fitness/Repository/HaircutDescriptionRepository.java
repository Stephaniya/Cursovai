package com.example.fitness.Repository;


import com.example.fitness.Entity.HaircutDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HaircutDescriptionRepository extends JpaRepository<HaircutDescription, String> {
    @Query("SELECT u FROM HaircutDescription u WHERE u.name = :name")
    public HaircutDescription getFitnessDescriptionByName(@Param("name") String name);


}
