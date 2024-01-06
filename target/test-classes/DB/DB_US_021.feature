@end2end
Feature: Database testing
  Scenario: TC21 Verify the number of users with "is_read=1" and "user_id = 1" in the "admin_notifications" table.

    * Database connection is established.
    * adminNotificationsUS21Query is prepared.
    * Verify that the number of users.
    * Database connection is closed.
