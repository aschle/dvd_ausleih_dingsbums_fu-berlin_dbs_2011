package de.softwarekollektiv.dbs.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import de.softwarekollektiv.dbs.app.MenuItem;
import de.softwarekollektiv.dbs.dbcon.DbConnection;
import de.softwarekollektiv.dbs.parser.imdb.ActorsParser;
import de.softwarekollektiv.dbs.parser.misc.CustomerParser;

// TODO rename again
public class ParserCommander implements MenuItem {

	private static Logger log = Logger.getLogger(ParserCommander.class);

	private static String createScript = "src/main/resources/create.sql";
	private final DbConnection dbcon;
	
	public ParserCommander(DbConnection dbcon) {
		this.dbcon = dbcon;
	}

	@Override
	public String getTitle() {
		return "Saschas Importer stuff";
	}

	@Override
	public String getDescription() {
		return "Sascha hackt hier an DB-Erzeugung und Importierung herum.";
	}

	@Override
	public boolean run() throws Exception {
		/*
		 * create a database with scheme
		 */
		log.info("creating database...");

		String query = fileToString(createScript);
		Connection db = dbcon.getConnection();
		Statement create = db.createStatement();
		create.execute(query);
		create.close();
		db.commit();

		Statement st =  dbcon.getConnection().createStatement();
		ResultSet result = st.executeQuery("select nextval(mov_id_seq) from movies");
		System.out.println(result.next());
		
		System.exit(0);
		
		log.info("begin parsing data");
		
		List<Parser> parsers = new LinkedList<Parser>();
//		parsers.add(new MoviesParser(dbcon, "src/main/resources/modmovies.list"));
		parsers.add(new ActorsParser(dbcon, "src/main/resources/actors.list"));
		parsers.add(new ActorsParser(dbcon, "src/main/resources/actresses.list"));
		parsers.add(new CustomerParser(dbcon, "src/main/resources/customers.list"));	
		
		log.debug("parsing lists");
		
		long before = System.currentTimeMillis();	
		
		for (final Parser parser : parsers){

					// TODO handle exceptions?
					parser.open();
					parser.parse();
					parser.close();
					
		}
		
		log.debug("time: " +( System.currentTimeMillis() - before) +" ms");
		
		db.close();
		return true;
	}

	private static String fileToString(String file) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));

			String line = null;
			while ((line = in.readLine()) != null) {
				sb.append(line + "\n");
			}

		} catch (IOException e) {
			// log.warn("Cannot convert file to string", e);
			e.printStackTrace();
		}
		return sb.toString();
	}
}