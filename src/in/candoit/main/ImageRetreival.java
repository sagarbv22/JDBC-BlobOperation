package in.candoit.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

import in.candoit.util.JdbcUtil;

public class ImageRetreival {

	public static void main(String[] args) {

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		// establish the connection
		connection = JdbcUtil.getConnection();

		try {
			if (connection != null)
				// create the pstmt
				pstmt = connection.prepareStatement("Select id, name, image from heros where id = ?");

			Scanner sc = new Scanner(System.in);
			System.out.print("Enter the id::");
			int id = sc.nextInt();

			pstmt.setInt(1, id);
			// location of the file to be copied
			File file = new File("D:\\images\\Copy.jpeg");
			FileOutputStream fos = new FileOutputStream(file);
			// execute the query
			resultSet = pstmt.executeQuery();
			// process the resultSet
			if (resultSet != null) {
				if (resultSet.next()) {
					int idNo = resultSet.getInt(1);
					String name = resultSet.getString(2);
					InputStream in = resultSet.getBinaryStream(3);
					// copying form inputStream to outputStream
					IOUtils.copy(in, fos);

					System.out.println(idNo + "\t" + name + "\t" + file.getAbsolutePath());
				}

			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			JdbcUtil.close(connection, pstmt, resultSet);
		}

	}

}
