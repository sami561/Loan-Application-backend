package com.bank.bank;

import com.bank.gouvernorat.IGouvernoratService;
import com.bank.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bank")
public class BankController {
    @Autowired
    IBankService ibs;
    @Autowired
    IGouvernoratService igs;
    @Autowired
    UserRepository ur;
    @GetMapping("/find/{id}")
    public ResponseEntity<Bank> findBankById(@PathVariable("id") int id) {
        Bank bank = ibs.findBankById(id);
        if (bank == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(bank);
    }

    @PostMapping("/add")
    public ResponseEntity<Bank>addBank(@RequestBody Bank b,@RequestParam int userId,@RequestParam int gauvernoratId){
        b.setGouvernorat(igs.findGouvernoratById(gauvernoratId));
        b.setUser(ur.findById(userId).orElseThrow());
        if(b.getUser() !=null && b.getGouvernorat() !=null ){
        Bank br = ibs.createBank(b);
        return ResponseEntity.ok(br);
        } else {
            // Return JSON message indicating the error
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "User or Gouvernorat not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((Bank)errorResponse);
        }

    }

    @PutMapping("/update")
    public ResponseEntity<Bank>updateBank(@RequestBody Bank b,@RequestParam int userId,@RequestParam int gauvernoratId){
        b.setGouvernorat(igs.findGouvernoratById(gauvernoratId));
        b.setUser(ur.findById(userId).orElseThrow());
        if(b.getUser() !=null && b.getGouvernorat() !=null ){
            Bank br = ibs.updateBank(b);
            return ResponseEntity.ok(br);
        } else {

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "User or Gouvernorat not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((Bank) errorResponse);
        }

    }


    @DeleteMapping("/delete/{id}")
    public void  deleteBank(@PathVariable("id") int id){
        ibs.deleteBank(id);
    }



    @GetMapping("/all")
    public ResponseEntity<List<Bank>> findAll(){
        return ResponseEntity.ok(ibs.findAllBanks());
    }
}
