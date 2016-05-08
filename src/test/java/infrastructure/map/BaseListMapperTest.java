package infrastructure.map;

import java.util.ArrayList;

import org.mockito.Mockito;

import database.DatabaseException;
import database.IDatabase;
import database.IDatabaseResult;
import database.query.IQuery;
import database.query.SelectQuery;
import database.query.condition.IDbCondition;
import database.query.condition.WhereInQuery;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import infrastructure.IModel;

import static info.solidsoft.mockito.java8.AssertionMatcher.assertArg;  

public class BaseListMapperTest extends TestCase
{
	private class FakeModel implements IModel{

		public ArrayList<Integer> idsToMap = new ArrayList<Integer>();
		private int id;
		
		public FakeModel(int id) {
			this.id = id;
		}
		
		@Override
		public int getId() {
			return id;
		}

		@Override
		public IModel buildClone() {
			return new FakeModel(id);
		}
		
	}
	
	private class FakeListMapper extends BaseListMapper<FakeModel>{

		public FakeListMapper(IDatabase database) {
			super(database);
		}

		@Override
		public ArrayList<Integer> getIdListOnModel(FakeModel model) {
			return model.idsToMap;
		}

		@Override
		public String getDbMap() {
			return "fakeTable";
		}

		@Override
		public String getFromIdColumn() {
			return "fakeId1";
		}

		@Override
		public String getToIdColumn() {
			return "fakeId2";
		}
		
	}
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public BaseListMapperTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( BaseListMapperTest.class );
    }

    /**
     * Ensure that the correct table is called.
     */
    public void testDb_CorrectTable() throws DatabaseException{
    	IDatabase db = Mockito.mock(IDatabase.class);
    	IDatabaseResult dbResult = Mockito.mock(IDatabaseResult.class);
    	FakeListMapper mapper = new FakeListMapper(db);
    	
    	Mockito.when(dbResult.next()).thenReturn(false);
    	Mockito.when(db.executeQuery(Mockito.any(IQuery.class))).thenReturn(dbResult);
    	
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	models.add(new FakeModel(5));
    	mapper.doMap(models);
    	
    	Mockito.verify(db).executeQuery(assertArg(query -> assertEquals(mapper.getDbMap(), ((SelectQuery) query).getTable())));
    }
    
    /**
     * Ensure that a where condition is added for the correct column name.
     */
    public void testDb_CorrectLookupColumnName() throws DatabaseException{
    	IDatabase db = Mockito.mock(IDatabase.class);
    	IDatabaseResult dbResult = Mockito.mock(IDatabaseResult.class);
    	FakeListMapper mapper = new FakeListMapper(db);
    	
    	Mockito.when(dbResult.next()).thenReturn(false);
    	Mockito.when(db.executeQuery(Mockito.any(IQuery.class))).thenReturn(dbResult);
    	
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	models.add(new FakeModel(5));
    	mapper.doMap(models);
    	
    	Mockito.verify(db).executeQuery(assertArg(query -> assertHasWhereConditionWithColumnId(mapper.getFromIdColumn(), query)));
    }
    
    /**
     * Ensure that no other conditions are added.
     */
    public void testDb_NoSpuriousConditions() throws DatabaseException{
    	IDatabase db = Mockito.mock(IDatabase.class);
    	IDatabaseResult dbResult = Mockito.mock(IDatabaseResult.class);
    	FakeListMapper mapper = new FakeListMapper(db);
    	
    	Mockito.when(dbResult.next()).thenReturn(false);
    	Mockito.when(db.executeQuery(Mockito.any(IQuery.class))).thenReturn(dbResult);
    	
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	models.add(new FakeModel(5));
    	mapper.doMap(models);
    	
    	Mockito.verify(db).executeQuery(assertArg(query -> assertEquals(1, ((SelectQuery) query).getConditions().size())));
    }
    
    /**
     * Ensure that a where condition with the right id values is added.
     */
    public void testDb_CorrectLookupColumnIds() throws DatabaseException{
    	IDatabase db = Mockito.mock(IDatabase.class);
    	IDatabaseResult dbResult = Mockito.mock(IDatabaseResult.class);
    	FakeListMapper mapper = new FakeListMapper(db);
    	
    	Mockito.when(dbResult.next()).thenReturn(false);
    	Mockito.when(db.executeQuery(Mockito.any(IQuery.class))).thenReturn(dbResult);
    	
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	models.add(new FakeModel(5));
    	models.add(new FakeModel(6));
    	models.add(new FakeModel(7));
    	ArrayList<String> ids = new ArrayList<String>();
    	
    	for (FakeModel model : models) {
			ids.add(Integer.toString(model.id));
		}
    	
    	mapper.doMap(models);
    	
    	Mockito.verify(db).executeQuery(assertArg(query -> assertHasWhereConditionWithIdValues(ids, query)));
    }
    
    /**
     * Ensure that the db is called once and only once if values are given.
     */
    public void testDb_CalledOnceIfValues() throws DatabaseException{
    	IDatabase db = Mockito.mock(IDatabase.class);
    	IDatabaseResult dbResult = Mockito.mock(IDatabaseResult.class);
    	FakeListMapper mapper = new FakeListMapper(db);
    	
    	Mockito.when(dbResult.next()).thenReturn(false);
    	Mockito.when(db.executeQuery(Mockito.any(IQuery.class))).thenReturn(dbResult);
    	
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	models.add(new FakeModel(5));
    	models.add(new FakeModel(6));
    	models.add(new FakeModel(7));
    	ArrayList<String> ids = new ArrayList<String>();
    	
    	for (FakeModel model : models) {
			ids.add(Integer.toString(model.id));
		}
    	
    	mapper.doMap(models);
    	
    	Mockito.verify(db, Mockito.times(1)).executeQuery(Mockito.any(IQuery.class));
    }
    
    /**
     * Ensure that the db is never called if no values are given
     * @throws DatabaseException 
     */
    public void testDb_CalledNeverIfNoValues() throws DatabaseException{
    	IDatabase db = Mockito.mock(IDatabase.class);
    	IDatabaseResult dbResult = Mockito.mock(IDatabaseResult.class);
    	FakeListMapper mapper = new FakeListMapper(db);
    	
    	Mockito.when(dbResult.next()).thenReturn(false);
    	Mockito.when(db.executeQuery(Mockito.any(IQuery.class))).thenReturn(dbResult);
    	
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	ArrayList<String> ids = new ArrayList<String>();
    	
    	for (FakeModel model : models) {
			ids.add(Integer.toString(model.id));
		}
    	
    	mapper.doMap(models);
    	
    	Mockito.verify(db, Mockito.never()).executeQuery(Mockito.any(IQuery.class));
    }

	private void assertHasWhereConditionWithColumnId(String id, IQuery query) {
		ArrayList<String> conditionColumns = new ArrayList<String>();
		
		for (IDbCondition condition : ((SelectQuery) query).getConditions()) {
			if (condition.getClass().equals(WhereInQuery.class)){
				conditionColumns.add(((WhereInQuery) condition).getColumn());
			}
		}
		
		assertTrue(conditionColumns.contains(id));
	}
	
	private void assertHasWhereConditionWithIdValues(ArrayList<String> values, IQuery query) {
		
		WhereInQuery cond = (WhereInQuery) ((SelectQuery) query).getConditions().get(0);
		
		assertEquals(values, cond.getValues());
	}

	
	public void testMap_NothingAddedIfNoDbResults() throws DatabaseException{
		IDatabase db = Mockito.mock(IDatabase.class);
    	IDatabaseResult dbResult = Mockito.mock(IDatabaseResult.class);
    	FakeListMapper mapper = new FakeListMapper(db);
    	
    	Mockito.when(dbResult.next()).thenReturn(false);
    	Mockito.when(db.executeQuery(Mockito.any(IQuery.class))).thenReturn(dbResult);
    	
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	
    	FakeModel fm1 = new FakeModel(1);
    	FakeModel fm2 = new FakeModel(2);
    	
    	models.add(fm1);
    	models.add(fm2);
    	ArrayList<String> ids = new ArrayList<String>();
    	
    	for (FakeModel model : models) {
			ids.add(Integer.toString(model.id));
		}
    	
    	mapper.doMap(models);
    	
    	assertTrue(fm1.idsToMap.isEmpty());
    	assertTrue(fm2.idsToMap.isEmpty());
	}
	
	public void testMap_CorrectColumnsRead() throws DatabaseException{
		IDatabase db = Mockito.mock(IDatabase.class);
    	IDatabaseResult dbResult = Mockito.mock(IDatabaseResult.class);
    	FakeListMapper mapper = new FakeListMapper(db);
    	
    	int fromId = 1;
    	int toId = 27;
    	
    	Mockito.when(dbResult.next()).thenReturn(true, false);
    	Mockito.when(dbResult.getInt(mapper.getFromIdColumn())).thenReturn(fromId);
    	Mockito.when(dbResult.getInt(mapper.getToIdColumn())).thenReturn(toId);
    	Mockito.when(db.executeQuery(Mockito.any(IQuery.class))).thenReturn(dbResult);
    	
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	
    	FakeModel fm1 = new FakeModel(1);
    	
    	models.add(fm1);
    	ArrayList<String> ids = new ArrayList<String>();
    	
    	for (FakeModel model : models) {
			ids.add(Integer.toString(model.id));
		}
    	
    	mapper.doMap(models);
    	
    	Mockito.verify(dbResult).getInt(mapper.getFromIdColumn());
    	Mockito.verify(dbResult).getInt(mapper.getToIdColumn());
    	Mockito.verify(dbResult, Mockito.times(2)).getInt(Mockito.any(String.class));
	}
	
	public void testMap_OneModel_OneResult() throws DatabaseException{
		IDatabase db = Mockito.mock(IDatabase.class);
    	IDatabaseResult dbResult = Mockito.mock(IDatabaseResult.class);
    	FakeListMapper mapper = new FakeListMapper(db);
    	
    	int fromId = 1;
    	int toId = 27;
    	
    	Mockito.when(dbResult.next()).thenReturn(true, false);
    	Mockito.when(dbResult.getInt(mapper.getFromIdColumn())).thenReturn(fromId);
    	Mockito.when(dbResult.getInt(mapper.getToIdColumn())).thenReturn(toId);
    	Mockito.when(db.executeQuery(Mockito.any(IQuery.class))).thenReturn(dbResult);
    	
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	
    	FakeModel fm1 = new FakeModel(1);
    	
    	models.add(fm1);
    	ArrayList<String> ids = new ArrayList<String>();
    	
    	for (FakeModel model : models) {
			ids.add(Integer.toString(model.id));
		}
    	
    	mapper.doMap(models);
    	
    	assertEquals(1, fm1.idsToMap.size());
    	assertEquals(toId, fm1.idsToMap.get(0).intValue());
	}
	
	public void testMap_OneModel_MultipleResults() throws DatabaseException{
		IDatabase db = Mockito.mock(IDatabase.class);
    	IDatabaseResult dbResult = Mockito.mock(IDatabaseResult.class);
    	FakeListMapper mapper = new FakeListMapper(db);
    	
    	int fromId = 1;
    	int toId1 = 27;
    	int toId2 = 753;
    	
    	Mockito.when(dbResult.next()).thenReturn(true, true, false);
    	Mockito.when(dbResult.getInt(mapper.getFromIdColumn())).thenReturn(fromId, fromId);
    	Mockito.when(dbResult.getInt(mapper.getToIdColumn())).thenReturn(toId1, toId2);
    	Mockito.when(db.executeQuery(Mockito.any(IQuery.class))).thenReturn(dbResult);
    	
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	
    	FakeModel fm1 = new FakeModel(1);
    	
    	models.add(fm1);
    	ArrayList<String> ids = new ArrayList<String>();
    	
    	for (FakeModel model : models) {
			ids.add(Integer.toString(model.id));
		}
    	
    	mapper.doMap(models);
    	
    	assertEquals(2, fm1.idsToMap.size());
    	assertEquals(toId1, fm1.idsToMap.get(0).intValue());
    	assertEquals(toId2, fm1.idsToMap.get(1).intValue());
	}
	
	public void testMap_MultipleModels_SkippedModel() throws DatabaseException{
		IDatabase db = Mockito.mock(IDatabase.class);
    	IDatabaseResult dbResult = Mockito.mock(IDatabaseResult.class);
    	FakeListMapper mapper = new FakeListMapper(db);
    	
    	int fromId1 = 1;
    	int fromId2 = 3;
    	int fromId3 = 7;
    	int toId1 = 27;
    	int toId3 = 753;
    	
    	Mockito.when(dbResult.next()).thenReturn(true, true, false);
    	Mockito.when(dbResult.getInt(mapper.getFromIdColumn())).thenReturn(fromId1, fromId3);
    	Mockito.when(dbResult.getInt(mapper.getToIdColumn())).thenReturn(toId1, toId3);
    	Mockito.when(db.executeQuery(Mockito.any(IQuery.class))).thenReturn(dbResult);
    	
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	
    	FakeModel fm1 = new FakeModel(fromId1);
    	FakeModel fm2 = new FakeModel(fromId2);
    	FakeModel fm3 = new FakeModel(fromId3);
    	
    	models.add(fm1);
    	models.add(fm2);
    	models.add(fm3);
    	
    	mapper.doMap(models);
    	
    	assertEquals(1, fm1.idsToMap.size());
    	assertEquals(0, fm2.idsToMap.size());
    	assertEquals(1, fm3.idsToMap.size());
    	assertEquals(toId1, fm1.idsToMap.get(0).intValue());
    	assertEquals(toId3, fm3.idsToMap.get(0).intValue());
	}
	
	public void testMap_MultipleModels_WrongOrder() throws DatabaseException{
		IDatabase db = Mockito.mock(IDatabase.class);
    	IDatabaseResult dbResult = Mockito.mock(IDatabaseResult.class);
    	FakeListMapper mapper = new FakeListMapper(db);
    	
    	int fromId1 = 1;
    	int fromId2 = 2;
    	int fromId3 = 3;
    	int toId1_1 = 11;
    	int toId2_1 = 21;
    	int toId2_2 = 22;
    	int toId3_1 = 31;
    	
    	Mockito.when(dbResult.next()).thenReturn(true, true, true, true, false);
    	Mockito.when(dbResult.getInt(mapper.getFromIdColumn())).thenReturn(fromId2, fromId3, fromId2, fromId1);
    	Mockito.when(dbResult.getInt(mapper.getToIdColumn())).thenReturn(toId2_2, toId3_1, toId2_1, toId1_1);
    	Mockito.when(db.executeQuery(Mockito.any(IQuery.class))).thenReturn(dbResult);
    	
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	
    	FakeModel fm1 = new FakeModel(fromId1);
    	FakeModel fm2 = new FakeModel(fromId2);
    	FakeModel fm3 = new FakeModel(fromId3);
    	
    	models.add(fm1);
    	models.add(fm2);
    	models.add(fm3);
    	
    	mapper.doMap(models);
    	
    	assertEquals(1, fm1.idsToMap.size());
    	assertEquals(2, fm2.idsToMap.size());
    	assertEquals(1, fm3.idsToMap.size());
    	
    	assertEquals(toId1_1, fm1.idsToMap.get(0).intValue());
    	assertTrue(fm2.idsToMap.contains(toId2_1));
    	assertTrue(fm2.idsToMap.contains(toId2_2));
    	assertEquals(toId3_1, fm3.idsToMap.get(0).intValue());
	}
	
	public void testMap_MultipleModels_MultipleValues() throws DatabaseException{
		IDatabase db = Mockito.mock(IDatabase.class);
    	IDatabaseResult dbResult = Mockito.mock(IDatabaseResult.class);
    	FakeListMapper mapper = new FakeListMapper(db);
    	
    	int fromId1 = 1;
    	int fromId2 = 3;
    	int fromId3 = 7;
    	int fromId4 = 2;
    	int toId1 = 27;
    	int toId3_1 = 753;
    	int toId3_2 = 279;
    	
    	Mockito.when(dbResult.next()).thenReturn(true, true, true, false);
    	Mockito.when(dbResult.getInt(mapper.getFromIdColumn())).thenReturn(fromId1, fromId3, fromId3);
    	Mockito.when(dbResult.getInt(mapper.getToIdColumn())).thenReturn(toId1, toId3_1, toId3_2);
    	Mockito.when(db.executeQuery(Mockito.any(IQuery.class))).thenReturn(dbResult);
    	
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	
    	FakeModel fm1 = new FakeModel(fromId1);
    	FakeModel fm2 = new FakeModel(fromId2);
    	FakeModel fm3 = new FakeModel(fromId3);
    	FakeModel fm4 = new FakeModel(fromId4);
    	
    	models.add(fm1);
    	models.add(fm2);
    	models.add(fm3);
    	models.add(fm4);
    	
    	mapper.doMap(models);
    	
    	assertEquals(1, fm1.idsToMap.size());
    	assertEquals(0, fm2.idsToMap.size());
    	assertEquals(2, fm3.idsToMap.size());
    	assertEquals(0, fm4.idsToMap.size());
    	assertEquals(toId1, fm1.idsToMap.get(0).intValue());
    	assertEquals(toId3_1, fm3.idsToMap.get(0).intValue());
    	assertEquals(toId3_2, fm3.idsToMap.get(1).intValue());
	}
}
