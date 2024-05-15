package com.bank.requestSubmit;

import org.springframework.data.domain.Page;

import java.util.List;

public interface IRequestService {
     public RequestSubmit CreateRequest(RequestSubmit r);
     public List<RequestSubmit> findAllRequest();
     public RequestSubmit  findRequestById(int id);
     public void DeleteRequest(int id);
     public RequestSubmit UpdateRequest(RequestSubmit r);
     public Page<RequestSubmit> findAllPaginatedRequest(int pageNum, int pageSize, String sortField, String sortDir);

}
