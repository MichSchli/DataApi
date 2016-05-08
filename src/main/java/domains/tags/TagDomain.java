package domains.tags;

import java.util.HashMap;
import java.util.Map.Entry;

import domains.Domain;
import infrastructure.repositories.IRepository;

public class TagDomain extends Domain<Tag, TagSpecification>{

	private ITagRepository repository;

	public TagDomain(ITagRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public TagSpecification getNewSpecification() {
		return new TagSpecification();
	}

	@Override
	public IRepository<Tag, TagSpecification> getRepository() {
		return repository;
	}

	@Override
	public void addToSpecifications(TagSpecification specifications, HashMap<String, String> queryHashmap) {
		for (Entry<String, String> entry : queryHashmap.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    
		    String[] valueParts = value.split(",");
		    
		    switch(key){
		    case "id":
		    	for (String string : valueParts) {
					specifications.addId(Integer.parseInt(string));
				}
		    default:
		    	continue;
		    }
		}
	}


}
