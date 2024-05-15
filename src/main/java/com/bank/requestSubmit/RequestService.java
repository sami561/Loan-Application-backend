package com.bank.requestSubmit;

import com.bank.book.Book;
import com.bank.book.BookResponse;
import com.bank.common.PageResponse;
import com.bank.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
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

    @Override
    public Page<RequestSubmit> findAllPaginatedRequest(int pageNum, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        return rr.findAll(pageable);
    }




}
