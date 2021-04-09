import metadata.BrandsMetaData;
import metadata.CustomersMetaData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import connection.DatabaseConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Task3DeleteData {
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
    public void testDeleteBrand() {
        boolean isDeleted = false;
        String dataToDelete = "Giant";

        isDeleted = brands.delete("brand_name = '" + dataToDelete + "'");

        assertThat("Brand record is deleted", isDeleted, is(true));
    }

    @Test
    public void testDeleteCustomers() {
        boolean isDeleted = false;

        isDeleted = customers.delete("customer_id >= '2526' ");

        assertThat("Customer is deleted", isDeleted, is(true));
    }

    @Test
    public void testDeleteBrandById() {
        boolean isDeleted = false;
        int idToDelete = 917;

        isDeleted = brands.deleteById(idToDelete);

        assertThat("Brand record is deleted", isDeleted, is(true));
    }

    @Test
    public void testDeleteCustomersById() {
        boolean isDeleted = false;
        int idToDelete = 1400;

        isDeleted = customers.deleteById(idToDelete);

        assertThat("Customer is deleted", isDeleted, is(true));
    }
}