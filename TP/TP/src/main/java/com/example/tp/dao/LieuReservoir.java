package com.example.tp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.tp.entities.Departement;
import com.example.tp.entities.Lieu;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface LieuReservoir extends JpaRepository<Lieu, String> {


    @Query("select l from Lieu l where l.nomCom=:x")
    public List<Lieu> findLieunomCom(@Param ("x")String nomCom);

    //@Query("Select l from lieu l where l.nomCom like %:nomCom%")
    public List<Lieu> findNomComContaining(@Param("%:nomCom%:") String nomCom);

    @Query("select l from Lieu l where l.codeInsee=:x")
    public Lieu getLieu(@Param("x")String codeInsee);

    @Query("select l from Lieu l where  l.nomCom like CONCAT('%',:x,'%')")
    public List<Lieu> getNameLieuContaining(@Param("x") String nom);

    @Modifying
    @Query("delete from Lieu l where  l.codeInsee = ?1")
    public int deleteLieuId(String codeIsee);




}
