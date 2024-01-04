package stepdefinitions;

import com.github.javafaker.Faker;
import io.cucumber.java.en.Given;
import org.junit.Assert;
import utilities.DBUtils;
import utilities.QueryManage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DB_Stepdefinitions {


    PreparedStatement preparedStatement;

    QueryManage queryManage = new QueryManage();

    String query;
    ResultSet rs;
    int id;
    int user_id;
    int plan_id;
    String loan_number;
    Faker faker=new Faker();
    int is_app;
    String token;
    String created_at;
    String updated_at;
    String name;
    String description;


    @Given("Database connection is established.")
    public void database_connection_is_established() {

        DBUtils.createConnection();

    }
    @Given("The query is prepared and executed to the Admins table.")
    public void the_query_is_prepared_and_executed_to_the_admins_table() throws SQLException {

        query = QueryManage.getAdminsQuery();
        rs = DBUtils.getStatement().executeQuery(query);


    }
    @Given("The email information in the resultSet returned from the Admins table is verified.")
    public void the_email_information_in_the_result_set_returned_from_the_admins_table_is_verified() throws SQLException {

        String expectedData= "1xUgfVUD1Ggx5CVz7mxLvcye8RXRbeFqSktSIkhya321TqDkLOsqhys4pnJv";

        rs.next();

        String actualData = rs.getString("remember_token");

        assertEquals(expectedData,actualData);

    }
    @Given("Database connection is closed.")
    public void database_connection_is_closed() {
    DBUtils.closeConnection();

    }


    @Given("Database baglantısı kurulur")
    public void database_baglantısı_kurulur() {
        DBUtils.createConnection();
    }

    @Given("subscribersQuery hazirlanir")
    public void subscribers_query_hazirlanir() throws SQLException {
        query = QueryManage.getSubscribersQuery();
        rs = DBUtils.getStatement().executeQuery(query);
    }

    @Given("data dogrulanır")
    public void data_dogrulanır() throws SQLException {

        List<String> aicerenEmail = new ArrayList<>();
        List<String> aicermeyenEmail = new ArrayList<>();

        while (rs.next()) {

            String email = rs.getString("email");
            if (email.contains("a")) {
                aicerenEmail.add(email);


            } else {
                aicermeyenEmail.add(email);
            }
            System.out.println(aicermeyenEmail);
        }
    }

    @Given("Database kapatilir")
    public void database_kapatilir() {
        DBUtils.closeConnection();
    }
    @Given("depositsAmountQuery hazirlanir")
    public void deposits_amount_query_hazirlanir() throws SQLException {
        query=QueryManage.getDepositsSumAmount();
        rs=DBUtils.getStatement().executeQuery(query);
    }
    @Given("totalAmountdata dogrulanır")
    public void total_amountdata_dogrulanır() throws SQLException {
        int expectedToatlAmount=91501709;
        rs.next();
        int actualToalAmount= rs.getInt("total_amount");
        System.out.println(expectedToatlAmount+"   ***   "+actualToalAmount);
        Assert.assertEquals(expectedToatlAmount,actualToalAmount);
    }
    @Given("loansInsertQuery hazirlanir")
    public void loans_ınsert_query_hazirlanir() throws SQLException {
        query=QueryManage.getLoansInsertQuery();
        loan_number=faker.internet().password();
        user_id=faker.number().numberBetween(10,1000);
        id=faker.number().numberBetween(700,1000);
        plan_id=faker.number().numberBetween(0,1);
        preparedStatement=DBUtils.getPraperedStatement(query);
        preparedStatement.setString(1,loan_number);
        preparedStatement.setInt(2,id);
        preparedStatement.setInt(3,user_id);
        preparedStatement.setInt(4,plan_id);
        System.out.println("**"+loan_number+"**");
        int rowCount=preparedStatement.executeUpdate();
        Assert.assertEquals(1,rowCount);

    }
    @Given("deleteLoansQuery hazırlanır")
    public void delete_loans_query_hazırlanır() throws SQLException {
        query=QueryManage.getLoansDeleteQuery();
        System.out.println(loan_number);
        preparedStatement=DBUtils.getPraperedStatement(query);
        preparedStatement.setString(1,loan_number);

    }
    @Given("Datanın silindigi dogrulanır")
    public void datanın_silindigi_dogrulanır() throws SQLException {
        int rowCount=preparedStatement.executeUpdate();
        Assert.assertEquals(1,rowCount);
    }
    @Given("UpdateQuery is prepared and executed to the device_tokens table.")
    public void update_query_is_prepared_and_executed_to_the_divice_tokens_table() throws SQLException {

        query = QueryManage.getDeviceTokensInsertQuery();
        id = 14;
        user_id = 0;
        is_app = 5;
        token = "n5rlvicckd0x";
        created_at = null;
        updated_at = null;

        preparedStatement=DBUtils.getPraperedStatement(query);
        preparedStatement.setInt(1,id);
        preparedStatement.setInt(2,user_id);
        preparedStatement.setInt(3,is_app);
        preparedStatement.setString(4,token);
        preparedStatement.setString(5,created_at);
        preparedStatement.setString(6,updated_at);

        int rowCount=preparedStatement.executeUpdate();
        Assert.assertEquals(1,rowCount);

    }

    @Given("UpdateQuery is prepared and executed to the Catagories table.")
    public void update_query_is_prepared_and_executed_to_the_catagories_table() throws SQLException {

        query = QueryManage.getCategoriesInsertQuery();
        id = 704;
        name = "Beyza Nergiz";
        description = "Add data to the category table test";
        System.out.println("id : "+ id);
        System.out.println("name : "+ name);
        System.out.println("description : "+ description);


        preparedStatement=DBUtils.getPraperedStatement(query);
        preparedStatement.setInt(1,id);
        preparedStatement.setString(2,name);
        preparedStatement.setString(3,description);


    }
    @Given("Verify that the data has been added.")
    public void verify_that_the_data_has_been_added() throws SQLException {
        int rowCount=preparedStatement.executeUpdate();
        Assert.assertEquals(1,rowCount);
        System.out.println(rowCount + " satirda update islemi gerceklesti.");
    }

    @Given("The query is prepared and executed to the deposits table.")
    public void the_query_is_prepared_and_executed_to_the_deposits_table() throws SQLException {
        query=QueryManage.getDepositsSumAmountCurrency();
        rs=DBUtils.getStatement().executeQuery(query);
    }
    @Given("Total deposit is calculated.")
    public void total_deposit_is_calculated() throws SQLException {
        int expectedTotalAmount = 182030;
        rs.next();
        int actualToalAmount= rs.getInt("total_amount");
        System.out.println(expectedTotalAmount+"   ***   "+actualToalAmount);
        Assert.assertEquals(expectedTotalAmount,actualToalAmount);
    }


}
