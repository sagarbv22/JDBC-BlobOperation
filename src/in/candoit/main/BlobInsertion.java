package in.candoit.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.Scanner;

import in.candoit.util.JdbcUtil;

public class BlobInsertion {

	public static void main(String[] args) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		// Establish the connection
		connection = JdbcUtil.getConnection();

		try {
			if (connection != null)
				// create the pstmt
				preparedStatement = connection.prepareStatement("Insert into heros(name, image) values(?,?)");

			if (preparedStatement != null) {

				Scanner sc = new Scanner(System.in);
				// userInput
				System.out.print("Enter the name::");
				String name = sc.next();
				System.out.print("Enter the location");
				String imgLoc = sc.next();
				// set the values
				preparedStatement.setString(1, name);
				preparedStatement.setBinaryStream(2, new FileInputStream(new File(imgLoc)));
				// execute the Query
				int rows = preparedStatement.executeUpdate();
				System.out.println("No of rows affected :: " + rows);
				sc.close();
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			// close the connection

			JdbcUtil.close(connection, preparedStatement, null);
		}

	}

}
