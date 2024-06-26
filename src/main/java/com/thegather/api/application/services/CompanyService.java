package com.thegather.api.application.services;

import com.thegather.api.domain.entities.Company;
import com.thegather.api.domain.interfaces.repositories.ICompanyRepo;
import com.thegather.api.domain.interfaces.services.ICompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CompanyService implements ICompanyService {
    ICompanyRepo companyRepo;

    public CompanyService(ICompanyRepo companyRepo) {
        this.companyRepo = companyRepo;
    }

    @Override
    public Company createCompany(Company company) {
        return companyRepo.createCompany(company);
    }

    @Override
    public boolean deleteCompany(Long id) {
        return companyRepo.deleteCompany(id);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepo.getAllCompanies();
    }

    @Override
    public int updateCompany(Company company) {
        return companyRepo.updateCompany(company);
    }

    @Override
    public Company getCompanyById(long id) {
        return companyRepo.getCompanyById(id);
    }

    @Override
    public Company getLastCompany() {
        return companyRepo.getLastCompany();
    }
}
