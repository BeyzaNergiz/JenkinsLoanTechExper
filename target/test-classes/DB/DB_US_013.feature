@end2end
Feature: Database testing
  Scenario: TC13: Enter data to the "cron_job_logs" table and verify that it is added to the table.

    * Database connection is established.
    * cronJobLogsInsertQuery is prepared.
    * Verify that the data has been added to the table.
    * Database connection is closed.

