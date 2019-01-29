package forth.ics.isl.service;

import forth.ics.isl.impl.Impl;
import forth.ics.isl.utils.PropertiesManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/exportBinary")
public class ExportBinary {
    
    
    private PropertiesManager propertiesManager = PropertiesManager.getPropertiesManager();

    @GET
    public Response export(@QueryParam("name") String fileName,
                               @QueryParam("fcrepo") String fcrepo) throws IOException {
        
        System.out.println("+++++++++++++++++++Name: "+ fileName);
        System.out.println("+++++++++++++++++++Fcrepo: "+ fcrepo);

        String finalFileName = fileName;
        
        String tomcatLocation = propertiesManager.getTomcatLocation();
        String fcrepoLocation = propertiesManager.getFcrepoLocation();
        
        String tomcatStorageLoc = tomcatLocation + "fcrepo/rest/";

        int exitValue = Impl.fcrepoImportExport(fcrepoLocation, fcrepo);
        
        System.out.println("+++++++++++++++++++exitValue: "+ exitValue);
        System.out.println("+++++++++++++++++++Paths: "+ Paths.get(tomcatStorageLoc + fcrepo)); 
       
        
        if(exitValue == 0)
            finalFileName = Impl.createZipFile(tomcatLocation, fileName, fcrepo);
        
        String filepath = tomcatLocation + finalFileName + ".zip";
        File file = new File(filepath);
        
        System.out.println("Returned filename:"+filepath);
        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition","attachment; filename=" + finalFileName + ".zip");
        return response.build();
        
       // return Response.status(responseStatus.getStatus()).entity(responseStatus.getResponse()).build();
    }

}
