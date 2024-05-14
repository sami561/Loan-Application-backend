package com.bank.requestSubmit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService implements IRequestService {
    @Autowired
    RequestRepo rr;


    @Override
    public RequestSubmit CreateRequest(RequestSubmit r) {
        return rr.save(r);
    }

    @Override
    public List<RequestSubmit> findAllRequest() {
        return rr.findAll();
    }

    @Override
    public RequestSubmit findRequestById(int id) {
        return rr.findById(id).get();
    }

    @Override
    public void DeleteRequest(int id) {
        rr.deleteById(id);
    }

    @Override
    public RequestSubmit UpdateRequest(RequestSubmit r) {
        return rr.save(r);
    }
}
