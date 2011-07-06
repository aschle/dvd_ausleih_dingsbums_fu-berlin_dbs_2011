package de.softwarekollektiv.dbs.parser.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.softwarekollektiv.dbs.dbcon.DbConnection;
import de.softwarekollektiv.dbs.parser.AbstractParser;


public class CustomerParser extends AbstractParser {

	private PreparedStatement customerStatement;

	public CustomerParser(DbConnection dbcon, String file) throws SQLException {
		super(dbcon, file);
		super.delimiter = ",";
		
		customerStatement = dbcon.getConnection().prepareStatement(
				"INSERT INTO customers VALUES (?, ?, ?, ?, ?, ?, ?)"
			);
	}

	@Override
	public void newLine(String[] lineParts) {
		int id = Integer.parseInt(lineParts[0]);
		String name = lineParts[1];
		String surname = lineParts[2];
		String street = lineParts[3];
		String zip = lineParts[4];
		String city = lineParts[5];
		String phone = lineParts[6];
		
		try {
			customerStatement.setInt(1, id);
			customerStatement.setString(2, name);
			customerStatement.setString(3, surname);
			customerStatement.setString(4, street);
			customerStatement.setString(5, zip);
			customerStatement.setString(6, city);
			customerStatement.setString(7, phone);	
			
			customerStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void skipHeader(BufferedReader in) throws IOException {
		in.readLine();
	}
}
