package com.bank.requestSubmit;

import java.util.List;

public interface IRequestService {
     public RequestSubmit CreateRequest(RequestSubmit r);
     public List<RequestSubmit> findAllRequest();
     public RequestSubmit  findRequestById(int id);
     public void DeleteRequest(int id);
     public RequestSubmit UpdateRequest(RequestSubmit r);
}
