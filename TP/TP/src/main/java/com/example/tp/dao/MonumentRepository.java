package com.example.tp.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.tp.entities.Departement;
import com.example.tp.entities.Monument;

@Repository
@Transactional(readOnly = true)
public interface MonumentRepository extends JpaRepository<Monument, String> {


    @Query("select m from Monument m order by m.nomM desc")
    public Page<Monument> getAllMonuments(Pageable pageable);

    @Query("select m from Monument m order by m.nomM desc")
    public Slice<Monument> getAllMonumentsSlice(Pageable pageable);

    @Query("select m from Monument m where  m.nomM like CONCAT('%',:x,'%')")
    public List<Monument> getByNameMonumentContaining(@Param("x") String nom);

    @Modifying
    @Query("delete from Monument m where  m.codeM = ?1")
    public int deleteMonumentById(String codeM);


    @Query("select m from Monument m where m.lieu.nomCom =:x")
    public List<Monument> getListMonumentsByLieu(@Param("x")String nomCom);

    @Query("SELECT m FROM Monument m WHERE " +
            "EXISTS (SELECT 1 FROM Departement d WHERE m.lieu.codeInsee = d.lieu.codeInsee AND d.nomDep = :x)")
    public List<Monument> getListMonumentsByDep(@Param("x")String nomDep);







}