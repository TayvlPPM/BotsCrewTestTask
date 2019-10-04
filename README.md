# BotsCrewTestTask

Test task for BotsCrew by Vitalii Doroshchuk

## Project configuration
1. Clone this repository.
2. Build project.

RDBMS chosen is MySQL
#### Backend
1. Change connecion parameters:
    ```
    String myUrl = "jdbc:mysql://localhost:3306/university?serverTimezone=UTC";
    conn = DriverManager.getConnection(myUrl, "<user>", "<password>");
    ```
2.Change column names in variable 'query' if needed.
