package by.javaguru.model.repository;

import by.javaguru.model.entity.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements CrudRepository<Integer, User> {
    private final Connection connection;

    private final static String FIND_ALL_SQL = """
            SELECT id, username, company_id
            FROM users
            """;
    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """ 
            WHERE id = ?""";

    private final static String SAVE_SQL = """
            INSERT INTO users (username, company_id)
            VALUES (?, ?)
            """;
    private final static String UPDATE_SQL = """
            UPDATE users
            SET username = ?, company_id = ?
            WHERE id = ?
            """;
    private final static String DELETE_SQL = """
            DELETE FROM users WHERE id=?
            """;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<User> findById(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            var result = statement.executeQuery();
            User user = null;
            if (result.next()) {
                user = User.builder()
                        .id(result.getInt("id"))
                        .username(result.getString("username"))
                        .companyId(result.getInt("company_id"))
                        .build();
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<User> users = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                users.add(User.builder()
                        .id(result.getInt("id"))
                        .username(result.getString("username"))
                        .companyId(result.getInt("company_id"))
                        .build());
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User save(User user) {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setInt(2, user.getCompanyId());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            keys.next();
            user.setId(keys.getObject("id", Integer.class));
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, user.getUsername());
            statement.setInt(2, user.getCompanyId());
            statement.setInt(3, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
