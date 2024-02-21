package by.javaguru.model.repository;

import by.javaguru.model.entity.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserRepository {
    private final Connection connection;

    private final static String FIND_BY_ID_SQL = """
            SELECT *
            FROM users
            WHERE id = ?
            """;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    //@Autowired
    public Optional<User> findById(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            var result = statement.executeQuery();
            User user = null;
            if (result.next()) {
                user = User.builder()
                        .id(result.getInt("id"))
                        .username(result.getString("username"))
                        .build();
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
