package com.example.tp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.tp.entities.User;


@Repository
@Transactional(readOnly = true)
public interface UserReservoir extends JpaRepository<User, String>  {


    @Modifying
    @Query("delete from User u where  u.email = ?1")
    public int deleteUser(String email);

    @Query("select u from User u where  u.email = ?1")
    public User getUser(String email);

//	public User save(User user);




}
