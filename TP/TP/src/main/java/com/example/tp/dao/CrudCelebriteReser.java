package com.example.tp.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.tp.entities.Celebrite;

@Repository("crud")
public interface CrudCelebriteReser extends CrudRepository<Celebrite, Long> {

}