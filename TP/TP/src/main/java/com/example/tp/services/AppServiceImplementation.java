package com.example.tp.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


import com.example.tp.dao.LieuReservoir;
import com.example.tp.dao.MonumentReservoir;
import com.example.tp.dao.UserReservoir;
import com.example.tp.dao.CelebritReservoir;
import com.example.tp.dao.CrudCelebriteReser;
import com.example.tp.dao.DepartementReservoir;

import com.example.tp.entities.Lieu;
import com.example.tp.entities.Monument;
import com.example.tp.entities.User;
import com.example.tp.exceptions.EntitiesNotFoundExcep;
import com.example.tp.entities.Celebrite;
import com.example.tp.entities.Departement;

@Transactional
@Service
public class AppServiceImplementation implements AppService {


    @Autowired
    private LieuReservoir lieuReservoir;

    @Autowired
    private MonumentReservoir monumentReservoir;

    @Autowired
    private CelebritReservoir celebritReservoir;

    @Autowired
    private CrudCelebriteReser crudCelebriteRepo;

    @Autowired
    private DepartementReservoir departementReservoir;

    @Autowired
    private UserReservoir userReservoir;


//	---------------------------------- Department ----------------------------------


    @Override
    public Departement getDepartement(String numDep) {

        return departementReservoir.findById(numDep).orElseThrow(() -> new EntitiesNotFoundExcep(numDep, "Can't find entered Departement with Department Number: "));
    }


    @Override
    public Page<Departement> getAllDepartements(int pageNumber, int parts, Sort sort) {

        Pageable pageable = PageRequest.of(pageNumber - 1, parts, sort);
        return departementReservoir.findAll(pageable);
    }


    @Override
    public Departement addDepartement(Departement departement) {
        return departementReservoir.save(departement);
    }


    @Override
    public List<Departement> findAll() {
        return departementReservoir.findAll();
    }


    @Override
    public List<Departement> getNameDepartementContaining(String nom) {
        return departementReservoir.getNameDepartementContaining(nom);    // .orElseThrow(() -> new EntitiesNotFoundException(nom, "Can't find entered Departement with Department Number: "));
    }

    @Override
    public int deleteDepartementId(String numDep) {
        return departementReservoir.deleteDepartementId(numDep);
    }


//	---------------------------------- Lieu ----------------------------------


    @Override
    public Lieu getLieu(String codeInsee) {
        return lieuReservoir.findById(codeInsee).orElseThrow(() -> new EntitiesNotFoundExcep(codeInsee, "Can't find entered Lieu with Code Insee: "));
    }

    @Override
    public Optional<Lieu> getLieu(Optional<String> codeIsee) {
        if (codeIsee.isEmpty()) throw new RuntimeException("Can't find entered Lieu");
        else return lieuReservoir.findById(codeIsee.get());
    }


    @Override
    public Page<Lieu> getAllLieux(int pageNumber, int parts, Sort sort) {
        Pageable pageable = PageRequest.of(pageNumber - 1, parts, sort);
        return lieuReservoir.findAll(pageable);
    }


    @Override
    public Lieu addLieu(Lieu lieu) {
        return lieuReservoir.save(lieu);
    }

    @Override
    public List<Lieu> getNameLieuContaining(String nom) {
        return lieuReservoir.getNameLieuContaining(nom);
    }

    @Override
    public int deleteLieuId(String codeIsee) {
        return lieuReservoir.deleteLieuId(codeIsee);
    }


//	---------------------------------- Monument ----------------------------------


    @Override
    public Monument getMonument(String codeM) {
        return monumentReservoir.findById(codeM).orElseThrow(() -> new EntitiesNotFoundExcep(codeM, "Can't find entered Monument with Code Monument: "));
    }

    @Override
    public Page<Monument> getAllMonuments(int pageNumber, int parts, Sort sort) {
        Pageable pageable = PageRequest.of(pageNumber - 1, parts, sort);
        return monumentReservoir.findAll(pageable);
    }


    @Override
    public Monument addMonument(Monument monument) {
        return monumentReservoir.save(monument);
    }

    @Override
    public void addMonumentToLieu(String codeM, String codeInsee) {


    }

    @Override
    public float getDistanceBetweenMonuments(String nomMonA, String nomMonB) {


        return 0;
    }


    @Override
    public List<Monument> getNameMonumentContaining(String nom) {
        return monumentReservoir.getNameMonumentContaining(nom);
    }


    @Override
    public int deleteMonumentId(String codeM) {
        return monumentReservoir.deleteMonumentId(codeM);
    }


    @Override
    public List<Monument> getListMonumentsDep(String nomDep) {
        return monumentReservoir.getListMonumentsDep(nomDep);
    }

    @Override
    public List<Monument> getListMonumentsLieu(String nomCom) {
        return monumentReservoir.getListMonumentsLieu(nomCom);
    }


//	---------------------------------- Celebrite ----------------------------------


    @Override
    public List<Celebrite> getCelebriteName(String prenom) {
        return celebritReservoir.getCelebriteName(prenom);
    }

    @Override
    public List<Celebrite> getCelebriteFamily(String nom) {
        return celebritReservoir.getCelebriteFamily(nom);
    }

    @Override
    public Page<Celebrite> getAllCelebrities(int pageNumber, int parts, Sort sort) {
        Pageable pageable = PageRequest.of(pageNumber - 1, parts, sort);
        return celebritReservoir.findAll(pageable);
    }


    @Override
    public Celebrite addCelebrite(Celebrite celebrite) {
        return crudCelebriteRepo.save(celebrite);
    }

    @Override
    public Celebrite getCelebriteId(long numCelebrite) {

        return celebritReservoir.findById(numCelebrite).orElseThrow(() -> new EntitiesNotFoundExcep(numCelebrite, "Can't find entered Celebrity with Celebrity Number: "));
    }

    @Override
    public List<Celebrite> getNameCelebriteContaining(String nom) {
        return celebritReservoir.getNameContaining(nom);
    }


    @Override
    public int deleteCelebriteId(long numCelebrite) {
        return celebritReservoir.deleteCelebriteId(numCelebrite);
    }

    @Override
    public int updateCelebrite(long numCelebrite, String nom, String prenom, String nationalite, String epoque) {
        return celebritReservoir.updateCelebrite(nom, prenom, nationalite, epoque, numCelebrite);
    }

//	---------------------------------- User ----------------------------------


    @Override
    public List<User> getAllUsers() {
        return userReservoir.findAll();
    }

    @Override
    public User getUser(String email) {
        return userReservoir.findById(email).orElseThrow(() -> new EntitiesNotFoundExcep(email, "Can't find entered User with email: "));

    }

    @Override
    public User addUser(User user) {
        return userReservoir.save(user);

    }

    @Override
    public int deleteUser(String user) {
        return userReservoir.deleteUser(user);
    }


}


