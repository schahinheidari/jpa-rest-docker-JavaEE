package com.example.tp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.tp.dao.CelebritReservoir;
import com.example.tp.dao.DepartementReservoir;
import com.example.tp.dao.LieuReservoir;
import com.example.tp.dao.MonumentReservoir;
import com.example.tp.entities.Celebrite;
import com.example.tp.services.AppService;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private DepartementReservoir departementRepository;

    @Autowired
    private MonumentReservoir monumentRepository;

    @Autowired
    private LieuReservoir lieuRepository;

    @Autowired
    private CelebritReservoir celebriteRepository;

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