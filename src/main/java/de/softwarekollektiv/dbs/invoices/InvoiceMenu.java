package de.softwarekollektiv.dbs.invoices;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import de.softwarekollektiv.dbs.app.MenuItem;
import de.softwarekollektiv.dbs.app.SelectionMenu;
import de.softwarekollektiv.dbs.dbcon.DbConnection;

public class InvoiceMenu extends SelectionMenu implements MenuItem {

	private List<MenuItem> items;
	
	public InvoiceMenu(PrintStream out, InputStream in, DbConnection dbcon) {
		super(out, in);
		
		items = new LinkedList<MenuItem>();
		items.add(new SingleInvoice(out, in, dbcon));
	}

	@Override
	public String getTitle() {
		return "Invoices";
	}

	@Override
	public String getDescription() {
		return "Lets the user generate invoices for each rental or accumulated by user per year";
	}

	@Override
	protected List<MenuItem> getItems() {
		return items;
	}

	@Override
	protected String getGreeter() {
		return "Invoices";
	}

}