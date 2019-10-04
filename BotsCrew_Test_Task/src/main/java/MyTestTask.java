
import java.sql.*;
import java.util.Scanner;
import java.lang.String;

public class MyTestTask
{

    public static void main(String[] args)
    {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            String myUrl = "jdbc:mysql://localhost:3306/university?createDatabaseIfNotExist=true&serverTimezone=UTC";
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
                Scanner depname = new Scanner(System.in);
                String name = depname.nextLine();
                switch (menu) {
                    case 1: {
                        query = "SELECT lectors.full_name " +
                                "FROM lectors INNER JOIN departments " +
                                "ON departments.head_of_department = lectors.id WHERE departments.name LIKE '" + name + "';";

                        rs = st.executeQuery(query);
                        while (rs.next())
                            System.out.println("Head of " + name + " department is "
                                    + rs.getString("full_name"));
                        System.out.println("Return to main menu 1 or 0 for exit");
                        depname = new Scanner(System.in);
                        menu = depname.nextInt();
                    }
                    break;
                    case 2:{
                        int a1=0,a2=0,a3=0;
                        query = "SELECT COUNT( l.id) " +
                                "FROM lectors AS l " +
                                "INNER JOIN lectors_has_departments AS ld " +
                                "ON l.id = ld.Lectors_idLectors " +
                                "INNER JOIN departments AS d " +
                                "ON d.id = ld.Departments_id_dep " +
                                "WHERE d.name LIKE '"
                                + name + "' AND l.degree = 'assistants';";
                        rs = st.executeQuery(query);
                        while (rs.next())
                            a1=rs.getInt(1);
                        System.out.println("Tuesday");
                        query = "SELECT COUNT( l.id) " +
                                "FROM lectors AS l " +
                                "INNER JOIN lectors_has_departments AS ld " +
                                "ON l.id = ld.Lectors_idLectors " +
                                "INNER JOIN departments AS d " +
                                "ON d.id = ld.Departments_id_dep " +
                                "WHERE d.name LIKE '"
                                + name + "' AND l.degree = 'associate professor';";
                        rs = st.executeQuery(query);
                        while (rs.next())
                            a2=rs.getInt(1);
                        query = "SELECT COUNT( l.id) " +
                                "FROM lectors AS l " +
                                "INNER JOIN lectors_has_departments AS ld " +
                                "ON l.id = ld.Lectors_idLectors " +
                                "INNER JOIN departments AS d " +
                                "ON d.id = ld.Departments_id_dep " +
                                "WHERE d.name LIKE '"
                                + name + "' AND l.degree = 'professors';";
                        while (rs.next()){
                            a3=rs.getInt(1);}
                        System.out.println("assistants - "+a1);
                        System.out.println("associate professors - "+a2);
                        System.out.println("professor - "+a3);
                        System.out.println("Return to main menu 1 or 0 for exit");
                        depname = new Scanner(System.in);
                        menu = depname.nextInt();
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
                                + name + "';";
                        rs = st.executeQuery(query);
                        while (rs.next())
                            System.out.println("The average salary of " + name + " is "+rs.getDouble(1));
                        System.out.println("Return to main menu 1 or 0 for exit");
                        depname = new Scanner(System.in);
                        menu = depname.nextInt();
                    }
                    break;
                    case 4:
                    {
                        query = "SELECT COUNT(lectors_has_departments.Lectors_idLectors) " +
                                "FROM lectors_has_departments INNER JOIN departments" +
                                " ON departments.id = lectors_has_departments.Departments_id_dep WHERE departments.name LIKE '"
                                + name + "';";

                        rs = st.executeQuery(query);
                        while (rs.next())
                            System.out.println(rs.getInt(1));
                        System.out.println("Return to main menu 1 or 0 for exit");
                        depname = new Scanner(System.in);
                        menu = depname.nextInt();
                    }
                    break;
                    case 5:
                    {
                        query = "SELECT * " +
                                "FROM lectors " +
                                "WHERE id LIKE '%" + name + "%' " +
                                "OR full_name LIKE '%"+name+"%' " +
                                "OR degree LIKE '%"+name+"%' " +
                                "OR salary LIKE '%"+name+"%' ;";
                        rs = st.executeQuery(query);
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int columnsNumber = rsmd.getColumnCount();
                        while (rs.next()) {
                            for (int i = 1; i <= columnsNumber; i++) {
                                System.out.println(rs.getString(i) + " " + rsmd.getColumnName(i));
                            }
                        }
                        query = "SELECT * " +
                                "FROM departments " +
                                "WHERE id LIKE '%" + name + "%' " +
                                "OR name LIKE '%"+name+"%' ;";
                        rs = st.executeQuery(query);
                        rsmd = rs.getMetaData();
                        columnsNumber = rsmd.getColumnCount();
                        while (rs.next()) {
                            System.out.println("Result: ");
                            for (int i = 1; i <= columnsNumber; i++) {
                                System.out.println(rs.getString(i) + " " + rsmd.getColumnName(i));
                            }
                        }
                        System.out.println("Return to main menu 1 or 0 for exit");
                        depname = new Scanner(System.in);
                        menu = depname.nextInt();
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