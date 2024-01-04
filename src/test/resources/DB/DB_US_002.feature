@end2end
Feature: Data Testing


  Scenario: Remember_token information verification test in the relevant email in the Admins table.

    * Database connection is established.
    * The query is prepared and executed to the Admins table.
    * The email information in the resultSet returned from the Admins table is verified.
    * Database connection is closed.