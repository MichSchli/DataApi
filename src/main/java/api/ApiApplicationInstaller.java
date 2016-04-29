package api;


import org.picocontainer.DefaultPicoContainer;

import infrastructure.repositories.IRepository;


public class ApiApplicationInstaller {
	private DefaultPicoContainer container;

	public ApiApplicationInstaller() {
		
		this.container = new DefaultPicoContainer();
		
		InstallUtilityComponents(container);
		
		InstallRepositories(container);
	}

	private void InstallRepositories(DefaultPicoContainer container2) {
		// TODO Auto-generated method stub
		
	}

	private void InstallUtilityComponents(DefaultPicoContainer container2) {
		// TODO Auto-generated method stub
		
	}

	public <T> T GetComponent(Class<T> repositoryClass){
		return container.getComponent(repositoryClass);
	}
}
