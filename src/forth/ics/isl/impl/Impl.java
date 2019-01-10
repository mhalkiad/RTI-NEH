/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forth.ics.isl.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author mhalkiad
 */
public class Impl {
    
    public static int fcrepoImportExport(String fcrepoLocation, String fcrepo) {
        
        try {
            Process p = Runtime.getRuntime().exec("java -jar fcrepo-import-export-0.3.0-SNAPSHOT.jar --mode export --resource " + fcrepoLocation + fcrepo + "--dir . --binaries");
            p.waitFor();
            return p.exitValue();
      
        }
        
        catch(IOException ioe) {Logger.getLogger(Impl.class.getName()).log(Level.SEVERE, null, ioe);}
        catch(InterruptedException ie) {Logger.getLogger(Impl.class.getName()).log(Level.SEVERE, null, ie);}

      return -1;
    }
    
    
    public static void createZipFile(String tomcatLocation, String fileName) throws IOException {
    
        if(Files.exists(Paths.get(tomcatLocation + fileName)) == true)
            fileName += new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        
        Path zipFilePath = Files.createFile(Paths.get(tomcatLocation + fileName));

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFilePath))) {
            Path sourceDirPath = Paths.get(tomcatLocation + "fcrepo");

            Files.walk(sourceDirPath).filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(sourceDirPath.relativize(path).toString());
                        try {
                            zipOutputStream.putNextEntry(zipEntry);
                            zipOutputStream.write(Files.readAllBytes(path));
                            zipOutputStream.closeEntry();
                        } catch (Exception e) {
                            System.err.println(e);
                        }
                    });
        }
    }
        
}
