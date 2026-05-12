Feature: Login Flow

  Scenario: Successful login with valid credentials
    Given I am on the login page
    When I enter username "student" and password "Password123"
    And I click the login button
    Then I should see the message "Logged In Successfully"

  Scenario: Failed login with wrong password
    Given I am on the login page
    When I enter username "student" and password "wrongpassword"
    And I click the login button
    Then I should see an error "Your password is invalid!"

  Scenario: Failed login with wrong username
    Given I am on the login page
    When I enter username "wronguser" and password "Password123"
    And I click the login button
    Then I should see an error "Your username is invalid!"
