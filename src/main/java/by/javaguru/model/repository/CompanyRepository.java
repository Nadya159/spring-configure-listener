package by.javaguru.repository;

import by.javaguru.model.Company;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class CompanyRepository {
    private final Connection connection;

    private final static String FIND_BY_ID_SQL = """
            SELECT *
            FROM company
            WHERE id = ?
            """;

    private CompanyRepository(Connection connection) {
        this.connection = connection;
    }

    public Optional<Company> findCompanyById(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            var result = statement.executeQuery();
            Company company = null;
            if (result.next()) {
                company = Company.builder()
                        .id(result.getInt("id"))
                        .name(result.getString("name"))
                        .build();
            }
            return Optional.ofNullable(company);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
