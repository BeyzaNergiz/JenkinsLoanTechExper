  @end2end
  Feature: Data Testing

    Scenario: Add the desired data to the "device_tokens" table on the database in a single query.


    * Database connection is established.
    * UpdateQuery is prepared and executed to the device_tokens table.
    * Database connection is closed.

