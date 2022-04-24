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

//	— pouvoir consulter la base de données au sujet des communes, de leurs monuments ou de leurs célébrités (prévoyez un jeu d’au moins 10 requètes à cet effet)
//	— pouvoir mettre à jour la base de données au travers d’opérations de:
//																		modification,
//																		suppression et
//																		insertion (prévoyez au moins un exemple de chaque)

//	— pouvoir lancer des traitements sur les données : par exemple
//															pouvoir connaître la distance entre deux monuments cibles dans la même commune ou
//															dans deux communes différentes
//
//	— pouvoir se connecter au système au travers de différents rôles et donc de différents droits en matière d’accès en lecture/écriture sur les données. Vous pouvez par exemple
//		définir les rôles
//						”Administrateur”,
//						”Voyagiste”,
//						”Touriste” qui donneront lieu à des vues externes différentes du système.

//	— ...
//	L’originalité sera une plus-value à votre travail et les aspects relatifs à l'orgonomie et à la restitution visuelle seront également considérés.


    //Autowired


    //	Departement methodes
    public Departement          addDepartement(Departement departement);
    public Departement          getDepartement(String numDep);
    public List<Departement>    getByNameDepartementContaining(String nom);
    public List<Departement>    findAll();
    public Page<Departement>    getAllDepartements(int pageNumber, int items, Sort sort);
    public int	                deleteDepartementById(String numDep);



    //	Lieu methodes
    public Lieu                 addLieu(Lieu lieu);
    public Lieu                 getLieu(String codeIsee);
    public Optional<Lieu> 		getLieu(Optional<String> codeIsee);
    public List<Lieu>           getByNameLieuContaining(String nom);
    public Page<Lieu>           getAllLieux(int pageNumber, int items, Sort sort);
    public int                  deleteLieuById(String id);



    //	Monument methodes
    public Monument             addMonument(Monument monument);
    public void                 addMonumentToLieu(String codeM, String codeInsee);
    public Monument             getMonument(String codeM);
    public List<Monument>       getListMonumentsByDep(String nomDep);
    public List<Monument>       getListMonumentsByLieu(String nomCom);
    public List<Monument>       getByNameMonumentContaining(String nom);
    public float                getDistanceBetweenMonuments(String nomMonA,String nomMonB);
    public Page<Monument>       getAllMonuments(int pageNumber, int items, Sort sort);
    public int                  deleteMonumentById(String codeM);





    //	Celebrite methodes
    public Celebrite            addCelebrite(Celebrite celebrite);
    public Celebrite            getCelebriteById(long numCelebrite);
    public List<Celebrite>      getCelebriteByName(String prenom);
    public List<Celebrite>      getCelebriteByFamily(String nom);
    public List<Celebrite>      getByNameCelebriteContaining(String nom);
    public Page<Celebrite>      getAllCelebrities(int pageNumber, int items, Sort sort);
    public int              	deleteCelebriteById(long numCelebrite);
    public int 					updateCelebrite(long numCelebrite, String nom, String prenom, String nationalite, String epoque);
//  public void 				updateCelebriteObject(Celebrite celebrite);


    //	User methodes
    public User 				getUser(String user);
    public List<User>    		getAllUsers();
    public User 				addUser(User user);
    public int 					deleteUser(String email);





}
