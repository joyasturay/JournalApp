package com.example.journalApp.service;
import com.example.journalApp.entity.User;
import com.example.journalApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class UserService {
    @Autowired
    private UserRepo usersRepo;

    public void saveEntry(User user){
        usersRepo.save(user);
    }

    public List<User> getAll(){
        return usersRepo.findAll();
    }

    public Optional<User> findById(String id){
        return  usersRepo.findById(id);
    }

    public void deleteById(String id){
        usersRepo.deleteById(id);
    }

    public User findByUserName(String userName) {
        return usersRepo.findByUserName(userName).orElse(null); // Avoids null pointer exceptions
    }

}
