package forth.ics.isl.service;

import forth.ics.isl.impl.Impl;
import forth.ics.isl.utils.PropertiesManager;
import java.io.File;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/hello")
public class HelloWorldService {
    
    
    private PropertiesManager propertiesManager = PropertiesManager.getPropertiesManager();

    @GET
    public Response helloWorld(@QueryParam("name") String fileName,
                               @QueryParam("fcrepo") String fcrepo) throws IOException {
        
        String tomcatLocation = propertiesManager.getTomcatLocation();
        String fcrepoLocation = propertiesManager.getFcrepoLocation();

        int exitValue = Impl.fcrepoImportExport(fcrepoLocation, fcrepo);
        
        if(exitValue == 0)
            Impl.createZipFile(tomcatLocation, fileName);
        
        String filepath = tomcatLocation + fileName;
        File file = new File(filepath);
        
        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition","attachment; filename=" + fileName + ".zip");
        return response.build();
        
       // return Response.status(responseStatus.getStatus()).entity(responseStatus.getResponse()).build();
    }

}
