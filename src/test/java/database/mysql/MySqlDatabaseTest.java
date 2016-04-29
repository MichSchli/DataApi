package database.mysql;

import junit.framework.Assert;
import junit.framework.TestCase;

public class MySqlDatabaseTest extends TestCase{
	
	/**
	 * Test connection to db. comment out.
	 */
    public void testCreate()
    {
    	MySqlDatabaseConfiguration config = new MySqlDatabaseConfiguration();
    	config.ServerName = "myserver";
    	config.UserName = "testuser";
    	config.Password = "1234";
    	MySqlDatabase db = new MySqlDatabase(config);
        Assert.assertNotNull(db);
    }
}
