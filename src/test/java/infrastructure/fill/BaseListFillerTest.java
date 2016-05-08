package infrastructure.fill;

import static info.solidsoft.mockito.java8.AssertionMatcher.assertArg;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;

import infrastructure.IModel;
import infrastructure.repositories.IRepository;
import infrastructure.specifications.ISpecification;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class BaseListFillerTest extends TestCase
{
	private class FakeModel implements IModel{

		public ArrayList<Integer> idsToMap = new ArrayList<Integer>();
		public ArrayList<FakeModel> foreignsToFill = new ArrayList<FakeModel>();
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
	
	private class FakeSpecification implements ISpecification{

		@Override
		public List<Integer> getIds() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isIdentifierLookup() {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	private class FakeListFiller extends BaseListFiller<FakeModel, FakeModel>{

		public FakeListFiller(IRepository<FakeModel,FakeSpecification> repository) {
			super(repository);
		}

		@Override
		public ArrayList<Integer> getIdListOnModel(FakeModel model) {
			return model.idsToMap;
		}

		@Override
		public ArrayList<FakeModel> getForeignListOnModel(FakeModel model) {
			return model.foreignsToFill;
		}

	}
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public BaseListFillerTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( BaseListFillerTest.class );
    }

    /**
     * Ensure that the repository is not called if there is no models
     * @throws FillException 
     */
	@SuppressWarnings("unchecked")
    public void testRepository_NotCalledIfNoModels() throws FillException{
		IRepository<FakeModel,FakeSpecification> repository = (IRepository<FakeModel,FakeSpecification>) Mockito.mock(IRepository.class);
    	FakeListFiller filler = new FakeListFiller(repository);
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	
    	filler.doFill(models);
    	
    	Mockito.verify(repository, Mockito.never()).Retrieve(Mockito.any(ArrayList.class));
    }

	@SuppressWarnings("unchecked")
    public void testRepository_RetrievesOnceAndNothingElse() throws FillException{
		IRepository<FakeModel,FakeSpecification> repository = (IRepository<FakeModel,FakeSpecification>) Mockito.mock(IRepository.class);
    	FakeListFiller filler = new FakeListFiller(repository);
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	
    	FakeModel fm1 = new FakeModel(1);
    	FakeModel fm2 = new FakeModel(2);

    	fm1.idsToMap.add(6);    	
    	fm2.idsToMap.add(7);
    	
    	FakeModel fm_r1 = new FakeModel(6);
    	FakeModel fm_r2 = new FakeModel(7);
    	ArrayList<FakeModel> returns = new ArrayList<FakeModel>();
    	returns.add(fm_r1);
    	returns.add(fm_r2);
    	Mockito.when(repository.Retrieve(Mockito.any(ArrayList.class))).thenReturn(returns);
    	
    	models.add(fm1);
    	models.add(fm2);
    	
    	filler.doFill(models);
    	
    	Mockito.verify(repository, Mockito.times(1)).Retrieve(Mockito.any(ArrayList.class));
    	Mockito.verify(repository, Mockito.only()).Retrieve(Mockito.any(ArrayList.class));
    }

	@SuppressWarnings("unchecked")
    public void testRepository_NotCalledIfNoModelsWithIds() throws FillException{
		IRepository<FakeModel,FakeSpecification> repository = (IRepository<FakeModel,FakeSpecification>) Mockito.mock(IRepository.class);
    	FakeListFiller filler = new FakeListFiller(repository);
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	
    	FakeModel fm1 = new FakeModel(1);
    	FakeModel fm2 = new FakeModel(2);
    	
    	models.add(fm1);
    	models.add(fm2);
    	
    	filler.doFill(models);
    	
    	Mockito.verify(repository, Mockito.never()).Retrieve(Mockito.any(ArrayList.class));
    }

	@SuppressWarnings("unchecked")
    public void testRepository_RightIdsOnSpec() throws FillException{
		IRepository<FakeModel,FakeSpecification> repository = (IRepository<FakeModel,FakeSpecification>) Mockito.mock(IRepository.class);
    	FakeListFiller filler = new FakeListFiller(repository);
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	
    	FakeModel fm1 = new FakeModel(1);
    	FakeModel fm2 = new FakeModel(2);
    	FakeModel fm3 = new FakeModel(3);
    	
    	models.add(fm1);
    	models.add(fm2);
    	models.add(fm3);
    	
    	fm1.idsToMap.add(5);
    	fm1.idsToMap.add(6);
    	
    	fm3.idsToMap.add(7);
    	fm3.idsToMap.add(8);
    	fm3.idsToMap.add(9);
    	
    	FakeModel fm_r1 = new FakeModel(5);
    	FakeModel fm_r2 = new FakeModel(6);
    	FakeModel fm_r3 = new FakeModel(7);
    	FakeModel fm_r4 = new FakeModel(8);
    	FakeModel fm_r5 = new FakeModel(9);
    	ArrayList<FakeModel> returns = new ArrayList<FakeModel>();
    	returns.add(fm_r1);
    	returns.add(fm_r2);
    	returns.add(fm_r3);
    	returns.add(fm_r4);
    	returns.add(fm_r5);
    	Mockito.when(repository.Retrieve(Mockito.any(ArrayList.class))).thenReturn(returns);
    	
    	filler.doFill(models);

    	ArrayList<Integer> idStrings = new ArrayList<Integer>();
    	idStrings.add(5);
    	idStrings.add(6);
    	idStrings.add(7);
    	idStrings.add(8);
    	idStrings.add(9);
    	Mockito.verify(repository).Retrieve(assertArg(ids -> assertEquals(5,ids.size())));
    	Mockito.verify(repository).Retrieve(assertArg(ids -> assertTrue(ids.containsAll(idStrings))));
    }
    
	@SuppressWarnings("unchecked")
	public void testRepository_NoDuplicateIdsOnSpec() throws FillException{
    	IRepository<FakeModel,FakeSpecification> repository = (IRepository<FakeModel,FakeSpecification>) Mockito.mock(IRepository.class);
    	FakeListFiller filler = new FakeListFiller(repository);
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	
    	FakeModel fm1 = new FakeModel(1);
    	FakeModel fm2 = new FakeModel(2);
    	FakeModel fm3 = new FakeModel(3);
    	
    	models.add(fm1);
    	models.add(fm2);
    	models.add(fm3);
    	
    	fm1.idsToMap.add(5);
    	fm1.idsToMap.add(6);
    	
    	fm3.idsToMap.add(5);
    	fm3.idsToMap.add(8);
    	
    	FakeModel fm_r1 = new FakeModel(5);
    	FakeModel fm_r2 = new FakeModel(6);
    	FakeModel fm_r3 = new FakeModel(8);
    	ArrayList<FakeModel> returns = new ArrayList<FakeModel>();
    	returns.add(fm_r1);
    	returns.add(fm_r2);
    	returns.add(fm_r3);
    	Mockito.when(repository.Retrieve(Mockito.any(ArrayList.class))).thenReturn(returns);
    	
    	
    	filler.doFill(models);

    	Mockito.verify(repository).Retrieve(assertArg(list -> assertEquals(3,list.size())));
    }

	@SuppressWarnings("unchecked")
	public void testFill_DirectMap() throws FillException{
		IRepository<FakeModel,FakeSpecification> repository = (IRepository<FakeModel,FakeSpecification>) Mockito.mock(IRepository.class);
    	FakeListFiller filler = new FakeListFiller(repository);
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	
    	FakeModel fm1 = new FakeModel(1);
    	FakeModel fm2 = new FakeModel(2);

    	FakeModel fm_r1 = new FakeModel(5);
    	FakeModel fm_r2 = new FakeModel(6);
    	ArrayList<FakeModel> returns = new ArrayList<FakeModel>();
    	returns.add(fm_r1);
    	returns.add(fm_r2);
    	Mockito.when(repository.Retrieve(Mockito.any(ArrayList.class))).thenReturn(returns);
    	
    	models.add(fm1);
    	models.add(fm2);
    	
    	fm1.idsToMap.add(5);
    	fm2.idsToMap.add(6);
    	
    	filler.doFill(models);

    	assertEquals(1, fm1.foreignsToFill.size());
    	assertEquals(1, fm2.foreignsToFill.size());

    	assertEquals(fm_r1.id, fm1.foreignsToFill.get(0).id);
    	assertEquals(fm_r2.id, fm2.foreignsToFill.get(0).id);
    }

	@SuppressWarnings("unchecked")
	public void testFill_DbMiss_ThrowsException(){
		IRepository<FakeModel,FakeSpecification> repository = (IRepository<FakeModel,FakeSpecification>) Mockito.mock(IRepository.class);
    	FakeListFiller filler = new FakeListFiller(repository);
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	
    	FakeModel fm1 = new FakeModel(1);
    	FakeModel fm2 = new FakeModel(2);

    	FakeModel fm_r1 = new FakeModel(5);
    	ArrayList<FakeModel> returns = new ArrayList<FakeModel>();
    	returns.add(fm_r1);
    	Mockito.when(repository.Retrieve(Mockito.any(ArrayList.class))).thenReturn(returns);
    	
    	models.add(fm1);
    	models.add(fm2);
    	
    	fm1.idsToMap.add(5);
    	fm2.idsToMap.add(6);
    	try {
        	filler.doFill(models);
        	fail("Exception not thrown.");
		} catch (FillException e) {
			assertEquals("Mapper contained link to nonexisting resource (id = 6)",e.getMessage());
		}

    }

	@SuppressWarnings("unchecked")
	public void testFill_MultipleForeigns() throws FillException{
		IRepository<FakeModel,FakeSpecification> repository = (IRepository<FakeModel,FakeSpecification>) Mockito.mock(IRepository.class);
    	FakeListFiller filler = new FakeListFiller(repository);
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	
    	FakeModel fm1 = new FakeModel(1);
    	FakeModel fm2 = new FakeModel(2);

    	FakeModel fm_r1 = new FakeModel(4);
    	FakeModel fm_r2 = new FakeModel(5);
    	FakeModel fm_r3 = new FakeModel(6);
    	FakeModel fm_r4 = new FakeModel(7);
    	ArrayList<FakeModel> returns = new ArrayList<FakeModel>();
    	returns.add(fm_r1);
    	returns.add(fm_r2);
    	returns.add(fm_r3);
    	returns.add(fm_r4);
    	Mockito.when(repository.Retrieve(Mockito.any(ArrayList.class))).thenReturn(returns);
    	
    	models.add(fm1);
    	models.add(fm2);
    	
    	fm1.idsToMap.add(4);
    	fm1.idsToMap.add(5);
    	fm2.idsToMap.add(6);
    	fm2.idsToMap.add(7);
    	
    	filler.doFill(models);

    	assertEquals(2, fm1.foreignsToFill.size());
    	assertEquals(2, fm2.foreignsToFill.size());

    	assertEquals(fm_r1.id, fm1.foreignsToFill.get(0).id);
    	assertEquals(fm_r2.id, fm1.foreignsToFill.get(1).id);
    	assertEquals(fm_r3.id, fm2.foreignsToFill.get(0).id);
    	assertEquals(fm_r4.id, fm2.foreignsToFill.get(1).id);
    }

	@SuppressWarnings("unchecked")
	public void testFill_OverlappingForeigns() throws FillException{
		IRepository<FakeModel,FakeSpecification> repository = (IRepository<FakeModel,FakeSpecification>) Mockito.mock(IRepository.class);
    	FakeListFiller filler = new FakeListFiller(repository);
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	
    	FakeModel fm1 = new FakeModel(1);
    	FakeModel fm2 = new FakeModel(2);

    	FakeModel fm_r1 = new FakeModel(4);
    	FakeModel fm_r2 = new FakeModel(5);
    	FakeModel fm_r3 = new FakeModel(6);
    	ArrayList<FakeModel> returns = new ArrayList<FakeModel>();
    	returns.add(fm_r1);
    	returns.add(fm_r2);
    	returns.add(fm_r3);
    	Mockito.when(repository.Retrieve(Mockito.any(ArrayList.class))).thenReturn(returns);
    	
    	models.add(fm1);
    	models.add(fm2);
    	
    	fm1.idsToMap.add(4);
    	fm1.idsToMap.add(5);
    	fm2.idsToMap.add(5);
    	fm2.idsToMap.add(6);
    	
    	filler.doFill(models);

    	assertEquals(2, fm1.foreignsToFill.size());
    	assertEquals(2, fm2.foreignsToFill.size());

    	assertEquals(fm_r1.id, fm1.foreignsToFill.get(0).id);
    	assertEquals(fm_r2.id, fm1.foreignsToFill.get(1).id);
    	assertEquals(fm_r2.id, fm2.foreignsToFill.get(0).id);
    	assertEquals(fm_r3.id, fm2.foreignsToFill.get(1).id);
    }

	@SuppressWarnings("unchecked")
	public void testFill_ModelsWithForeigns() throws FillException{
    	@SuppressWarnings("unchecked")
		IRepository<FakeModel,FakeSpecification> repository = (IRepository<FakeModel,FakeSpecification>) Mockito.mock(IRepository.class);
    	FakeListFiller filler = new FakeListFiller(repository);
    	ArrayList<FakeModel> models = new ArrayList<FakeModel>();
    	
    	FakeModel fm1 = new FakeModel(1);
    	FakeModel fm2 = new FakeModel(2);
    	FakeModel fm3 = new FakeModel(3);

    	FakeModel fm_r1 = new FakeModel(4);
    	FakeModel fm_r2 = new FakeModel(5);
    	FakeModel fm_r3 = new FakeModel(6);
    	FakeModel fm_r4 = new FakeModel(7);
    	ArrayList<FakeModel> returns = new ArrayList<FakeModel>();
    	returns.add(fm_r1);
    	returns.add(fm_r2);
    	returns.add(fm_r3);
    	returns.add(fm_r4);
    	Mockito.when(repository.Retrieve(Mockito.any(ArrayList.class))).thenReturn(returns);
    	
    	models.add(fm1);
    	models.add(fm2);
    	models.add(fm3);
    	
    	fm1.idsToMap.add(4);
    	fm1.idsToMap.add(5);
    	fm3.idsToMap.add(6);
    	fm3.idsToMap.add(7);
    	
    	filler.doFill(models);

    	assertEquals(2, fm1.foreignsToFill.size());
    	assertEquals(0, fm2.foreignsToFill.size());
    	assertEquals(2, fm3.foreignsToFill.size());

    	assertEquals(fm_r1.id, fm1.foreignsToFill.get(0).id);
    	assertEquals(fm_r2.id, fm1.foreignsToFill.get(1).id);
    	assertEquals(fm_r3.id, fm3.foreignsToFill.get(0).id);
    	assertEquals(fm_r4.id, fm3.foreignsToFill.get(1).id);
    }
}
