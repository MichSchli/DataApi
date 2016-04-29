package domains.images;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.IDatabase;
import database.query.IQuery;
import infrastructure.SpecificationHelper;
import infrastructure.repositories.BaseRepository;
import infrastructure.specifications.ISpecification;

public class ImageRepository extends BaseRepository<Image> {

	public ImageRepository(IDatabase database){
		super(database);
	}
	
	@Override
	public IQuery SpecificationsToQuery(ISpecification specification) {
		return SpecificationHelper.SpecificationToQuery(specification, "images");
	}


	@Override
	public Image ReadFromDb(ResultSet dbResult) throws SQLException {
		Image image = new Image();
		
		image.id = dbResult.getInt("id");
		image.path = dbResult.getString("path");
		
		return image;
	}


	@Override
	public IQuery ModelToQuery(Image model) {
		// TODO Auto-generated method stub
		return null;
	}

}