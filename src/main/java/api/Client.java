package api;

import java.util.List;

import domains.Domain;
import domains.images.ImageRepository;
import infrastructure.IApiClient;
import infrastructure.IModel;
import infrastructure.repositories.IRepository;

public class Client implements IApiClient{

	protected ImageRepository imageRepository;

	public Client(){
		ApiApplicationInstaller installer = new ApiApplicationInstaller();
		this.imageRepository = installer.GetComponent(ImageRepository.class);
	}
	
	public List<IModel> Search(ApiRequest request){
		@SuppressWarnings("rawtypes")
		IRepository repository = GetRepository(request.domain);
		
		System.out.println(request.specifications);
		@SuppressWarnings("unchecked")
		List<IModel> domainModels = repository.Retrieve(request.specifications);
		
		return domainModels;
	}

	private IRepository<?> GetRepository(Domain domain) {
		switch (domain) {
		case Images:
			return imageRepository;

		default:
			return null;
		}
	}

}
