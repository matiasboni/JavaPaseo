package config;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.server.ResourceConfig;

public class AppConfig extends ResourceConfig {
	 public AppConfig() {
		 packages("api");
		 ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();
		 ServiceLocatorUtilities.bind(locator, new AppBinder());
		 ServiceLocatorUtilities.dumpAllDescriptors(locator);
		 //register(new AppBinder());
	}

}
