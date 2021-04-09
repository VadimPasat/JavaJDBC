package ProjectJDBC;

import lombok.extern.log4j.Log4j;
import model.SalesCustomers;
import util.DatabaseConnection;

import java.io.File;
import java.sql.*;

import static util.Input.fileToArray;


@Log4j
public class CustomersDAO implements Dao<SalesCustomers> {
    private Connection connection;
    public Statement statement;

    {
        try {
            //get connection to DB
            connection = DatabaseConnection.getInstance().getConnection();
            //create a statement
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public ResultSet selectAll() {
        ResultSet rs = null;
        log.info("Execute query: SELECT * FROM sales.customers");
        try {
            rs = statement.executeQuery("SELECT * FROM production.products");
            log.info("Query was executed successfully");
        } catch (SQLException throwables) {
            log.error("Query was not executed!");
            throwables.printStackTrace();
        }
        return rs;
    }

    @Override
    public ResultSet selectById(int id) {
        log.info("SELECT * FROM sales.customers WHERE customer_id = " + id);
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM sales.customers WHERE customer_id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            log.info("Query was executed successfully");
        } catch (SQLException e) {
            log.error("Query was not executed!", e);
        }

        return rs;
    }

    @Override
    public ResultSet select(String sqlRequest) {
        ResultSet rs = null;
        log.info("Execute query: " + sqlRequest);
        try {
            rs = statement.executeQuery(sqlRequest);
            log.info("Query was executed successfully");
        } catch (SQLException e) {
            log.error("Query was not executed!", e);
        }
        return rs;
    }

    @Override
    public boolean update(SalesCustomers newValues, int id) {
        log.info("Update customer with ID " + id + " at sales.customers  table");

        String query = "UPDATE sales.customers SET " +
                "first_name=?, last_name=?, phone=?, email=?, street=?, city=?, state=?, zip_code=?" +
                " WHERE customer_id = ?";
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM sales.customers WHERE customer_id = ?");
            ps.setInt(1, id);
            ResultSet currentValues = ps.executeQuery();

            ps = connection.prepareStatement(query);

            while (currentValues.next()) {
                if (newValues.getFirstName() == null) newValues.setFirstName(currentValues.getString("first_name"));
                ps.setString(1, newValues.getFirstName());
                if (newValues.getLastName() == null) newValues.setLastName(currentValues.getString("last_name"));
                ps.setString(2, newValues.getLastName());
                if (newValues.getPhone() == null) newValues.setPhone(currentValues.getString("phone"));
                ps.setString(3, newValues.getPhone());
                if (newValues.getEmail() == null) newValues.setEmail(currentValues.getString("email"));
                ps.setString(4, newValues.getEmail());
                if (newValues.getStreet() == null) newValues.setStreet(currentValues.getString("street"));
                ps.setString(5, newValues.getStreet());
                if (newValues.getCity() == null) newValues.setCity(currentValues.getString("city"));
                ps.setString(6, newValues.getCity());
                if (newValues.getState() == null) newValues.setState(currentValues.getString("state"));
                ps.setString(7, newValues.getState());
                if (newValues.getZipCode() == null) newValues.setZipCode(currentValues.getString("zip_code"));
                ps.setString(8, newValues.getZipCode());
            }
            ps.setInt(9, id);
            ps.executeUpdate();

            log.info("Customer data is updated");
            return true;
        } catch (SQLException e) {
            log.error("Update was not executed", e);
        }
        return false;
    }

    public boolean update(String sqlRequest) {
        log.info("Execute update: " + sqlRequest);
        try {
            statement.executeUpdate(sqlRequest);
            log.info("Update was executed successfully");
            //printResultNoHardCore(statement.executeQuery(sqlRequest));
            return true;

        } catch (SQLException e) {
            log.error("Update was not executed!", e);
        }
        return false;
    }

    @Override
    public boolean delete(String clause) {
        log.info("Delete customer from sales.customers table where: " + clause);
        try {
            if (clause.isEmpty()) {
                log.info("The clause is not specified, cannot delete");
                return false;
            }
            statement.executeUpdate("DELETE FROM sales.customers WHERE " + clause);
            log.info("Delete was executed successfully");
            return true;

        } catch (SQLException e) {
            log.error("Delete was not processed!", e);
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        log.info("Delete customer with ID " + id + " from sales.customers table");
        String query = "DELETE FROM sales.customers WHERE customer_id = ?";
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();

            log.info("Customer is deleted");
            return true;
        } catch (SQLException e) {
            log.error("Delete was not processed", e);
        }
        return false;
    }

    public int insert(SalesCustomers values) {
        int rs = 0;
        log.info("Add new customer to sales.customers table");
        try {
            rs = statement.executeUpdate("INSERT INTO sales.customers " +
                    "(first_name, last_name, phone, email, street, city, state, zip_code)" +
                    " VALUES " + "('" +
                    values.getFirstName() + "', '" +
                    values.getLastName() + "', '" +
                    values.getPhone() + "', '" +
                    values.getEmail() + "', '" +
                    values.getStreet() + "', '" +
                    values.getCity() + "', '" +
                    values.getState() + "', '" +
                    values.getZipCode() + "')");
            log.info("New customer added");
        } catch (SQLException e) {
            log.error("Customer was not added!", e);
        }
        return rs;
    }

    @Override
    public boolean bulkInsert(File csv) {
        String[] values = fileToArray(csv.getPath());
        log.info("Add new customers from .csv file to sales.customers table");
        try {
            for (int i = 0; i < values.length; i++) {
                statement.executeUpdate("INSERT INTO sales.customers " +
                        "(first_name, last_name, phone, email, street, city, state, zip_code)" +
                        " VALUES (" + values[i] + ")");
            }
            log.info("New customers from .csv file were added with success");
            return true;

        } catch (SQLException e) {
            log.error("Customers were not added!", e);
        }
        return false;
    }

    public void printResult(ResultSet result) {
        try {
            while (result.next()) { //read data from each row
                System.out.println(result.getString("customer_id") + ". " +
                        result.getString("first_name") + " " +
                        result.getString("last_name") + " " +
                        result.getString("phone") + " " +
                        result.getString("email") + " " +
                        result.getString("street") + " " +
                        result.getString("city") + " " +
                        result.getString("state") + " " +
                        result.getString("zip_code"));
            }
            log.info("Result is printed");
        } catch (SQLException e) {
            log.error("Result cannot be printed", e);
        }
    }

    public void printResultNoHardCore(ResultSet result) {
        try {
            while (result.next()) {//read data from each row
                for (int i = 1; i < result.getMetaData().getColumnCount() + 1; i++) {
                    System.out.print(result.getString(result.getMetaData().getColumnName(i)) + ", ");
                }
                System.out.println();
            }
            log.info("Result is printed");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}