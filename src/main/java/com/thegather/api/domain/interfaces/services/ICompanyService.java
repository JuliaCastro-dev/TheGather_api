package com.thegather.api.domain.interfaces.services;

import com.thegather.api.domain.entities.Company;

import java.util.List;

public interface ICompanyService {
    Company createCompany(Company company);
    boolean deleteCompany(Long id);
    List<Company> getAllCompanies();
    int updateCompany(Company company);
    Company getCompanyById(long id);
    Company getLastCompany();
}
