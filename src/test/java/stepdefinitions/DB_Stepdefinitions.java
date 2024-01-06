package stepdefinitions;

import io.cucumber.java.en.Given;
import utilities.DBReusableMethods;
import utilities.QueryManage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.github.javafaker.Faker;

import org.junit.Assert;
import utilities.DBUtils;



import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DB_Stepdefinitions {


    PreparedStatement preparedStatement;

    QueryManage queryManage = new QueryManage();

    String query;
    ResultSet rs;
    int id;
    int user_id;
    int plan_id;
    String loan_number;
    Faker faker = new Faker();
    int is_app;
    String token;
    String created_at;
    String updated_at;
    String name;
    String description;

    String e_mail; // cagla
    String version; // cagla
    String updateLog; // cagla


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

        String expectedData = "1xUgfVUD1Ggx5CVz7mxLvcye8RXRbeFqSktSIkhya321TqDkLOsqhys4pnJv";

        rs.next();

        String actualData = rs.getString("remember_token");

        assertEquals(expectedData, actualData);

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
        query = QueryManage.getDepositsSumAmount();
        rs = DBUtils.getStatement().executeQuery(query);
    }

    @Given("totalAmountdata dogrulanır")
    public void total_amountdata_dogrulanır() throws SQLException {
        int expectedToatlAmount = 91501709;
        rs.next();
        int actualToalAmount = rs.getInt("total_amount");
        System.out.println(expectedToatlAmount + "   ***   " + actualToalAmount);
        Assert.assertEquals(expectedToatlAmount, actualToalAmount);
    }

    @Given("loansInsertQuery hazirlanir")
    public void loans_ınsert_query_hazirlanir() throws SQLException {
        query = QueryManage.getLoansInsertQuery();
        loan_number = faker.internet().password();
        user_id = faker.number().numberBetween(10, 1000);
        id = faker.number().numberBetween(700, 1000);
        plan_id = faker.number().numberBetween(0, 1);
        preparedStatement = DBUtils.getPraperedStatement(query);
        preparedStatement.setString(1, loan_number);
        preparedStatement.setInt(2, id);
        preparedStatement.setInt(3, user_id);
        preparedStatement.setInt(4, plan_id);
        System.out.println("**" + loan_number + "**");
        int rowCount = preparedStatement.executeUpdate();
        Assert.assertEquals(1, rowCount);

    }

    @Given("deleteLoansQuery hazırlanır")
    public void delete_loans_query_hazırlanır() throws SQLException {
        query = QueryManage.getLoansDeleteQuery();
        System.out.println(loan_number);
        preparedStatement = DBUtils.getPraperedStatement(query);
        preparedStatement.setString(1, loan_number);

    }

    @Given("Datanın silindigi dogrulanır")
    public void datanın_silindigi_dogrulanır() throws SQLException {
        int rowCount = preparedStatement.executeUpdate();
        Assert.assertEquals(1, rowCount);
    }

    @Given("UpdateQuery is prepared and executed to the device_tokens table.")
    public void update_query_is_prepared_and_executed_to_the_divice_tokens_table() throws SQLException {

        query = QueryManage.getDeviceTokensInsertQuery();
        id = 18;
        user_id = 0;
        is_app = 5;
        token = "n5rlvicckd0x";
        created_at = null;
        updated_at = null;

        preparedStatement = DBUtils.getPraperedStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, user_id);
        preparedStatement.setInt(3, is_app);
        preparedStatement.setString(4, token);
        preparedStatement.setString(5, created_at);
        preparedStatement.setString(6, updated_at);

        int rowCount = preparedStatement.executeUpdate();
        Assert.assertEquals(1, rowCount);

    }

    @Given("UpdateQuery is prepared and executed to the Catagories table.")
    public void update_query_is_prepared_and_executed_to_the_catagories_table() throws SQLException {

        query = QueryManage.getCategoriesInsertQuery();
        id = 708;
        name = "Beyza Nergiz";
        description = "Add data to the category table test";
        System.out.println("id : " + id);
        System.out.println("name : " + name);
        System.out.println("description : " + description);


        preparedStatement = DBUtils.getPraperedStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, description);


    }

    @Given("Verify that the data has been added.")
    public void verify_that_the_data_has_been_added() throws SQLException {
        int rowCount = preparedStatement.executeUpdate();
        Assert.assertEquals(1, rowCount);
        System.out.println(rowCount + " satirda update islemi gerceklesti.");
    }

    @Given("The query is prepared and executed to the deposits table.")
    public void the_query_is_prepared_and_executed_to_the_deposits_table() throws SQLException {
        query = QueryManage.getDepositsSumAmountCurrency();
        rs = DBUtils.getStatement().executeQuery(query);
    }

    @Given("Total deposit is calculated.")
    public void total_deposit_is_calculated() throws SQLException {
        int expectedTotalAmount = 182030;
        rs.next();
        int actualToalAmount = rs.getInt("total_amount");
        System.out.println(expectedTotalAmount + "   ***   " + actualToalAmount);
        Assert.assertEquals(expectedTotalAmount, actualToalAmount);
    }


    @Given("supportMessagesQuery is prepared and executed.")
    public void support_messages_query_is_prepared_and_executed() throws SQLException {
        query = QueryManage.getsupportMessagesQuery();
        rs = DBUtils.getStatement().executeQuery(query);
    }

    @Given("Verify the support_ticket_id information Results are obtained.")
    public void verify_the_support_ticket_id_information_results_are_obtained() throws SQLException {
        rs.next();
        int support_ticket_id = rs.getInt("support_ticket_id");
        int exp_s_t_id = 2;
        assertEquals(exp_s_t_id, support_ticket_id);
    }

    // CAGLA DB_Stepdefinitions 850 - 1050 SATIR ARALIGI  US_14 ICIN
    @Given("GatewaysQuery is prepared and executed")
    public void gateways_query_is_prepared_and_executed() throws SQLException {
        query = QueryManage.getGatewaysQuery();
        rs = DBUtils.getStatement().executeQuery(query);
    }

    @Given("Verify the  List the {int} highest {string} values")
    public void verify_the_list_the_highest_values(Integer int1, String column) throws SQLException {
        List<Integer> codes = new ArrayList<>();
        List<Integer> expcodes = new ArrayList<>();
        expcodes.add(1003);
        expcodes.add(1002);
        expcodes.add(1001);
        expcodes.add(1000);
        expcodes.add(509);

        while (rs.next()) {
            int code = rs.getInt("code");
            codes.add(code);
            for (int i = 0; i < codes.size(); i++) {
                assertEquals(expcodes.get(i), codes.get(i));
            }
        }
    }

    @Given("admin_password_resets_Query is prepared and executed.")
    public void admin_password_resets_query_is_prepared_and_executed() throws SQLException {
        query = QueryManage.getAdminPasswordResetsQuery();
        id = faker.number().numberBetween(100, 1000);
        e_mail = faker.internet().emailAddress();
        token = faker.internet().password();
        Date created_at = Date.valueOf(LocalDate.now());
        // System.out.println(id +" "+e_mail+" "+token);
        preparedStatement = DBUtils.getPraperedStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, e_mail);
        preparedStatement.setString(3, token);
        preparedStatement.setInt(4, 0);
        preparedStatement.setDate(5, created_at);
    }

    @Given("Data Results are obtained.")
    public void data_results_are_obtained() throws SQLException {
        int rowCount = preparedStatement.executeUpdate();
        assertEquals(1, rowCount);
    }

    @Given("e_mail is prepared for admin_password_resets query and status is updated.")
    public void e_mail_is_prepared_for_admin_password_resets_query_and_status_is_updated() throws SQLException {
        query = QueryManage.getQuery22();
        preparedStatement = DBUtils.getPraperedStatement(query);
        preparedStatement.setInt(1, 1);
        preparedStatement.setString(2, e_mail);
    }

    @Given("Data Results are validate.")
    public void data_results_are_validate() throws SQLException {
        int result = preparedStatement.executeUpdate();
        int verify = 0;
        if (result > 0) {
            verify++;
        }
        assertEquals(1, verify);
    }

    @Given("update_log tables insert Query prepared")
    public void update_log_tables_insert_query_prepared() throws SQLException {
        query = QueryManage.getInsertupdate_Logs();
        preparedStatement = DBUtils.getPraperedStatement(query);
        id = faker.number().numberBetween(100, 500);
        version = faker.options().option("Windows 10", "macOS 11");
        updateLog = faker.lorem().sentence(1);

        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, version);
        preparedStatement.setString(3, updateLog);
        preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));
    }

    @Given("Prepares update_log Query to be deleted")
    public void prepares_update_log_query_to_be_deleted() throws SQLException {
        query = QueryManage.getDeleteUpdateLog();
        preparedStatement = DBUtils.getPraperedStatement(query);
        preparedStatement.setInt(1, id);
    }



    @Given("CronSchedulesQuery is prepared.")
    public void cron_schedules_query_is_prepared() throws SQLException {
        query = QueryManage.getCronSchedulesQuery();
        rs = DBUtils.getStatement().executeQuery(query);
    }

    @Given("The name information in the resultSet returned from the CronSchedules table is verified.")
    public void the_name_information_in_the_result_set_returned_from_the_cron_schedules_table_is_verified() throws SQLException {
        while (rs.next()) {

            String name = rs.getString("name");

            System.out.println(name);
        }
    }

    @Given("AdminNotificationsQuery is prepared.")
    public void admin_notifications_query_is_prepared() throws SQLException {
        query = QueryManage.getAdminNotificationsQuery();
        rs = DBUtils.getStatement().executeQuery(query);
    }

    @Given("The resultSet is verified.")
    public void the_result_set_is_verified() throws SQLException {
        int expectedUserCount = 1;
        rs.next();
        int actualUserCount = rs.getInt(1);
        assertEquals(expectedUserCount, actualUserCount);
    }

    @Given("usersTableQuery is prepared.")
    public void users_table_query_is_prepared() throws SQLException {
        query = QueryManage.getusersTableQuery();
        rs = DBUtils.getStatement().executeQuery(query);

    }

    @Given("The usersResultSet is verified.")
    public void the_users_result_set_is_verified() throws SQLException {
        if (rs.next()) {
            String firstLastName = rs.getString("lastname");
            // Assert that the first last name is null
            assert firstLastName == null : "First last name should be null";
            // Alternatively, you can use JUnit assertions
            // Assert.assertNull(firstLastName);
            System.out.println("Assertion passed");
        } else {
            System.out.println("No result found");
        }
    }


    @Given("InstallmentsTableQuery is prepared.")
    public void ınstallments_table_query_is_prepared() throws SQLException {
        query = QueryManage.getInstallmentsTableQuery();
        rs = DBUtils.getStatement().executeQuery(query);
    }

    @Given("The InstallmentresultSet is verified.")
    public void the_ınstallmentresult_set_is_verified() throws SQLException {
        int expectedInstallment = 10;
        rs.next();
        int actualInstallment = rs.getInt(2);
        assertEquals(expectedInstallment, actualInstallment);
    }




    @Given("adminResetInsertQuery is prepared.")
    public void admin_reset_ınsert_query_is_prepared() throws SQLException {
        query = QueryManage.getAdminResetInsertQuery();
        // id email token status created_at
        preparedStatement = DBUtils.getPraperedStatement(query);
        preparedStatement.setInt(1, 3000);
        preparedStatement.setString(2, "email");
        preparedStatement.setString(3, "dffgdfg");
        preparedStatement.setInt(4, 2);
        preparedStatement.setString(5, "2023-05-16");
    }

    @Given("Verify that the multiple data has been added.")
    public void verify_that_the_multiple_data_has_been_added() throws SQLException {
        int rowCount = preparedStatement.executeUpdate();
        Assert.assertEquals(1, rowCount);
        System.out.println(rowCount + " satirda update islemi gerceklesti.");
    }

    //-----------U13
    @Given("cronJobLogsInsertQuery is prepared.")
    public void cron_job_logs_ınsert_query_is_prepared() throws SQLException {
        query = QueryManage.getCronJobLogsInsertQuery();
        preparedStatement = DBUtils.getPraperedStatement(query);
        //id bigint(20) UN AI PK // cron_job_id int(11)
        preparedStatement.setInt(1, 250);
    }

    @Given("Verify that the data has been added to the table.")
    public void verify_that_the_data_has_been_added_to_the_table() throws SQLException {
        int rowCount = preparedStatement.executeUpdate();
        Assert.assertEquals(1, rowCount);
        System.out.println(rowCount + " satirda update islemi gerceklesti.");
    }

    //--------US21
    @Given("adminNotificationsUS21Query is prepared.")
    public void admin_notifications_us21query_is_prepared() throws SQLException {
        query = QueryManage.getAdminNotificationsUS21Query();
        rs = DBUtils.getStatement().executeQuery(query);
    }

    @Given("Verify that the number of users.")
    public void verify_that_the_number_of_users() throws SQLException {
        int expectedUserCount = 13;
        rs.next();
        int actualUserCount = rs.getInt(1);
        assertEquals(expectedUserCount, actualUserCount);
        System.out.println("kullanıcı sayısı:" + actualUserCount);
        System.out.println("beklenen kullanıcı sayısı:" + expectedUserCount);
    }

    //--------US28
    @Given("updateLogsinsertQuery is prepared.")
    public void update_logsinsert_query_is_prepared() throws SQLException {
        query = QueryManage.getUpdateLogsinsertQuery();
        preparedStatement = DBUtils.getPraperedStatement(query);
        int rowCount = preparedStatement.executeUpdate();
        Assert.assertEquals(1, rowCount);
        System.out.println(rowCount + " satirda insert islemi gerceklesti.");
    }

    @Given("updateLogsUpdateQuery is prepared.")
    public void update_logs_update_query_is_prepared() throws SQLException {
        query = QueryManage.getUpdateLogsUpdateQuery();
        preparedStatement = DBUtils.getPraperedStatement(query);
        int rowCount = preparedStatement.executeUpdate();
        Assert.assertEquals(1, rowCount);
        System.out.println(rowCount + " satirda update islemi gerceklesti.");
    }

    @Given("Support tickets query hazirlanir")
    public void support_tickets_query_hazirlanir() throws SQLException {
        query = QueryManage.getSupportTicketsQuery();
        rs = DBUtils.getStatement().executeQuery(query);
    }

    @Given("Support tickets data dogrulanir")
    public void support_tickets_data_dogrulanir() throws SQLException {

        // ResultSet içindeki verileri doğrula
        while (rs.next()) {
            // Her satir icin istedigin doğrulama islemlerini yap
            // Ornegin, subject kolonunun bos olmadigini kontrol et
            assertNotNull(rs.getString("subject"));
        }

    }
}