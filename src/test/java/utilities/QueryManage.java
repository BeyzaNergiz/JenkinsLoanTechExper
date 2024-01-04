package utilities;

public class QueryManage {

    private static String subscribersQuery="Select * from subscribers Where email Not Like '%a%';";

    public static String getSubscribersQuery() {
        return subscribersQuery;
    }

    public static String getDepositsSumAmount() {
        return depositsSumAmount;
    }

    private static String depositsSumAmount="Select Sum(final_amo) AS total_amount from deposits Where status=1 And created_at between '2023-01-01' And '2023-12-25';";
    private static String loansInsertQuery="Insert into loans (loan_number,id,user_id,plan_id)Values(?,?,?,?);";

    public static String getLoansInsertQuery() {
        return loansInsertQuery;
    }

    public static String getLoansDeleteQuery() {
        return loansDeleteQuery;
    }

    private static String loansDeleteQuery="Delete from loans Where loan_number=? ;";

    private static String adminsQuery = "SELECT remember_token FROM admins WHERE email = 'info@loantechexper.com';";

    public static String getAdminsQuery() {
        return adminsQuery;
    }

    private static String deviceTokensInsertQuery = "INSERT INTO device_tokens (id, user_id, is_app, token, created_at, updated_at) VALUES (?,?,?,?,?,?);";

    public static String getDeviceTokensInsertQuery() {
        return deviceTokensInsertQuery;
    }
    private static String categoriesInsertQuery = "INSERT INTO categories (id, name, description) VALUES (?,?,?);";

    public static String getCategoriesInsertQuery() {
        return categoriesInsertQuery;
    }
    //Select Sum(final_amo) AS total_amount from deposits Where method_currency='USD';
    private static String depositsSumAmountCurrency="SELECT g.currency,SUM(amount) AS total_amount FROM deposits d JOIN gateway_currencies g ON d.method_code = g.method_code GROUP BY g.currency;";

    public static String getDepositsSumAmountCurrency() {
        return depositsSumAmountCurrency;
    }
}
