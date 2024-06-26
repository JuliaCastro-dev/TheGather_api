package com.thegather.api.infrastructure.dao;

import com.thegather.api.domain.entities.Event;
import com.thegather.api.domain.interfaces.dao.IEventDAO;
import com.thegather.api.infrastructure.DbContext;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EventDAO implements IEventDAO {
    private static final Logger logger = LoggerFactory.getLogger(EventDAO.class);
    @Override
    public Event createEvent(Event event) {
        String sql = "INSERT INTO EVENT (TITLE, DESCRIPTION, EVENT_DATE, EVENT_TIME, IMAGE, CREATOR, COMPANY, IS_PRIVATE, NOTIFICATION_WHATS, NOTIFICATION_EMAIL, NOTIFICATION_SMS, DATE_START, TIME_START, DATE_END, TIME_END, LINK) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection connection = DbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, event.getTitle());
            statement.setString(2, event.getDescription());
            statement.setDate(3, java.sql.Date.valueOf(event.getEvent_date()));
            statement.setTime(4, java.sql.Time.valueOf(event.getEvent_time()));
            statement.setString(5, event.getImage());
            statement.setInt(6, event.getCreator());
            statement.setInt(7, event.getCompany());
            statement.setBoolean(8, event.isIs_private());
            statement.setBoolean(9, event.isNotification_whats());
            statement.setBoolean(10, event.isNotification_email());
            statement.setBoolean(11, event.isNotification_sms());

            if (event.getDate_start() != null) {
                statement.setDate(12, java.sql.Date.valueOf(event.getDate_start()));
            } else {
                statement.setNull(12, Types.DATE);
            }

            if (event.getTime_start() != null) {
                statement.setTime(13, java.sql.Time.valueOf(event.getTime_start()));
            } else {
                statement.setNull(13, Types.TIME);
            }

            if (event.getDate_end() != null) {
                statement.setDate(14, java.sql.Date.valueOf(event.getDate_end()));
            } else {
                statement.setNull(14, Types.DATE);
            }

            if (event.getTime_end() != null) {
                statement.setTime(15, java.sql.Time.valueOf(event.getTime_end()));
            } else {
                statement.setNull(15, Types.TIME);
            }

            if (event.getLink() != null) {
                statement.setString(16, event.getLink());
            } else {
                statement.setNull(16, Types.VARCHAR);
            }

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 1) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        long eventId = resultSet.getLong(1);
                        event.setId(eventId);
                    }
                }
                return event;
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("Error creating event: {}", event, e.getCause());
            return null;
        }
    }

    @Override
    public Event getById(Long id) {
        String sql = "SELECT * FROM EVENT WHERE ID = ?";

        try (Connection connection = DbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mappedEvent(resultSet);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("Error retrieving event by ID: {}", id, e);
            return null;
        }
    }

    private Event mappedEvent(ResultSet resultSet) throws SQLException {
        Long idEvent = resultSet.getLong("ID");
        int creator = resultSet.getInt("CREATOR");
        int company = resultSet.getInt("COMPANY");
        boolean is_private = resultSet.getBoolean("IS_PRIVATE");
        boolean notification_whats = resultSet.getBoolean("NOTIFICATION_WHATS");
        boolean notification_email = resultSet.getBoolean("NOTIFICATION_EMAIL");
        boolean notification_sms = resultSet.getBoolean("NOTIFICATION_SMS");
        String title = resultSet.getString("TITLE");
        String image = resultSet.getString("IMAGE");
        LocalDate event_date = resultSet.getDate("EVENT_DATE").toLocalDate();
        LocalTime event_time = resultSet.getTime("EVENT_TIME").toLocalTime();

        Optional<LocalDate> date_start = Optional.ofNullable(resultSet.getDate("DATE_START") != null ? resultSet.getDate("DATE_START").toLocalDate() : null);
        Optional<LocalTime> time_start = Optional.ofNullable(resultSet.getTime("TIME_START") != null ? resultSet.getTime("TIME_START").toLocalTime() : null);
        Optional<LocalDate> date_end = Optional.ofNullable(resultSet.getDate("DATE_END") != null ? resultSet.getDate("DATE_END").toLocalDate() : null);
        Optional<LocalTime> time_end = Optional.ofNullable(resultSet.getTime("TIME_END") != null ? resultSet.getTime("TIME_END").toLocalTime() : null);
        Optional<String> link = Optional.ofNullable(resultSet.getString("LINK"));

        return new Event(idEvent, creator, company, is_private, notification_whats, notification_email, notification_sms, title, image, event_date, event_time, date_start, time_start, date_end, time_end, link, resultSet.getString("DESCRIPTION"));
    }

    @Override
    public int updateEvent(Event event) {
        String sql = "UPDATE EVENT SET TITLE = ?, DESCRIPTION = ?, EVENT_DATE = ?, EVENT_TIME = ?, IMAGE = ?, CREATOR = ?, COMPANY = ?, IS_PRIVATE = ?, NOTIFICATION_WHATS = ?, NOTIFICATION_EMAIL = ?, NOTIFICATION_SMS = ?, DATE_START = ?, TIME_START = ?, DATE_END = ?, TIME_END = ?, LINK = ? WHERE ID = ?";

        try (Connection connection = DbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, event.getTitle());
            statement.setString(2, event.getDescription());
            statement.setDate(3, java.sql.Date.valueOf(event.getEvent_date()));
            statement.setTime(4, java.sql.Time.valueOf(event.getEvent_time()));
            statement.setString(5, event.getImage());
            statement.setInt(6, event.getCreator());
            statement.setInt(7, event.getCompany());
            statement.setBoolean(8, event.isIs_private());
            statement.setBoolean(9, event.isNotification_whats());
            statement.setBoolean(10, event.isNotification_email());
            statement.setBoolean(11, event.isNotification_sms());

            if (event.getDate_start() != null) {
                statement.setDate(12, java.sql.Date.valueOf(event.getDate_start()));
            } else {
                statement.setNull(12, Types.DATE);
            }

            if (event.getTime_start() != null) {
                statement.setTime(13, java.sql.Time.valueOf(event.getTime_start()));
            } else {
                statement.setNull(13, Types.TIME);
            }

            if (event.getDate_end() != null) {
                statement.setDate(14, java.sql.Date.valueOf(event.getDate_end()));
            } else {
                statement.setNull(14, Types.DATE);
            }

            if (event.getTime_end() != null) {
                statement.setTime(15, java.sql.Time.valueOf(event.getTime_end()));
            } else {
                statement.setNull(15, Types.TIME);
            }

            if (event.getLink() != null) {
                statement.setString(16, event.getLink());
            } else {
                statement.setNull(16, Types.VARCHAR);
            }

            statement.setLong(17, event.getId());

            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error updating event: {}", event, e);
            return 0;
        }
    }

    @Override
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM EVENT";

        try (Connection connection = DbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                events.add(mappedEvent(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public Boolean delete(Long id) {
        String sql = "DELETE FROM EVENT WHERE ID = ?";

        try (Connection connection = DbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
