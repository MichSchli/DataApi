package domains.images;

import database.DatabaseException;
import database.IDatabase;
import database.IDatabaseResult;
import database.query.IQuery;
import database.query.SelectQuery;
import infrastructure.repositories.BaseRepository;
import infrastructure.specifications.ISpecification;

public class ImageRepository extends BaseRepository<Image, ImageSpecification> implements IImageRepository{


	public ImageRepository(IDatabase database, ImageTagMapper imageTagMapper, ImageTagFiller imageTagFiller){
		super(database);
		addMapper(imageTagMapper);
		addFiller(imageTagFiller);
	}
	
	
	public IQuery SpecificationsToQuery(ISpecification specification) {
		SelectQuery query = new SelectQuery("id", getTable());
		return query;
	}


	public Image ReadFromDb(IDatabaseResult dbResult) throws DatabaseException {
		Image image = new Image();
		
		image.id = dbResult.getInt("id");
		image.path = dbResult.getString("path");
		
		return image;
	}


	public IQuery ModelToQuery(Image model) {
		return null;
	}


	@Override
	public String getTable() {
		return "images";
	}

}