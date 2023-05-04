package com.example.fitness.Repository;


import com.example.fitness.Entity.Haircut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface HaircutRepository extends JpaRepository<Haircut, String> {


    @Transactional
    @Query("SELECT u FROM Haircut u WHERE u.email = :UserEmail")
    public Iterable<Haircut> getAllByUserEmail(@Param("UserEmail") String UserEmail);

    @Modifying
    @Transactional
    @Query("delete from Haircut where email = :UserEmail")
    void deleteUsersByUserEmail(@Param("UserEmail") String UserEmail);
}
