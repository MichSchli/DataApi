package domains.tags;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseException;
import database.IDatabase;
import database.IDatabaseResult;
import database.query.IQuery;
import infrastructure.SpecificationHelper;
import infrastructure.repositories.BaseRepository;
import infrastructure.specifications.ISpecification;

public class TagRepository extends BaseRepository<Tag> {

	public TagRepository(IDatabase database) {
		super(database);
	}

	@Override
	public IQuery SpecificationsToQuery(ISpecification specification) {
		return SpecificationHelper.SpecificationToQuery(specification, "tags");
	}

	@Override
	public Tag ReadFromDb(IDatabaseResult dbResult) throws DatabaseException {
		Tag tag = new Tag();
		
		tag.id = dbResult.getInt("id");
		tag.name = dbResult.getString("name");
		
		return tag;
	}

	@Override
	public IQuery ModelToQuery(Tag model) {
		// TODO Auto-generated method stub
		return null;
	}


}
