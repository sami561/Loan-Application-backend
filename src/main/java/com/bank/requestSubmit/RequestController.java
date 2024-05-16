package com.bank.requestSubmit;

import com.bank.bank.Bank;
import com.bank.bank.BankRepo;
import com.bank.bank.IBankService;
import com.bank.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/request")
public class RequestController {
    @Autowired
    IRequestService irs;
    @Autowired
    IBankService ibs;
    @Autowired
    BankRepo br;
    @Autowired
    UserRepository ur;
    @PostMapping("/add")
    public ResponseEntity<RequestSubmit> addRequest(@RequestBody RequestSubmit r, @RequestParam int userId, @RequestParam int bankId){
          r.setBank(br.findById(bankId).orElseThrow());
        r.setUser(ur.findById(userId).orElseThrow());
        if(r.getUser() !=null && r.getBank()!=null ){
            RequestSubmit req = irs.CreateRequest(r);
            return ResponseEntity.ok(req);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "User or Gouvernorat not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((RequestSubmit)errorResponse);
        }

    }
    @GetMapping("/all")
    public ResponseEntity<List<RequestSubmit>> findAll(){
        return ResponseEntity.ok(irs.findAllRequest());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<RequestSubmit> findRequestById(@PathVariable("id") int id) {
        RequestSubmit Request = irs.findRequestById(id);
        if (Request == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(Request);
    }
    @DeleteMapping("/delete/{id}")
    public void  deleteRequest(@PathVariable("id") int id){
        irs.DeleteRequest(id);
    }
    @PutMapping("/update")
    public ResponseEntity<RequestSubmit>updateBank(@RequestBody RequestSubmit r,@RequestParam int userId, @RequestParam int bankId){
        r.setBank(br.findById(bankId).orElseThrow());
        r.setUser(ur.findById(userId).orElseThrow());

        if(r.getUser() !=null &&r.getBank() !=null ){
            RequestSubmit request =irs.UpdateRequest(r);
            return ResponseEntity.ok(request);
        } else {

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "User or Gouvernorat not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((RequestSubmit) errorResponse);
        }

    }
    @GetMapping("/allP")
    public Page<RequestSubmit> getPaginatedRequests(
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sortField", defaultValue = "id") String sortField,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        return irs.findAllPaginatedRequest(pageNum, pageSize, sortField, sortDir);
    }

}
