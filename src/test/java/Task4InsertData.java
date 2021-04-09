import metadata.BrandsMetaData;
import metadata.CustomersMetaData;
import updatecolums.ProductionBrandsData;
import updatecolums.SalesCustomersData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import connection.DatabaseConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Task4InsertData {
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
    public void testInsertIntoBrandsTable() {
        int rowsUpdated = 0;

        ProductionBrandsData newBrand = new ProductionBrandsData();
        newBrand.setBrandName("Shimano");

        rowsUpdated = brands.insert(newBrand);

        assertThat("Brand is added", rowsUpdated > 0, is(true));
    }


    @Test
    public void testInsertIntoCustomersTable() {
        int rowsUpdated = 0;

        SalesCustomersData newCustomer = new SalesCustomersData();
        newCustomer.setFirstName("Joe");
        newCustomer.setLastName("Black");
        newCustomer.setPhone("null");
        newCustomer.setEmail("olga@foo.com");
        newCustomer.setStreet("25 Avenue");
        newCustomer.setCity("New York");
        newCustomer.setState("NY");
        newCustomer.setZipCode("22222");

        rowsUpdated = customers.insert(newCustomer);

        assertThat("Customer is added", rowsUpdated > 0, is(true));
    }
}
