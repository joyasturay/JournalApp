package com.example.journalApp.controller;
import com.example.journalApp.JournalApplication;
import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.User;
import com.example.journalApp.service.JournalEntryService;
import com.example.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.*;
@RestController
@RequestMapping("/journal")
public class JornalEntryControllerV2 {
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<JournalEntry> getAll(){
        return journalEntryService.getAll() ;
    }


    @GetMapping("{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username) {
        User user = userService.findByUserName(username);

        // Check if user is null
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        // Get journal entries
        List<JournalEntry> all = user.getJournalEntries();

        // Check if journal entries exist
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>("No journal entries found", HttpStatus.NO_CONTENT);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myentry,@PathVariable String userName){
        try{
            journalEntryService.saveEntry(myentry,userName);
            return new ResponseEntity<>(myentry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(myentry,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/id/{myid}")
    public ResponseEntity<JournalEntry> getJournalEntryByID(@PathVariable String myid){
        Optional<JournalEntry> journalEntry=journalEntryService.findById(myid);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{username}/{myid}")
    public boolean deleteEntry(@PathVariable String myid,@PathVariable String username){
        journalEntryService.deleteById(myid,username);
         return true;
    }

    @PutMapping("/id/{username}/{myid}")
    public JournalEntry updateEntry(@PathVariable String myid, @RequestBody JournalEntry newEntry,@PathVariable String username){
        JournalEntry old=journalEntryService.findById(myid).orElse(null);
        if(old!=null){
            old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
            journalEntryService.saveNewEntry(old);
        }
        return old;
    }

}
