package com.example.tp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import com.example.tp.entities.Lieu;
import com.example.tp.entities.Monument;
import com.example.tp.entities.User;
import com.example.tp.entities.Celebrite;
import com.example.tp.entities.Departement;


public interface AppService {

    //	Departement methodes
    public Departement          addDepartement(Departement departement);
    public Departement          getDepartement(String numDep);
    public List<Departement>    getNameDepartementContaining(String nom);
    public List<Departement>    findAll();
    public Page<Departement>    getAllDepartements(int pageNumber, int items, Sort sort);
    public int	                deleteDepartementId(String numDep);



    //	Lieu methodes
    public Lieu                 addLieu(Lieu lieu);
    public Lieu                 getLieu(String codeIsee);
    public Optional<Lieu> 		getLieu(Optional<String> codeIsee);
    public List<Lieu>           getNameLieuContaining(String nom);
    public Page<Lieu>           getAllLieux(int pageNumber, int items, Sort sort);
    public int                  deleteLieuId(String id);



    //	Monument methodes
    public Monument             addMonument(Monument monument);
    public void                 addMonumentToLieu(String codeM, String codeInsee);
    public Monument             getMonument(String codeM);
    public List<Monument>       getListMonumentsDep(String nomDep);
    public List<Monument>       getListMonumentsLieu(String nomCom);
    public List<Monument>       getNameMonumentContaining(String nom);
    public float                getDistanceBetweenMonuments(String nomMonA,String nomMonB);
    public Page<Monument>       getAllMonuments(int pageNumber, int items, Sort sort);
    public int                  deleteMonumentId(String codeM);





    //	Celebrite methodes
    public Celebrite            addCelebrite(Celebrite celebrite);
    public Celebrite            getCelebriteId(long numCelebrite);
    public List<Celebrite>      getCelebriteName(String prenom);
    public List<Celebrite>      getCelebriteFamily(String nom);
    public List<Celebrite>      getNameCelebriteContaining(String nom);
    public Page<Celebrite>      getAllCelebrities(int pageNumber, int items, Sort sort);
    public int              	deleteCelebriteId(long numCelebrite);
    public int 					updateCelebrite(long numCelebrite, String nom, String prenom, String nationalite, String epoque);
//  public void 				updateCelebriteObject(Celebrite celebrite);


    //	User methodes
    public User 				getUser(String user);
    public List<User>    		getAllUsers();
    public User 				addUser(User user);
    public int 					deleteUser(String email);





}
