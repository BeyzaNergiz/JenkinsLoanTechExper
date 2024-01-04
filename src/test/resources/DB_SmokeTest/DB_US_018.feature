  @end2end
  Feature: Data Testing

    Scenario: Add only the data containing "id, name, description" to the Catagories table.

      * Database connection is established.
      * UpdateQuery is prepared and executed to the Catagories table.
      * Verify that the data has been added.
      * Database connection is closed.