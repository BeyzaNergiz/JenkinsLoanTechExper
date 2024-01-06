@end2end
Feature: Data Testing

  Scenario: Verify the number of users with "user id= 1" and  "is_read=0" in the "admin_notifications" table.

    * Database connection is established.
    * AdminNotificationsQuery is prepared.
    * The resultSet is verified.
    * Database connection is closed.
