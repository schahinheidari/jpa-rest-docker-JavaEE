package com.example.tp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.tp.entities.Celebrite;
import com.example.tp.entities.Departement;

@Repository
@Transactional(readOnly = true)
public interface DepartementRepository extends JpaRepository<Departement, String> {

//	@Query("select d from Departement d where d.numDep=:x")
//	Departement findOne(@Param("x") String numDep);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Departement d SET d.numDep =:x , d.nomDep =:y WHERE d.lieu.codeInsee =:z")
    int updateDepartement(@Param("x") String numDep, @Param("y") String nomDep, @Param("z") String codeInsee);

    @Query("select d from Departement d where  d.nomDep like CONCAT('%',:x,'%')")
    public List<Departement> getByNameDepartementContaining(@Param("x") String nom);

    @Modifying
    @Query("delete from Departement d where  d.numDep = ?1")
    public int deleteDepartementById(String numDep);





}