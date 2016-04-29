package database;

import java.sql.ResultSet;

import database.query.IQuery;

public interface IDatabase {
	void executeUpdate(IQuery query);
	ResultSet executeQuery(IQuery query);
}
