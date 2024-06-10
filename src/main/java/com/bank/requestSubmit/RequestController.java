package com.bank.requestSubmit;

import com.bank.CreditType.CreditTypeRepo;
import com.bank.bank.Bank;
import com.bank.bank.BankRepo;
import com.bank.bank.IBankService;
import com.bank.file.FileStorageService;
import com.bank.user.UserRepository;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    CreditTypeRepo cr;
    @Autowired
    RequestRepo rr;
    @Autowired
    private FileStorageService fileStorageService;
    @PostMapping(value = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> addRequest(@RequestPart("request") RequestSubmit r, @RequestPart("file") MultipartFile file) {
        r.setBank(br.findById(r.getBankId()).orElseThrow());
        r.setUser(ur.findById(r.getUserId()).orElseThrow());
        r.setCreditType(cr.findById(r.getCreditTypeId()).orElseThrow());
        if (r.getUser() != null && r.getBank() != null) {
            String imagePath = fileStorageService.saveFile(file, r.getId(), null); // Assuming user ID is not required here
            r.setStatus("pending");
            r.setFile(imagePath);
            RequestSubmit req = rr.save(r);
            return ResponseEntity.ok(req);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "User or Bank not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
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
    @PostMapping(value = "/file/{request-id}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadRequestFile(
            @PathVariable("request-id") Integer requestId,
            @Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication connectedUser
    ) {
        irs.uploadQuotePicture(file, connectedUser, requestId);
        return ResponseEntity.accepted().build();
    }

}
