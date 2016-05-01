package domains.images;

import java.util.ArrayList;
import java.util.List;

import database.DatabaseException;
import database.IDatabase;
import database.IDatabaseResult;
import database.query.IQuery;
import domains.tags.TagRepository;
import infrastructure.SpecificationHelper;
import infrastructure.repositories.IRepository;
import infrastructure.specifications.ISpecification;

public class ImageRepository implements IRepository<Image> {

	private ImageMapHelper imageMapHelper;
	private ImageFillHelper imageFillHelper;
	private IDatabase database;


	public ImageRepository(IDatabase database){
		this.database = database;
		imageMapHelper = new ImageMapHelper(database);
		imageFillHelper = new ImageFillHelper(new TagRepository(database));
	}
	

	public void Add(Image model){
		IQuery query = ModelToQuery(model);
		database.executeUpdate(query);
	}

	public List<Image> Retrieve(ISpecification specifications) {
		IDatabaseResult dbresults = database.executeQuery(SpecificationsToQuery(specifications));
		ArrayList<Image> results = new ArrayList<Image>();
		try {
			while(dbresults.next()){
				results.add(ReadFromDb(dbresults));
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		
		imageMapHelper.addMap(results);
		imageFillHelper.fillResults(results);
		
		return results;
	}
	
	public IQuery SpecificationsToQuery(ISpecification specification) {
		return SpecificationHelper.SpecificationToQuery(specification, "images");
	}


	public Image ReadFromDb(IDatabaseResult dbResult) throws DatabaseException {
		Image image = new Image();
		
		image.id = dbResult.getInt("id");
		image.path = dbResult.getString("path");
		
		return image;
	}


	public IQuery ModelToQuery(Image model) {
		// TODO Auto-generated method stub
		return null;
	}

}