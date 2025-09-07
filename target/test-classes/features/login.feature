Feature: Login functionality


  Scenario Outline: Login with valid credentials
    Given the user is on the login page
    When the user logs in with username "<username>" and password "<password>"
    Then the username "<username>" should be visible

    Examples:
      | username             | password         |
      | demouser             | testingisfun99   |
      | fav_user             | testingisfun99   |
      | image_not_loading_user | testingisfun99 |
      | existing_orders_user | testingisfun99   |

 
  Scenario: Locked user should see locked message
    Given the user is on the login page
    When the user logs in with username "locked_user" and password "testingisfun99"
    Then a locked user message should be shown


  Scenario: Login with empty username should show message
    Given the user is on the login page
    When the user logs in with username "" and password "testingisfun99"
    Then an error message for empty username should be shown


  Scenario: Login with empty password should show message
    Given the user is on the login page
    When the user logs in with username "demouser" and password ""
    Then an error message for empty password should be shown
