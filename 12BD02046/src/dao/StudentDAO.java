/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;



import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.*;
import model.Student;

/**
 *
 * @author User
 */
public class StudentDAO {
    private Connection myConn;
	
	public StudentDAO() throws Exception {
		
		
		Properties props = new Properties();
		props.load(new FileInputStream("db.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		//conection to db
		myConn = DriverManager.getConnection(dburl, user, password);
		
	}
       
        public void updateStudent(Student theStudent) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			//sql statement
			myStmt = myConn.prepareStatement("update students" + " set firstname=?, lastname=?" + " where id=?");
			
                        myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setInt(3, theStudent.getId());
                        
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
		
	}
        public void deleteStudent(int stId) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("delete from students where id=?");
			
			myStmt.setInt(1, stId);
			
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
	}
	
	public void addStudent(Student theStudent) throws Exception {
		PreparedStatement myStmt = null;

		try {
			myStmt = myConn.prepareStatement("insert into students" + " (firstname, lastname)" + " values (?, ?)");
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt);
		}
		
	}
	
	public List<Student> getAllStudents() throws Exception {
		List<Student> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from students");
			
			while (myRs.next()) {
				Student temp = convertRowToStudent(myRs);
				list.add(temp);
			}

			return list;		
		}
		finally {
			close(myStmt);
		}
	}
	
	
	private Student convertRowToStudent(ResultSet myRs) throws SQLException {
		
		int id = myRs.getInt("id");
		String lastName = myRs.getString("lastname");
		String firstName = myRs.getString("firstname");
		
		Student temp = new Student(id,firstName, lastName);
		
		return temp;
	}

	
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs)
			throws SQLException {

		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			
		}
		
		if (myConn != null) {
			myConn.close();
		}
	}

	private void close(Statement myStmt) throws SQLException {
		close(null, myStmt, null);		
	}

	public static void main(String[] args) throws Exception {
		
		StudentDAO dao = new StudentDAO();
                System.out.println(dao.getAllStudents());
	}
    
}
