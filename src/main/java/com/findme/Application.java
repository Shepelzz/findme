package com.findme;

import com.findme.dao.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@SpringBootApplication
public class Application {


    private static UserDAO sUserDAO;
    private UserDAO userDAO;

    private static EntityManager sEntityManager;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public Application(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @PostConstruct
    public void init(){
        Application.sUserDAO = userDAO;
    }

    public static void main(String[] args) throws Exception{
        SpringApplication.run(Application.class, args);

        sUserDAO.delete(3L);
        System.out.println("Ok");


    }

    public static Date makeDate(String dt) throws ParseException {
        return new SimpleDateFormat("yyyy-mm-dd").parse(dt);
    }

}