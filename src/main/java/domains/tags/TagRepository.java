package domains.tags;

import database.DatabaseException;
import database.IDatabase;
import database.IDatabaseResult;
import database.query.IQuery;
import infrastructure.repositories.BaseRepository;
import infrastructure.specifications.ISpecification;

public class TagRepository extends BaseRepository<Tag, TagSpecification> implements ITagRepository {

	public TagRepository(IDatabase database) {
		super(database);
	}

	@Override
	public IQuery SpecificationsToQuery(ISpecification specification) {
		return null;
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

	@Override
	public String getTable() {
		return "tags";
	}


}
