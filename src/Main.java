import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Model.Student;
import org.postgresql.ds.PGSimpleDataSource;

public class Main {
    private static JdbcImple jdbc;
    static Scanner scanner;

    public static void main(String[] args) throws SQLException {

        jdbc = new JdbcImple();
        Student student = new Student();
        scanner = new Scanner(System.in);
        int option;
        do{
            System.out.println("----------------------------Student System-----------------------");
            System.out.println("1.  Insert Student to System");
            System.out.println("2.  Update Student Information");
            System.out.println("3.  Search Student Information");
            System.out.println("4.  Delete Student Information");
            System.out.println("5.  Show Student Information");
            System.out.println("6.  Exit program");
            System.out.print(">>> Choose option from 1 - 6 : ");
            option=scanner.nextInt();
            switch(option) {
                case 1 -> {
                    System.out.println("---------------------------Insert Student to System---------------------------");
                    System.out.print("Enter ID : "); student.setId(scanner.nextInt());scanner.nextLine();
                    System.out.print("Enter name : "); student.setName(scanner.nextLine());
                    System.out.print("Enter address : ");  student.setAddress(scanner.nextLine());
                    insertStudent(student);
                    System.out.println("Successfully Added..!");
                }
                case 2 -> {
                    System.out.println("---------------------------Update Student Information---------------------------");
//                    int idSearch;
//                    System.out.println("Enter id to search : "); idSearch=scanner.nextInt();
                    updateStudent(student);
                }
                case 3 -> {
                    System.out.println("--------------------------- Search Student Information---------------------------");
                    searchStudent(student);
                }
                case 4 -> {
                    System.out.println("---------------------------Delete Student Information---------------------------");
                    deleteStudent(student);
                }
                case 5 -> {
                    System.out.println("---------------------------Show Student Information---------------------------");
                    showStudent();
                }
                case 6 -> {
                    System.out.println("---------------------------THANK YOU SO MUCH FOR USING MY CONSOLE APP---------------------------");

                }
            }
        }while(option!=6);
        System.out.println("Invalid number, please input again");
    }
    private static void deleteStudent(Student student){
        try(Connection conn = jdbc.dataSource().getConnection()){

            String deleteSql = "DELETE FROM student WHERE id = ?;";

            PreparedStatement pS = conn.prepareStatement(deleteSql);

            pS.setInt(1,student.getId());


            int count = pS.executeUpdate();
            System.out.println(count);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void searchStudent(Student student){
        try(Connection conn = jdbc.dataSource().getConnection()){
            //1. Create SQL statement object
            String searchSql = "SELECT *FROM student WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(searchSql);
            //2.
            ResultSet resultSet = statement.executeQuery();
            //3.
            List<Student> studentList = new ArrayList<>();
            while(resultSet.next()){
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
            }
            System.out.println(studentList);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void updateStudent(Student student){
        try(Connection conn = jdbc.dataSource().getConnection()){

            String updateSql = "UPDATE student"
                    + "SET name = ? ,address = ?"
                    + "WHERE id = ?";

            PreparedStatement pS = conn.prepareStatement(updateSql);

            pS.setString(2, student.getName());
            pS.setString(3,student.getAddress());

            int count = pS.executeUpdate();
            System.out.println(count);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static void insertStudent(Student student){
        try(Connection conn = jdbc.dataSource().getConnection()){

            String insertSql = "INSERT INTO student(id,name,address) VALUES (?,?,?)";

            PreparedStatement statement = conn.prepareStatement(insertSql);

            statement.setInt(1,student.getId());
            statement.setString(2, student.getName());
            statement.setString(3,student.getAddress());

            int count = statement.executeUpdate();
            System.out.println(count);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void showStudent(){
        try(Connection conn = jdbc.dataSource().getConnection()){
            //1. Create SQL statement object
            String selectSql = "SELECT *FROM student";
            PreparedStatement statement = conn.prepareStatement(selectSql);
            //2.
            ResultSet resultSet = statement.executeQuery();
            //3.
            List<Student> studentList = new ArrayList<>();
            while(resultSet.next()){
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                studentList.add(new Student(id,name,address));
            }
            System.out.println(studentList);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}


