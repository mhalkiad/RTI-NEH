package forth.ics.isl.config;

import org.glassfish.jersey.server.ResourceConfig;

public class App extends ResourceConfig {
	public App() {
        // Define the package which contains the service classes.
        packages("forth.ics.isl.service");
    }
}
