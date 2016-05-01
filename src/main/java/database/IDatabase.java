package database;

import database.query.IQuery;

public interface IDatabase {
	void executeUpdate(IQuery query);
	IDatabaseResult executeQuery(IQuery query);
}
