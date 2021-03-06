package de.softwarekollektiv.dbs.queries.simple;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import de.softwarekollektiv.dbs.app.AbstractSelectionMenu;
import de.softwarekollektiv.dbs.app.MenuItem;
import de.softwarekollektiv.dbs.app.QuitItem;
import de.softwarekollektiv.dbs.dbcon.DbConnection;

public class SimpleQueryMenu extends AbstractSelectionMenu implements MenuItem {

	private final List<MenuItem> items;
	
	public SimpleQueryMenu(PrintStream out, InputStream in, DbConnection dbcon) {
		super(out, in);
		
		items = new LinkedList<MenuItem>();
		items.add(new QueryA(out, dbcon));
		items.add(new QueryB(out, dbcon));
		items.add(new QueryC(out, dbcon));
		items.add(new QueryD(out, dbcon));
		items.add(new QuitItem("Return", "Return to main menu."));
	}
	
	@Override
	protected List<MenuItem> getItems() {
		return items;
	}

	@Override
	protected String getGreeter() {
		return "Simple queries (as required by assignment 5.1)";
	}

	@Override
	public String getTitle() {
		return "Simple queries";
	}

	@Override
	public String getDescription() {
		return "Run the simple SQL queries as required in the project description.";
	}

}
