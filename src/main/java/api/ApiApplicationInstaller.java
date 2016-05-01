package api;


import org.picocontainer.DefaultPicoContainer;

import database.IDatabase;
import database.mysql.MySqlDatabase;
import database.mysql.MySqlDatabaseConfiguration;
import domains.images.ImageRepository;
import domains.tags.TagRepository;


public class ApiApplicationInstaller {
	private DefaultPicoContainer container;

	public ApiApplicationInstaller() {
		
		this.container = new DefaultPicoContainer();
		
		InstallUtilityComponents(container);
		InstallDatabase(container);
		InstallRepositories(container);
	}

	private void InstallDatabase(DefaultPicoContainer container) {
		MySqlDatabaseConfiguration config = new MySqlDatabaseConfiguration();
		
		config.Password = "password";
		config.ServerName = "metadata";
		config.UserName = "pi";
		
		container.addComponent(MySqlDatabaseConfiguration.class, config);
		container.addComponent(IDatabase.class, MySqlDatabase.class);
	}
	
	private void InstallUtilityComponents(DefaultPicoContainer container) {
		// TODO Auto-generated method stub
		
	}
	
	private void InstallRepositories(DefaultPicoContainer container) {
		container.addComponent(ImageRepository.class);
		container.addComponent(TagRepository.class);
	}


	public <T> T GetComponent(Class<T> repositoryClass){
		return container.getComponent(repositoryClass);
	}
}
