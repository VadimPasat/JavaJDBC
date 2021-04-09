import metadata.BrandsMetaData;
import metadata.CustomersMetaData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import connection.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;


public class Task1ReadData {
    BrandsMetaData printResult = new BrandsMetaData();
    CustomersMetaData printData = new CustomersMetaData();
    private static BrandsMetaData brands = null;
    private static CustomersMetaData customers = null;

    ResultSet result;

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
    public void testSelectAllBrandsTable() throws SQLException {
        result = brands.selectAll();
        assertThat("Result is not empty", result, notNullValue());
        printData.printResultNoHardCore(customers.selectAll());
    }

    @Test
    public void testSelectAllCustomersTable() throws SQLException {
        result = customers.selectAll();
        assertThat("Result is not empty", result, notNullValue());
        //printResult.printResult(customers.selectAll());
        printData.printResultNoHardCore(customers.selectAll());
    }

    @Test
    public void testSelectByIdBrandsTable() throws SQLException {
        int id = 1;
        int resultId = 0;
        result = brands.selectById(id);

        if (result.next()) {
            resultId = result.getInt("brand_id");
        }

        assertThat(format("ID value is [%s]", id),
                resultId,
                is(id));
        printData.printResultNoHardCore(customers.selectAll());
    }

    @Test
    public void testSelectByIdCustomersTable() throws SQLException {
        int id = 118;
        int resultId = 0;

        result = customers.selectById(118);

        if (result.next()) {
            resultId = result.getInt("customer_id");
        }

        assertThat(format("ID value is [%s]", id),
                resultId,
                is(id));
    }

    @Test
    public void testSelectBrandsTable() throws SQLException {
        String brandName = "Strider";
        String resultBrandName = "";

        result = brands.select("SELECT * FROM production.brands WHERE brand_name = '" + brandName + "'");

        if (result.next()) {
            resultBrandName = result.getString("brand_name");
        }

        assertThat(format("Brand name value is [%s]", brandName),
                resultBrandName,
                is(brandName));
        printData.printResultNoHardCore(customers.selectAll());
    }

    @Test
    public void testSelectCustomersTable() throws SQLException {
        String lastName = "Black";
        String resultLastName = "";

        result = customers.select("SELECT * FROM sales.customers WHERE last_name = '" + lastName + "'");

        if (result.next()) {
            resultLastName = result.getString("last_name");
        }

        assertThat(format("Brand name value is [%s]", lastName),
                resultLastName,
                is(lastName));
        // printData.printResult(customers.select("SELECT * FROM production.products WHERE product_id = '917'"));
        printData.printResultNoHardCore(customers.selectAll());
    }
}