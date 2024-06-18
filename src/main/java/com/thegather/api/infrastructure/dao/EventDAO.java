package com.thegather.api.infrastructure.dao;


import com.thegather.api.domain.entities.Event;
import com.thegather.api.domain.interfaces.dao.IEventDAO;
import com.thegather.api.infrastructure.DbContext;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
@Component
public class EventDAO implements IEventDAO {
    Connection connection;
    @Override
    public Event createEvent(Event event) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DbContext.getConnection();
            statement = connection.
                    prepareStatement("INSERT INTO EVENT (TITLE, DESCRIPTION, EVENT_DATE, EVENT_TIME," +
                            "IMAGE, CREATOR, COMPANY, IS_PRIVATE,NOTIFICATION_WHATS, NOTIFICATION_EMAIL," +
                            " NOTIFICATION_SMS) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            statement.setString(1, event.getTitle());
            statement.setString(2, event.getDescription());
            statement.setDate(3, java.sql.Date.valueOf(event.getEvent_date()));
            statement.setTime(4, java.sql.Time.valueOf(event.getEvent_time().toString()));
            statement.setString(5, event.getImage());
            statement.setInt(6, event.getCreator());
            statement.setInt(7, event.getCompany());
            statement.setBoolean(8, event.getIs_private());
            statement.setBoolean(9, event.isNotification_whats());
            statement.setBoolean(10, event.isNotification_email());
            statement.setBoolean(11, event.isNotification_sms());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 1) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    long eventId = resultSet.getLong(1);
                    event.setId(eventId);
                }
                return event;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Event getById(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DbContext.getConnection();
            statement = connection.prepareStatement("SELECT * FROM EVENT WHERE ID = ?");
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            // Check if a record was found
            if (resultSet.next()) {
                Event event = new Event();
                event.setId(resultSet.getLong("ID"));

                event.setTitle(resultSet.getString("TITLE"));
                event.setDescription(resultSet.getString("DESCRIPTION"));
                event.setEvent_date(resultSet.getDate("EVENT_DATE").toLocalDate());
                event.setEvent_time(resultSet.getTime("EVENT_TIME").toLocalTime());
                event.setImage(resultSet.getString("IMAGE"));
                event.setCreator(resultSet.getInt("CREATOR"));
                event.setCompany(resultSet.getInt("COMPANY"));
                event.setIs_private(resultSet.getBoolean("IS_PRIVATE"));
                event.setNotification_whats(resultSet.getBoolean("NOTIFICATION_WHATS"));
                event.setNotification_email(resultSet.getBoolean("NOTIFICATION_EMAIL"));
                event.setNotification_sms(resultSet.getBoolean("NOTIFICATION_SMS"));
                return event;
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

    public int updateEvent(Event event){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DbContext.getConnection();
            String sql = "UPDATE EVENT " +
                    "SET TITLE = ?, DESCRIPTION = ?, EVENT_DATE = ?, EVENT_TIME = ?, " +
                    "IMAGE = ?, CREATOR = ?, COMPANY = ?, IS_PRIVATE = ?, " +
                    "NOTIFICATION_WHATS = ?, NOTIFICATION_EMAIL = ?, NOTIFICATION_SMS = ? " +
                    "WHERE ID = ?";

            statement = connection.prepareStatement(sql);
            statement.setString(1, event.getTitle());
            statement.setString(2, event.getDescription());
            statement.setDate(3, java.sql.Date.valueOf(event.getEvent_date()));
            statement.setTime(4, java.sql.Time.valueOf(event.getEvent_time().toString()));
            statement.setString(5, event.getImage());
            statement.setInt(6, event.getCreator());
            statement.setInt(7, event.getCompany());
            statement.setBoolean(8, event.getIs_private());
            statement.setBoolean(9, event.isNotification_whats());
            statement.setBoolean(10, event.isNotification_email());
            statement.setBoolean(11, event.isNotification_sms());
            statement.setLong(12, event.getId());
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    event.setId(resultSet.getLong(1));
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
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<Event>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DbContext.getConnection();
            statement = connection.prepareStatement("SELECT * FROM EVENT");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Event event = new Event();
                event.setId(resultSet.getLong("ID"));
                event.setTitle(resultSet.getString("TITLE"));
                event.setDescription(resultSet.getString("DESCRIPTION"));
                event.setEvent_date(resultSet.getDate("EVENT_DATE").toLocalDate()); // Convert SQL Date to LocalDate
                event.setEvent_time(resultSet.getTime("EVENT_TIME").toLocalTime()); // Convert SQL Time to LocalTime
                event.setImage(resultSet.getString("IMAGE"));
                event.setCreator(resultSet.getInt("CREATOR"));
                event.setCompany(resultSet.getInt("COMPANY"));
                event.setIs_private(resultSet.getBoolean("IS_PRIVATE"));
                event.setNotification_whats(resultSet.getBoolean("NOTIFICATION_WHATS"));
                event.setNotification_email(resultSet.getBoolean("NOTIFICATION_EMAIL"));
                event.setNotification_sms(resultSet.getBoolean("NOTIFICATION_SMS"));
                events.add(event); // Add the populated Event object to the list
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public Boolean delete(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DbContext.getConnection();

            statement = connection.prepareStatement("delete from EVENT where ID = ?");
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
}
