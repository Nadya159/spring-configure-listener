package by.javaguru.model.repository;

import by.javaguru.model.entity.Company;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CompanyRepository implements CrudRepository<Integer, Company> {
    private final Connection connection;

    private final static String FIND_ALL_SQL = """
            SELECT id, name
            FROM company
            """;
    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
    private final static String SAVE_SQL = """
            INSERT INTO company (name)
            VALUES (?)
            """;
    private final static String UPDATE_SQL = """
            UPDATE company
            SET name = ?
            WHERE id = ?
            """;
    private final static String DELETE_SQL = """
            DELETE FROM company WHERE id=?
            """;

    public CompanyRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Company> findById(Integer id) {
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

    @Override
    public List<Company> findAll() {
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Company> companies = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                companies.add(Company.builder()
                        .id(result.getInt("id"))
                        .name(result.getString("name"))
                        .build());
            return companies;
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
    public Company save(Company company) {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, company.getName());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            keys.next();
            company.setId(keys.getObject("id", Integer.class));
            return company;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Company company) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, company.getName());
            statement.setInt(2, company.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
