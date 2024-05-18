package com.bank.CreditType;

import org.springframework.data.domain.Page;

import java.util.List;

public class CreditTypeService implements ICreditTypeService{
    @Override
    public CreditType CreateCreditType(CreditType ct) {
        return null;
    }

    @Override
    public List<CreditType> findAllCreditType() {
        return null;
    }

    @Override
    public CreditType findCreditTypeById(int id) {
        return null;
    }

    @Override
    public void DeleteCreditType(int id) {

    }

    @Override
    public CreditType UpdateCreditType(CreditType ct) {
        return null;
    }

    @Override
    public Page<CreditType> findAllPaginatedCreditType(int pageNum, int pageSize, String sortField, String sortDir) {
        return null;
    }
}
