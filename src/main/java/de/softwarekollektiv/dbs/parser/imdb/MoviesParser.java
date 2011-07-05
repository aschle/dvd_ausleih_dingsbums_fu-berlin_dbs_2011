package de.softwarekollektiv.dbs.parser.imdb;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import de.softwarekollektiv.dbs.dbcon.DbConnection;
import de.softwarekollektiv.dbs.parser.AbstractParser;
import de.softwarekollektiv.dbs.parser.Parser;

public class MoviesParser extends AbstractParser implements Parser {

	private PreparedStatement movieStatement;

	public MoviesParser(DbConnection dbcon, String file) throws SQLException {
		super(dbcon, file);
		super.delimiter = "\t+";
		super.firstStop = "title\tyear\tcategory";

		movieStatement = dbcon.getConnection().prepareStatement(
				"INSERT INTO movies VALUES (DEFAULT, ?, ?,null,?, null)");
		

	}

	/*
	 * Each line is independend from any other It consists of one title, release
	 * year and a Category seperated by \t+
	 */
	@Override
	public void newLine(String[] lineParts) {


		String movieTitle = lineParts[0];
		Date movieRelease;
		String movieCategory;
		try {
			movieRelease = getFirstDayOfYear(lineParts[1].substring(0,4));
			movieCategory = lineParts[2];
		} catch (StringIndexOutOfBoundsException e){
			log.debug(Arrays.toString(lineParts), e);
			movieRelease = getDateFromImdbString(lineParts[0]);
			movieCategory = lineParts[1];
		} catch (NumberFormatException ne){
			log.debug(Arrays.toString(lineParts), ne);
			movieRelease = getDateFromImdbString(lineParts[0]);
			movieCategory = lineParts[2];
		}
		
		try {
			movieStatement.setString(1, movieTitle);
			movieStatement.setDate(2, movieRelease);
			movieStatement.setString(3, movieCategory);
			
			movieStatement.execute();
		} catch (SQLException e) {
//			log.warn(Arrays.toString(lineParts), e);
		}

	}

}
