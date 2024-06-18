package com.thegather.api.infrastructure.repositories;

import com.thegather.api.domain.entities.Company;
import com.thegather.api.domain.interfaces.repositories.ICompanyRepo;
import com.thegather.api.infrastructure.dao.CompanyDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CompanyRepository implements ICompanyRepo {

    private final CompanyDAO companyDAO;

    public CompanyRepository(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    @Override
    public Company createCompany(Company company) {
        return companyDAO.createCompany(company);
    }

    @Override
    public boolean deleteCompany(Long id) {
        return companyDAO.deleteCompany(id);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyDAO.getAllCompanies();
    }

    @Override
    public int updateCompany(Company company) {
        return companyDAO.updateCompany(company);
    }

    @Override
    public Company getCompanyById(long id) {
        return companyDAO.getCompanyById(id);
    }
}
