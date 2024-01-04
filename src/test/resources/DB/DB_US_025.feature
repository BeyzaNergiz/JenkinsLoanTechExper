  @end2end
  Feature: Data Testing

    Scenario: "currency=USD" Calculate the Total Amount of Deposits from "gateway_currencies" table


    * Database connection is established.
    * The query is prepared and executed to the deposits table.
    * Total deposit is calculated.
    * Database connection is closed.
