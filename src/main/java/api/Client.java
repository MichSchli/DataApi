package api;

import java.util.List;

import domains.Domain;
import domains.images.ImageDomain;
import domains.tags.TagDomain;
import infrastructure.IApiClient;
import infrastructure.IModel;
import infrastructure.repositories.IRepository;

public class Client implements IApiClient{

	private ImageDomain imageDomain;
	private TagDomain tagDomain;

	public Client(ApiConfiguration configuration){
		if (configuration == null){
			throw new IllegalArgumentException("API configuration was null");
		}
		
		ApiApplicationInstaller installer = new ApiApplicationInstaller(configuration);
		this.imageDomain = installer.GetComponent(ImageDomain.class);
		this.tagDomain = installer.GetComponent(TagDomain.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<IModel> Search(ApiRequest request){
		IRepository repository = request.domain.getRepository();
		
		List<Integer> ids;
		if (request.specifications.isIdentifierLookup()){
			ids = request.specifications.getIds();
		} else{
			ids = repository.Search(request.specifications);		
		}
		
		List<IModel> domainModels = repository.Retrieve(ids);
		
		return domainModels;
	}
	
	public Domain<?,?> getDomain(String domainName){
		switch(domainName){
			case "images":
				return imageDomain;
			case "tags":
				return tagDomain;
			default:
				return null;
		}
	}
}
