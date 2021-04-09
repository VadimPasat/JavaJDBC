import ProjectJDBC.BrandsDao;
import ProjectJDBC.CustomersDAO;
import model.ProductionBrands;
import model.SalesCustomers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.DatabaseConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Task2UpdateTest {
    CustomersDAO printData = new CustomersDAO();
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
    public void testUpdateBrands() {
        String newData = "Co-op Cycles";
        int idToUpdate = 2;
        boolean isUpdated = false;

        ProductionBrands brandToUpdate = new ProductionBrands();
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

        SalesCustomers customerToUpdate = new SalesCustomers();
        customerToUpdate.setEmail(newData);

        isUpdated = customers.update(customerToUpdate, idToUpdate);

        assertThat("Customer data is updated", isUpdated, is(true));
    }
}