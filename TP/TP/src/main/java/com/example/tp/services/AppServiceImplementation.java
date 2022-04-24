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


import com.example.tp.dao.LieuRepository;
import com.example.tp.dao.MonumentRepository;
import com.example.tp.dao.UserRepository;
import com.example.tp.dao.CelebriteRepository;
import com.example.tp.dao.CrudCelebriteRepo;
import com.example.tp.dao.DepartementRepository;

import com.example.tp.entities.Lieu;
import com.example.tp.entities.Monument;
import com.example.tp.entities.User;
import com.example.tp.exceptions.EntitiesNotFoundException;
import com.example.tp.entities.Celebrite;
import com.example.tp.entities.Departement;

@Transactional
@Service
public class AppServiceImplementation implements AppService {


    @Autowired
    private LieuRepository lieuRepository;

    @Autowired
    private MonumentRepository monumentRepository;

    @Autowired
    private CelebriteRepository celebriteRepository;

    @Autowired
    private CrudCelebriteRepo crudCelebriteRepo;

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private UserRepository userRepository;


//	---------------------------------- Department ----------------------------------


    @Override
    public Departement getDepartement(String numDep) {

        return departementRepository.findById(numDep).orElseThrow(() -> new EntitiesNotFoundException(numDep, "Can't find entered Departement with Department Number: "));
    }


    @Override
    public Page<Departement> getAllDepartements(int pageNumber, int items, Sort sort) {

        Pageable pageable = PageRequest.of(pageNumber - 1, items, sort);
        return departementRepository.findAll(pageable);
    }


    @Override
    public Departement addDepartement(Departement departement) {
        return departementRepository.save(departement);
    }


    @Override
    public List<Departement> findAll() {
        return departementRepository.findAll();
    }


    @Override
    public List<Departement> getByNameDepartementContaining(String nom) {
        return departementRepository.getByNameDepartementContaining(nom);    // .orElseThrow(() -> new EntitiesNotFoundException(nom, "Can't find entered Departement with Department Number: "));
    }

    @Override
    public int deleteDepartementById(String numDep) {
        return departementRepository.deleteDepartementById(numDep);
    }


//	---------------------------------- Lieu ----------------------------------


    @Override
    public Lieu getLieu(String codeInsee) {
        return lieuRepository.findById(codeInsee).orElseThrow(() -> new EntitiesNotFoundException(codeInsee, "Can't find entered Lieu with Code Insee: "));
    }

    @Override
    public Optional<Lieu> getLieu(Optional<String> codeIsee) {
        if (codeIsee.isEmpty()) throw new RuntimeException("Can't find entered Lieu");
        else return lieuRepository.findById(codeIsee.get());
    }


    @Override
    public Page<Lieu> getAllLieux(int pageNumber, int items, Sort sort) {
        Pageable pageable = PageRequest.of(pageNumber - 1, items, sort);
        return lieuRepository.findAll(pageable);
    }


    @Override
    public Lieu addLieu(Lieu lieu) {
        return lieuRepository.save(lieu);
    }


    @Override
    public List<Lieu> getByNameLieuContaining(String nom) {
        return lieuRepository.getByNameLieuContaining(nom);
    }

    @Override
    public int deleteLieuById(String codeIsee) {
        return lieuRepository.deleteLieuById(codeIsee);
    }


//	---------------------------------- Monument ----------------------------------


    @Override
    public Monument getMonument(String codeM) {
        return monumentRepository.findById(codeM).orElseThrow(() -> new EntitiesNotFoundException(codeM, "Can't find entered Monument with Code Monument: "));
    }

//	public List<Monument> getAllMonuments() {
//
//		return monumentRepository.findAll();
//	}


//	@Override
//	public Page<Monument> getAllMonuments(int pageNumber) {
//		Pageable pageable = PageRequest.of(pageNumber - 1, 17);
//		return monumentRepository.findAll(pageable);
//	}


    @Override
    public Page<Monument> getAllMonuments(int pageNumber, int items, Sort sort) {
        Pageable pageable = PageRequest.of(pageNumber - 1, items, sort);
        return monumentRepository.findAll(pageable);
    }


    @Override
    public Monument addMonument(Monument monument) {
        return monumentRepository.save(monument);
    }

    @Override
    public void addMonumentToLieu(String codeM, String codeInsee) {


//		Monument m = em.find(Monument.class, codeM);
//		Lieu l = em.find(Lieu.class, codeInsee);
//		l.getMonuments().add(m);


    }

    @Override
    public float getDistanceBetweenMonuments(String nomMonA, String nomMonB) {

        // /**
        //  * Calculate distance between two points in latitude and longitude taking
        //  * into account height difference. If you are not interested in height
        //  * difference pass 0.0. Uses Haversine method as its base.
        //  *
        //  * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
        //  * el2 End altitude in meters
        //  * @returns Distance in Meters
        //  */
        // public static double distance(double lat1, double lat2, double lon1,
        //         double lon2, double el1, double el2) {

        //     final int R = 6371; // Radius of the earth

        //     double latDistance = Math.toRadians(lat2 - lat1);
        //     double lonDistance = Math.toRadians(lon2 - lon1);
        //     double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
        //             + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
        //             * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        //     double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        //     double distance = R * c * 1000; // convert to meters

        //     double height = el1 - el2;

        //     distance = Math.pow(distance, 2) + Math.pow(height, 2);

        //     return Math.sqrt(distance);
        // }


        return 0;
    }


    @Override
    public List<Monument> getByNameMonumentContaining(String nom) {
        return monumentRepository.getByNameMonumentContaining(nom);
    }


    @Override
    public int deleteMonumentById(String codeM) {
        return monumentRepository.deleteMonumentById(codeM);
    }


    @Override
    public List<Monument> getListMonumentsByDep(String nomDep) {
        return monumentRepository.getListMonumentsByDep(nomDep);
    }

    @Override
    public List<Monument> getListMonumentsByLieu(String nomCom) {
        return monumentRepository.getListMonumentsByLieu(nomCom);
    }


//	---------------------------------- Celebrite ----------------------------------


    @Override
    public List<Celebrite> getCelebriteByName(String prenom) {
        return celebriteRepository.getCelebriteByName(prenom);
    }

    @Override
    public List<Celebrite> getCelebriteByFamily(String nom) {
        return celebriteRepository.getCelebriteByFamily(nom);
    }

    @Override
    public Page<Celebrite> getAllCelebrities(int pageNumber, int items, Sort sort) {
        Pageable pageable = PageRequest.of(pageNumber - 1, items, sort);
        return celebriteRepository.findAll(pageable);
    }


    @Override
    public Celebrite addCelebrite(Celebrite celebrite) {
        return crudCelebriteRepo.save(celebrite);
    }

    @Override
    public Celebrite getCelebriteById(long numCelebrite) {

        return celebriteRepository.findById(numCelebrite).orElseThrow(() -> new EntitiesNotFoundException(numCelebrite, "Can't find entered Celebrity with Celebrity Number: "));
    }

    @Override
    public List<Celebrite> getByNameCelebriteContaining(String nom) {
        return celebriteRepository.getByNameContaining(nom);
    }


    @Override
    public int deleteCelebriteById(long numCelebrite) {
        return celebriteRepository.deleteCelebriteById(numCelebrite);
    }

    @Override
    public int updateCelebrite(long numCelebrite, String nom, String prenom, String nationalite, String epoque) {
        return celebriteRepository.updateCelebrite(nom, prenom, nationalite, epoque, numCelebrite);
    }


//	public void updateCelebriteObject(Celebrite celebrite) {
//		Celebrite newCelebrite = crudCelebriteRepo.findById(celebrite.getNumCelebrite());
////	    modelMapper.map(celebrite, newCelebrite);
//	    celebriteRepository.save(newCelebrite);
//	}


//	---------------------------------- User ----------------------------------


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String email) {
        return userRepository.findById(email).orElseThrow(() -> new EntitiesNotFoundException(email, "Can't find entered User with email: "));

    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);

    }

    @Override
    public int deleteUser(String user) {
        return userRepository.deleteUser(user);
    }


}


//---------------------------------- Temp ----------------------------------


//example
//@RequestMapping(value = {"/article", "/article/{id}"}")
//public Article getArticle(@PathVariable Optional<Integer> optionalArticleId) {
//  if (optionalArticleId.isPresent()) {
//      Integer articleId = optionalArticleId.get();
//      //...
//  } else {
//      //...
//  }
//}


//Optional<String> opt = Optional.of("baeldung");
//opt.ifPresent(name -> System.out.println(name.length()));
//String name = Optional.ofNullable(nullName).orElse("john");
//String name = Optional.ofNullable(nullName).orElseGet(() -> "john");
//String name = Optional.ofNullable(nullName).orElseThrow(
//	      IllegalArgumentException::new);
//	}

//Integer year = 2016;
//Optional<Integer> yearOptional = Optional.of(year);
//boolean is2016 = yearOptional.filter(y -> y == 2016).isPresent();
//assertTrue(is2016);
//boolean is2017 = yearOptional.filter(y -> y == 2017).isPresent();
//assertFalse(is2017);


//----------------------------------  End Temp ----------------------------------


