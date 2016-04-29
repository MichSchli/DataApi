package database.mysql.query;

import java.util.ArrayList;

import org.mockito.Mockito;

import api.ClientTest;
import database.query.SelectQuery;
import database.query.condition.IDbCondition;
import database.query.sort.IDbSort;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SelectTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SelectTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SelectTest.class );
    }

    /**
     * Ensure that a query with a single item can be parsed
     */
    public void testToString_SingleItem(){
    	SelectQuery query = new SelectQuery("testrowname", "testtable");
    	String stringQuery = query.process();
    	
    	Assert.assertEquals("select testrowname from testtable", stringQuery);
    }
    
    /**
     * Ensure that a query with a closure can be parsed
     */
    public void testToString_AllItems(){
    	SelectQuery query = new SelectQuery("testtable");
    	String stringQuery = query.process();
    	
    	Assert.assertEquals("select * from testtable", stringQuery);
    }
    
    /**
     * Ensure that a query with a list of items can be parsed
     */
    public void testToString_ItemList(){
    	ArrayList<String> items = new ArrayList<String>();
    	
    	items.add("testrowname1");
    	items.add("testrowname2");
    	items.add("testrowname3");
    	SelectQuery query = new SelectQuery(items, "testtable");
    	String stringQuery = query.process();
    	
    	Assert.assertEquals("select testrowname1,testrowname2,testrowname3 from testtable", stringQuery);
    }
    
    /**
     * Ensure that a query with a single condition can be parsed
     */
    public void testToString_SingleCondition(){
    	SelectQuery query = new SelectQuery("testrowname", "testtable");
    	IDbCondition c = Mockito.mock(IDbCondition.class);

		Mockito.when(c.process()).thenReturn("testcondition");
		
    	query.addCondition(c);
    	
    	String stringQuery = query.process();
    	
    	Assert.assertEquals("select testrowname from testtable where testcondition", stringQuery);
    }
    
    /**
     * Ensure that a query with multiple conditions can be parsed
     */
    public void testToString_MultipleConditions(){
    	SelectQuery query = new SelectQuery("testrowname", "testtable");
    	IDbCondition c1 = Mockito.mock(IDbCondition.class);
    	IDbCondition c2 = Mockito.mock(IDbCondition.class);
    	IDbCondition c3 = Mockito.mock(IDbCondition.class);

		Mockito.when(c1.process()).thenReturn("testcondition1");
		Mockito.when(c2.process()).thenReturn("testcondition2");
		Mockito.when(c3.process()).thenReturn("testcondition3");
		
    	query.addCondition(c1);
    	query.addCondition(c2);
    	query.addCondition(c3);
    	
    	String stringQuery = query.process();
    	
    	Assert.assertEquals("select testrowname from testtable where testcondition1 and testcondition2 and testcondition3", stringQuery);
    }
    
    /**
     * Ensure that a query with a single sort can be parsed
     */
    public void testToString_SingleSort(){
    	SelectQuery query = new SelectQuery("testrowname", "testtable");
    	IDbSort s = Mockito.mock(IDbSort.class);

		Mockito.when(s.process()).thenReturn("testval asc");
		
    	query.addSort(s);
    	
    	String stringQuery = query.process();
    	
    	Assert.assertEquals("select testrowname from testtable order by testval asc", stringQuery);
    }
    
    /**
     * Ensure that a query with a multiple sorts can be parsed
     */
    public void testToString_MultipleSorts(){
    	SelectQuery query = new SelectQuery("testrowname", "testtable");
    	IDbSort s1 = Mockito.mock(IDbSort.class);
    	IDbSort s2 = Mockito.mock(IDbSort.class);
    	IDbSort s3 = Mockito.mock(IDbSort.class);

		Mockito.when(s1.process()).thenReturn("testval1 asc");
		Mockito.when(s2.process()).thenReturn("testval2 desc");
		Mockito.when(s3.process()).thenReturn("testval3 asc");
		
    	query.addSort(s1);
    	query.addSort(s2);
    	query.addSort(s3);
    	
    	String stringQuery = query.process();
    	
    	Assert.assertEquals("select testrowname from testtable order by testval1 asc, testval2 desc, testval3 asc", stringQuery);
    }
    
}
