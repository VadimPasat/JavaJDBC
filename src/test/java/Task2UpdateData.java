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

public class Task2UpdateData {
    CustomersMetaData printData = new CustomersMetaData();
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
    public void testUpdateBrands() {
        String newData = "Co-op Cycles";
        int idToUpdate = 2;
        boolean isUpdated = false;

        ProductionBrandsData brandToUpdate = new ProductionBrandsData();
        brandToUpdate.setBrandName(newData);

        isUpdated = brands.update(brandToUpdate, idToUpdate);

        assertThat("Brand name is updated", isUpdated, is(true));
        printData.printResultNoHardCore(customers.selectAll());
    }

    @Test
    public void testUpdateCustomers() {
        boolean isUpdated = false;

        isUpdated = customers.update("UPDATE sales.customers SET last_name = 'Red' WHERE last_name = 'White' and customer_id>1200");

        assertThat("Customer data is updated", isUpdated, is(true));
    }

    @Test
    public void testUpdateCustomerById() {
        String newData = "joered@foo.com";
        int idToUpdate = 1111;
        boolean isUpdated = false;

        SalesCustomersData customerToUpdate = new SalesCustomersData();
        customerToUpdate.setEmail(newData);

        isUpdated = customers.update(customerToUpdate, idToUpdate);

        assertThat("Customer data is updated", isUpdated, is(true));
    }
}