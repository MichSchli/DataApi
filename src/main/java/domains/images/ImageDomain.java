package domains.images;

import java.util.HashMap;
import java.util.Map.Entry;

import domains.Domain;
import infrastructure.repositories.IRepository;

public class ImageDomain extends Domain<Image, ImageSpecification>{

	private IImageRepository repository;

	public ImageDomain(IImageRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public ImageSpecification getNewSpecification() {
		return new ImageSpecification();
	}

	@Override
	public IRepository<Image, ImageSpecification> getRepository() {
		return repository;
	}

	@Override
	public void addToSpecifications(ImageSpecification specifications, HashMap<String, String> queryHashmap) {
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
