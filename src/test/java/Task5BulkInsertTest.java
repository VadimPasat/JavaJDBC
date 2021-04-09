import ProjectJDBC.BrandsDao;
import ProjectJDBC.CustomersDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.DatabaseConnection;

import java.io.File;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Task5BulkInsertTest {
    private static BrandsDao brands = null;
    private static CustomersDAO customers = null;

    @BeforeAll
    static void setup() {
        DatabaseConnection.getInstance().getConnection();
        brands = new BrandsDao();
        customers = new CustomersDAO();
    }

    @AfterAll
    static public void tearDown() {
        DatabaseConnection.close();
    }

    @Test
    public void testInsertCustomersFromCSV() throws SQLException {
        boolean isUpdated = false;

        File file = new File( "src/main/resources/customers.csv");
        isUpdated = customers.bulkInsert(file);

        assertThat("Customers are added from .csv file", isUpdated, is(true));
    }

    @Test
    public void testInsertBrandsFromCSV() throws SQLException {
        boolean isUpdated = false;

        File file = new File( "src/main/resources/brands1.csv");
        isUpdated = brands.bulkInsert(file);

        assertThat("Brands are added from .csv file", isUpdated, is(true));
    }
}
