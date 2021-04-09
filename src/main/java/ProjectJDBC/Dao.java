package ProjectJDBC;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Dao<T> {

    ResultSet selectAll() throws SQLException;
    ResultSet selectById(int id) throws SQLException;
    ResultSet select(String sqlRequest) throws SQLException;

    boolean update(T values, int id) throws SQLException;

    boolean delete(String clause) throws SQLException;
    boolean deleteById(int id) throws SQLException;

    int insert (T values) throws SQLException;

    boolean bulkInsert(File csv) throws SQLException;

}
