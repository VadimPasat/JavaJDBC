import metadata.BrandsMetaData;
import metadata.CustomersMetaData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import connection.DatabaseConnection;

import java.io.File;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Task5BulkInsertData {
    private static BrandsMetaData brands = null;
    private static CustomersMetaData customers = null;

    @BeforeAll
    static void setup() {
        DatabaseConnection.getInstance().getConnection();
        brands = new BrandsMetaData();
        customers = new CustomersMetaData();
    }

    @AfterAll
    static public void tearDown() {
        DatabaseConnection.close();
    }

    @Test
    public void testInsertCustomersFromCSV() throws SQLException {
        boolean isUpdated = false;

        File file = new File( "src/main/resources/CustomersForUpdate.csv");
        isUpdated = customers.bulkInsert(file);

        assertThat("Customers are added from .csv file", isUpdated, is(true));
    }

    @Test
    public void testInsertBrandsFromCSV() throws SQLException {
        boolean isUpdated = false;

        File file = new File( "src/main/resources/BrandsForUpdate.csv");
        isUpdated = brands.bulkInsert(file);

        assertThat("Brands are added from .csv file", isUpdated, is(true));
    }
}
