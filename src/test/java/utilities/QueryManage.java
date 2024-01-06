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

    private static String supportTicketsQuery ="SELECT subject FROM support_tickets WHERE ticket LIKE '4%';";

    public static String getSupportTicketsQuery() {
        return supportTicketsQuery;
    }



    // cagla Ouery Manage satir araligi 200-250
    // us_06 icin
    private static String supportMessagesQuery="select support_ticket_id from support_messages Where message='Tickett';";
    public static String getsupportMessagesQuery() {
        return supportMessagesQuery;
    }
    // us_14 icin
    private static String gatewaysQuery = "select code from gateways order by code Desc Limit 5;";
    public  static String getGatewaysQuery() {return gatewaysQuery; }
    // us_22 icin
    private static String   adminPasswordResetsQuery= "Insert into admin_password_resets (id,email,token,status,created_at) Values(?,?,?,?,?);";
    public static String getAdminPasswordResetsQuery() { return adminPasswordResetsQuery; }
    private static String Query22 = "UPDATE admin_password_resets SET status = 1 WHERE email = 'ıgnacio.bernier@hotmail.com' AND status = 0";
    public static String getQuery22() {return Query22; }
    // us_29 icin
    private static String insertupdate_Logs = "Insert into update_logs (id,version,update_log,created_at) values(451, 'Windows 10', 'macOS 11', null,null);";
    public static String getInsertupdate_Logs() {return insertupdate_Logs; }
    public static String deleteUpdateLog = "DELETE FROM update_logs WHERE id = 452;";
    public static String getDeleteUpdateLog() {return deleteUpdateLog; }


    // Aysegul 50 - 100

    public static String getCronSchedulesQuery() { return CronSchedulesQuery; }
    private static String CronSchedulesQuery = "SELECT name FROM cron_schedules LIMIT 2;";


    public static String getAdminNotificationsQuery() { return AdminNotificationsQuery; }
    private static String AdminNotificationsQuery = "SELECT COUNT(*) AS userCount FROM admin_notifications WHERE user_id = 1 AND is_read = 0;";


    public static String getusersTableQuery() { return usersTableQuery;  }
    private static String usersTableQuery = "SELECT * FROM users ORDER BY lastname DESC, firstname DESC LIMIT 1;";


    public static String getInstallmentsTableQuery() { return InstallmentsTableQuery; }
    private static String InstallmentsTableQuery ="SELECT loan_id, SUM(delay_charge) AS total_delay_charge FROM installments WHERE loan_id = 1 GROUP BY loan_id";


    // Mustafa 100-150
    private static  String remarkSumAmountQuery="select remark, sum(amount) as total_amount from transactions group by remark having (total_amount)>1000;";

    public static String getRemarkSumAmountQuery() { return remarkSumAmountQuery; }

    private static String adminNotificationQuery = "select COUNT(*) from admin_notifications where user_id='2' AND id > '20';";

    public static String getAdminNotificationQuery() { return adminNotificationQuery; }

    private static String depositAmountTrx = "select * from deposits where trx='4GC9SMZUS69S';";

    public static String getDepositAmountTrx() { return depositAmountTrx; }

    private static String loanPlansFirstNames = "select name, delay_value, fixed_charge, percent_charge from loan_plans LIMIT 3;";

    public static String getLoanPlansFirstNames() { return loanPlansFirstNames; }

    //*********************AYSE********************
    //US05
    private static String adminResetInsertQuery="INSERT INTO admin_password_resets VALUES (?,?, ?, ?, ?);";
    public static String getAdminResetInsertQuery() {return adminResetInsertQuery;}
    //US13
    private static String cronJobLogsInsertQuery="INSERT INTO cron_job_logs (cron_job_id) VALUES (?);";
    public static String getCronJobLogsInsertQuery() {return cronJobLogsInsertQuery;}
    //US21
    private static String adminNotificationsUS21Query="SELECT COUNT(*) AS userCount FROM admin_notifications WHERE user_id = 1 AND is_read = 1;";
    public static String getAdminNotificationsUS21Query() {return adminNotificationsUS21Query;}
    //US28
    private static String updateLogsinsertQuery="INSERT INTO update_logs (id,version) VALUES (600, \"Linux\");";
    public static String getUpdateLogsinsertQuery() {return updateLogsinsertQuery;}
    private static String updateLogsUpdateQuery="UPDATE update_logs SET update_log=\"merhaba\" WHERE version=\"Linux\" and id=600;";
    public static String getUpdateLogsUpdateQuery() {return updateLogsUpdateQuery;}


    // Bülent
    private static String usersQueryUS08 = "SELECT firstname, lastname FROM users WHERE country_code <>\"TR\" and id=\"11\";";
    public static String getUsersQueryUS08() {
        return usersQueryUS08;
    }

    // SELECT user_id, user_ip, city FROM user_logins group by city;

    private static String userLoginsQueryUS16 = "SELECT user_id, user_ip, city FROM user_logins group by city;";
    public static String getuserLoginsQueryUS16() {
        return userLoginsQueryUS16;
    }

    private static String admin_notificationsQueryUS24 = "UPDATE admin_notifications SET is_read = 1 WHERE id = 1200 and is_read=0;";
    public static String get_admin_notificationsQueryUS24() {
        return admin_notificationsQueryUS24;
    }
    private static String admin_notificationsQueryVerifyUS24 = "SELECT * from admin_notifications WHERE id = 1200;";
    public static String get_admin_notificationsQueryVerifyUS24() {
        return admin_notificationsQueryVerifyUS24;
    }

    private static String admin_notificationsUndoQueryUS24 = "UPDATE admin_notifications SET is_read = 0 WHERE id = 1200 and is_read=1;";
    public static String get_admin_notificationsUndoQueryUS24() {
        return admin_notificationsUndoQueryUS24;
    }

    private static String support_attachmentsQueryUS30 = "UPDATE support_attachments SET attachment = NULL WHERE support_message_id = 522;";
    public static String get_support_attachmentsQueryUS30() {
        return support_attachmentsQueryUS30;
    }
    private static String support_attachmentsQueryVerifyUS30 = "SELECT * from support_attachments WHERE support_message_id = 522;";
    public static String get_support_attachmentsQueryVerifyUS30() {
        return support_attachmentsQueryVerifyUS30;
    }

    private static String support_attachmentsUndoQueryUS30 = "UPDATE support_attachments SET attachment = \"65919c93cddd81704041619.jpg\" WHERE support_message_id = 522;";
    public static String get_support_attachmentsUndoQueryUS30() {
        return support_attachmentsUndoQueryUS30;
    }

}


