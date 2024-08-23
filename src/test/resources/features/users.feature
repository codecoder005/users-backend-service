Feature: Users API should work
  Scenario: users endpoints should be reachable
    Given client want to check api v1 users is up and running
    When the client calls api v1 users ping endpoint
    Then the response status should be 200
#    And client receives PingAPIResponse

