package com.thegather.api.infrastructure.dao;

import com.thegather.api.domain.entities.Company;
import com.thegather.api.domain.interfaces.dao.ICompanyDAO;
import com.thegather.api.infrastructure.DbContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyDAO implements ICompanyDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
    private static final String INSERT_SQL = "INSERT INTO COMPANY (NAME, ADDRESS, CEP, LINK, SEGMENT, CORPORATE_NAME, CNPJ, PHONE, CREATOR) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_SQL = "DELETE FROM COMPANY WHERE ID = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM COMPANY";
    private static final String UPDATE_SQL = "UPDATE COMPANY SET NAME = ?, ADDRESS = ?, CEP = ?, LINK = ?, SEGMENT = ?, CORPORATE_NAME = ?, CNPJ = ?, PHONE = ?, CREATOR = ? WHERE ID = ?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM COMPANY WHERE ID = ?";
    private static final String SELECT_LAST = "SELECT * FROM COMPANY WHERE ID = (select max(ID) from COMPANY)";

    @Override
    public Company createCompany(Company company) {
        Company comp = null;
        try (Connection connection = DbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, company.getName());
            statement.setString(2, company.getAddress());
            statement.setString(3, company.getCEP());
            statement.setString(4, company.getLink());
            statement.setString(5, company.getSegment());
            statement.setString(6, company.getCorporate_name());
            statement.setString(7, company.getCNPJ());
            statement.setString(8, company.getPhone());
            statement.setInt(9, company.getCreator());

            int rowsAffected = statement.executeUpdate();
            System.out.println("rowsAffected " + rowsAffected);
            if (rowsAffected == 1) {
                comp = getLastCompany();
                return comp;
            }
        } catch (SQLException e) {
            logger.error("Error creating company: {}", company, e);
        }
        return null;
    }

    @Override
    public boolean deleteCompany(Long id) {
        try (Connection connection = DbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {

            statement.setLong(1, id);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Company getLastCompany() {
        Company company = null;
        try (Connection connection = DbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_LAST);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                company = new Company();
                company.setId(resultSet.getLong("ID"));
                company.setName(resultSet.getString("NAME"));
                company.setCorporate_name(resultSet.getString("CORPORATE_NAME"));
                company.setAddress(resultSet.getString("ADDRESS"));
                company.setCNPJ(resultSet.getString("CNPJ"));
                company.setLink(resultSet.getString("LINK"));
                company.setCreator(resultSet.getInt("CREATOR"));
                company.setPhone(resultSet.getString("PHONE"));
                company.setSegment(resultSet.getString("SEGMENT"));
                company.setCEP(resultSet.getString("CEP"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return company;
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>();
        try (Connection connection = DbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Company company = new Company();
                company.setId(resultSet.getLong("ID"));
                company.setName(resultSet.getString("NAME"));
                company.setCorporate_name(resultSet.getString("CORPORATE_NAME"));
                company.setAddress(resultSet.getString("ADDRESS"));
                company.setCNPJ(resultSet.getString("CNPJ"));
                company.setLink(resultSet.getString("LINK"));
                company.setCreator(resultSet.getInt("CREATOR"));
                company.setPhone(resultSet.getString("PHONE"));
                company.setSegment(resultSet.getString("SEGMENT"));
                company.setCEP(resultSet.getString("CEP"));
                companies.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    @Override
    public int updateCompany(Company company) {
        try (Connection connection = DbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {

            statement.setString(1, company.getName());
            statement.setString(2, company.getAddress());
            statement.setString(3, company.getCEP());
            statement.setString(4, company.getLink());
            statement.setString(5, company.getSegment());
            statement.setString(6, company.getCorporate_name());
            statement.setString(7, company.getCNPJ());
            statement.setString(8, company.getPhone());
            statement.setInt(9, company.getCreator());
            statement.setLong(10, company.getId());

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Company getCompanyById(long id) {
        try (Connection connection = DbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Company company = new Company();
                    company.setId(resultSet.getLong("ID"));
                    company.setName(resultSet.getString("NAME"));
                    company.setCorporate_name(resultSet.getString("CORPORATE_NAME"));
                    company.setAddress(resultSet.getString("ADDRESS"));
                    company.setCNPJ(resultSet.getString("CNPJ"));
                    company.setLink(resultSet.getString("LINK"));
                    company.setCreator(resultSet.getInt("CREATOR"));
                    company.setPhone(resultSet.getString("PHONE"));
                    company.setSegment(resultSet.getString("SEGMENT"));
                    company.setCEP(resultSet.getString("CEP"));
                    return company;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
