package database;

public interface IDatabaseResult {

	boolean next() throws DatabaseException;
	int getInt(String fromIdColumn) throws DatabaseException;
	String getString(String string) throws DatabaseException;

}
