package api;


import org.picocontainer.DefaultPicoContainer;

import database.IDatabase;
import database.mysql.MySqlDatabase;
import database.mysql.MySqlDatabaseConfiguration;
import domains.images.IImageRepository;
import domains.images.ImageDomain;
import domains.images.ImageTagFiller;
import domains.images.ImageTagMapper;
import domains.images.ImageRepository;
import domains.tags.ITagRepository;
import domains.tags.TagDomain;
import domains.tags.TagRepository;


public class ApiApplicationInstaller {
	private DefaultPicoContainer container;
	private ApiConfiguration configuration;

	public ApiApplicationInstaller(ApiConfiguration configuration) {
		this.configuration = configuration;
		this.container = new DefaultPicoContainer();
		
		InstallUtilityComponents(container);
		InstallDatabase(container);
		InstallRepositories(container);
		InstallDomains(container);
	}

	private void InstallDatabase(DefaultPicoContainer container) {
		MySqlDatabaseConfiguration config = new MySqlDatabaseConfiguration();
		
		config.Password = configuration.Password;//"1234";//"password";
		config.ServerName = configuration.Database;//"myserver";//"metadata";
		config.UserName = configuration.Username;//"testuser";//"pi";
		
		container.addComponent(MySqlDatabaseConfiguration.class, config);
		container.addComponent(IDatabase.class, MySqlDatabase.class);
	}
	
	private void InstallUtilityComponents(DefaultPicoContainer container) {
		// TODO Auto-generated method stub
		
	}
	
	private void InstallRepositories(DefaultPicoContainer container) {
		container.addComponent(ITagRepository.class, TagRepository.class);
		
		container.addComponent(ImageTagFiller.class);
		container.addComponent(ImageTagMapper.class);
		container.addComponent(IImageRepository.class, ImageRepository.class);
	}

	private void InstallDomains(DefaultPicoContainer contained){
		container.addComponent(TagDomain.class);
		container.addComponent(ImageDomain.class);
	}

	public <T> T GetComponent(Class<T> repositoryClass){
		return container.getComponent(repositoryClass);
	}
}
