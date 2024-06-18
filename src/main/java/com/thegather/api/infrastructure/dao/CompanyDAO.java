package com.thegather.api.infrastructure.dao;

import com.thegather.api.application.services.CompanyService;
import com.thegather.api.domain.entities.Company;
import com.thegather.api.domain.entities.Event;
import com.thegather.api.domain.interfaces.dao.ICompanyDAO;
import com.thegather.api.domain.entities.Company;
import com.thegather.api.domain.interfaces.dao.ICompanyDAO;
import com.thegather.api.infrastructure.DbContext;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class CompanyDAO implements ICompanyDAO {
    @Override
    public Company createCompany(Company company) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DbContext.getConnection();
            String sql = "INSERT INTO COMPANY (NAME, ADDRESS, CEP, LINK, SEGMENT, CORPORATE_NAME, CNPJ, PHONE, CREATOR) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, company.getName());
            statement.setString(2, company.getAddress());
            statement.setString(3, company.getCEP());
            statement.setString(4, company.getLink());
            statement.setString(5, company.getSegment() );
            statement.setString(6, company.getCorporate_name());
            statement.setString(7, company.getCNPJ());
            statement.setString(8, company.getPhone());
            statement.setInt(9, company.getCreator());

            statement.executeUpdate();

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 1) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    long companyId = resultSet.getLong(1);
                    company.setId(companyId);
                }
                return company;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean deleteCompany(Long id) {
            Connection connection = null;
            PreparedStatement statement = null;

            try {
                connection = DbContext.getConnection();

                statement = connection.prepareStatement("delete from COMPANY where ID = ?");
                statement.setLong(1, id);

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected == 1) {
                    return true;
                } else if (rowsAffected == 0) {
                    return false;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

    }

    @Override
    public List<Company> getAllCompanies() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Company> companies = new ArrayList<Company>();
        try {
            connection = DbContext.getConnection();
            statement = connection.prepareStatement("SELECT * FROM COMPANY");
            resultSet = statement.executeQuery();

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
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return companies;
    }

    @Override

    public int updateCompany(Company company) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DbContext.getConnection();
            String sql = "UPDATE COMPANY " +
                     "SET NAME = ?, ADDRESS = ?, CEP = ? ,LINK = ?, SEGMENT = ? , CORPORATE_NAME = ?, CNPJ = ? , PHONE = ?, CREATOR = ? WHERE ID = ?";

            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, company.getName());
            statement.setString(2, company.getAddress());
            statement.setString(3, company.getCEP());
            statement.setString(4, company.getLink());
            statement.setString(5, company.getSegment() );
            statement.setString(6, company.getCorporate_name());
            statement.setString(7, company.getCNPJ());
            statement.setString(8, company.getPhone());
            statement.setInt(9, company.getCreator());
            statement.setLong(10, company.getId());

            statement.executeUpdate();

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    company.setId(resultSet.getLong(1));
                }
            }
            return rowsUpdated;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Company getCompanyById(long id) {
        return null;
    }
}
