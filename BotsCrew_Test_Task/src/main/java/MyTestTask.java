
import java.sql.*;
import java.util.Scanner;
import java.lang.String;

public class MyTestTask
{

    public static int menuReturn(){
        int menu;
        System.out.println("Return to main menu 1 or any other key for exit");
        Scanner menu_return = new Scanner(System.in);
        menu = menu_return.nextInt();
        if (menu!=1)menu=0;
        return menu;
    }

    public static void main(String[] args)
    {
        Connection conn = null;
        Statement st = null;
        ResultSet resultSet = null;
        try {
            String myUrl = "jdbc:mysql://localhost:3306/university?serverTimezone=UTC";
            conn = DriverManager.getConnection(myUrl, "root", "123434v555");
            st = conn.createStatement();
            int menu;
            String query;
            do {
                System.out.println("1.Who is head of department {department_name}");
                System.out.println("2.Show {department_name} statistic.");
                System.out.println("3.Show the average salary for department {department_name}");
                System.out.println("4.Show count of employee for {department_name}");
                System.out.println("5.Global search by {template}");
                System.out.println("Any other key for exit");
                System.out.println("Enter number of command ");
                Scanner myObj = new Scanner(System.in);
                menu = myObj.nextInt();
                System.out.println("what are we looking for?");
                Scanner input_word = new Scanner(System.in);
                String input = input_word.nextLine();
                switch (menu) {
                    case 1: {
                        query = "SELECT lectors.full_name " +
                                "FROM lectors INNER JOIN departments " +
                                "ON departments.head_of_department = lectors.id WHERE departments.name LIKE '" + input + "';";

                        resultSet = st.executeQuery(query);
                        while (resultSet.next())
                            System.out.println("Head of " + input + " department is "
                                    + resultSet.getString("full_name"));
                        menu=menuReturn();
                    }
                    break;
                    case 2:{
                        int assitant_count=0, associate_count=0, proffesor_count=0;
                        String basequery = "SELECT COUNT( l.id) " +
                                "FROM lectors AS l " +
                                "INNER JOIN lectors_has_departments AS ld " +
                                "ON l.id = ld.Lectors_idLectors " +
                                "INNER JOIN departments AS d " +
                                "ON d.id = ld.Departments_id_dep " +
                                "WHERE d.name LIKE '"
                                + input + "' AND l.degree = ";
                        query = basequery + "'assistant';";
                        resultSet = st.executeQuery(query);
                        while (resultSet.next())
                            assitant_count=resultSet.getInt(1);
                        query = basequery + "'associate professor';";
                        resultSet = st.executeQuery(query);
                        while (resultSet.next())
                            associate_count=resultSet.getInt(1);
                        query = basequery + "'professor';";
                        resultSet = st.executeQuery(query);
                        while (resultSet.next()){
                            proffesor_count=resultSet.getInt(1);}
                        System.out.println("assistants - "+assitant_count);
                        System.out.println("associate professors - "+associate_count);
                        System.out.println("professor - "+proffesor_count);
                        menu=menuReturn();
                    }
                    break;
                    case 3:
                    {
                        query = "SELECT AVG( l.salary) " +
                                "FROM lectors AS l " +
                                "INNER JOIN lectors_has_departments AS ld " +
                                "ON l.id = ld.Lectors_idLectors " +
                                "INNER JOIN departments AS d " +
                                "ON d.id = ld.Departments_id_dep " +
                                "WHERE d.name LIKE '"
                                + input + "';";
                        resultSet = st.executeQuery(query);
                        while (resultSet.next())
                            System.out.println("The average salary of " + input + " is "+resultSet.getDouble(1));
                        menu=menuReturn();
                    }
                    break;
                    case 4:
                    {
                        query = "SELECT COUNT(lectors_has_departments.Lectors_idLectors) " +
                                "FROM lectors_has_departments INNER JOIN departments" +
                                " ON departments.id = lectors_has_departments.Departments_id_dep WHERE departments.name LIKE '"
                                + input + "';";

                        resultSet = st.executeQuery(query);
                        while (resultSet.next())
                            System.out.println(resultSet.getInt(1));
                        menu=menuReturn();
                    }
                    break;
                    case 5:
                    {
                        query = "SELECT * " +
                                "FROM lectors " +
                                "WHERE id LIKE '%" + input + "%' " +
                                "OR full_name LIKE '%"+input+"%' " +
                                "OR degree LIKE '%"+input+"%' " +
                                "OR salary LIKE '%"+input+"%' ;";
                        resultSet = st.executeQuery(query);
                        ResultSetMetaData rsmd = resultSet.getMetaData();
                        int columnsNumber = rsmd.getColumnCount();
                        while (resultSet.next()) {
                            for (int i = 1; i <= columnsNumber; i++) {
                                System.out.println(resultSet.getString(i) + " " + rsmd.getColumnName(i));
                            }
                        }
                        query = "SELECT * " +
                                "FROM departments " +
                                "WHERE id LIKE '%" + input + "%' " +
                                "OR name LIKE '%"+input+"%' ;";
                        resultSet = st.executeQuery(query);
                        rsmd = resultSet.getMetaData();
                        columnsNumber = rsmd.getColumnCount();
                        while (resultSet.next()) {
                            System.out.println("Result: ");
                            for (int i = 1; i <= columnsNumber; i++) {
                                System.out.println(resultSet.getString(i) + " " + rsmd.getColumnName(i));
                            }
                        }
                        menu=menuReturn();
                    }
                    break;
                    default:
                        menu = -1;
                        break;
                }
            }
            while(menu > 0);
        }
        catch (SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* ignored */}
            }
        }
    }
}