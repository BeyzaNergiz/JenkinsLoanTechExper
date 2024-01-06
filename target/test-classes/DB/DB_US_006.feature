@end2end

Feature: Data Testing

  Scenario: In the "Support_messages" table in the database,
  verify the "support_ticket_id" of the data whose "message" information is "Tickett".



    * Database connection is established.
    * supportMessagesQuery is prepared and executed.
    * Verify the support_ticket_id information Results are obtained.
    * Database connection is closed.