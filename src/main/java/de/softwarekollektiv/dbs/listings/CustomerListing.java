package de.softwarekollektiv.dbs.listings;

import java.io.PrintStream;

import de.softwarekollektiv.dbs.dbcon.DbConnection;

class CustomerListing extends AbstractListing {

	CustomerListing(PrintStream out, DbConnection dbcon) {
		super(out, dbcon);
	}

	@Override
	public String getTitle() {
		return "Customers";
	}

	@Override
	public String getDescription() {
		return "Prints a list of all customers with their associated ID.";
	}

	@Override
	protected String getTable() {
		return "customers";
	}

	@Override
	protected String[] getFields() {
		return new String[] { "cus_id", "name", "surname" };
	}

}
