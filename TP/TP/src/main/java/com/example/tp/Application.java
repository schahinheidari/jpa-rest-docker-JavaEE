package com.example.tp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.tp.dao.CelebriteRepository;
import com.example.tp.dao.DepartementRepository;
import com.example.tp.dao.LieuRepository;
import com.example.tp.dao.MonumentRepository;
import com.example.tp.entities.Celebrite;
import com.example.tp.services.AppService;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private MonumentRepository monumentRepository;

    @Autowired
    private LieuRepository lieuRepository;

    @Autowired
    private CelebriteRepository celebriteRepository;

    @Autowired
    private AppService appService;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub


        for (Celebrite celeb : this.celebriteRepository.findAll())
            System.out.println(celeb.toString());


    }

}