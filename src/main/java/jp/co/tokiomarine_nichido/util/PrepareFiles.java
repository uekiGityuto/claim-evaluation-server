package jp.co.tokiomarine_nichido.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class PrepareFiles {

    public static void main(String[] args) throws IOException {
        prepareFiles();
    }

    public static void prepareFiles() throws IOException {
        System.out.println("prepare files for DataService");
        ResourceBundle bundle = ResourceBundle.getBundle("config");
        String mode = bundle.getString("mode");
        String prjDir = System.getProperty("user.dir");
        String pgModule = "";

        System.out.println("Mode: " + mode );
        if ("local".equals(mode)) {
            String moduleName = "org.postgresql";
            String postgresqlVer = "42.2.6";
            String postgresqlJar = "postgresql-" + postgresqlVer + ".jar";
            StringBuilder fPath = new StringBuilder();
            StringBuilder module = new StringBuilder();
            StringBuilder fPathPgJar = new StringBuilder();
            PropertyManager pm = new PropertyManager();
            fPath.append(prjDir);
            fPath.append("/target/wildfly-14.0.1.Final/modules/system/layers/base/org/postgresql/main/module.xml");
            module.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            module.append("<module name=\"" + moduleName + "\" xmlns=\"urn:jboss:module:1.5\">");
            module.append("<resources><resource-root path=\"" + postgresqlJar + "\"/></resources></module>");
            fPathPgJar.append(pm.get("local.repository"));
            fPathPgJar.append("/org/postgresql/postgresql/" + postgresqlVer + "/" + postgresqlJar);
            Path path = Paths.get(fPath.toString());
            Path pathJar = Paths.get(fPathPgJar.toString());
            Path pathTargetJar = Paths.get(path.getParent().toString() + "/" + postgresqlJar);
            Files.createDirectories(path.getParent());
            if (!(new File(path.toString())).exists()) {
                Files.write(path, module.toString().getBytes());
            }
            if (!(new File(pathTargetJar.toString())).exists()) {
                Files.copy(pathJar, pathTargetJar);
            }
            pgModule = "<module name=\"" + moduleName +"\"/>";
        }

        StringBuilder fPathJbossXml = new StringBuilder();
        StringBuilder structureXml = new StringBuilder();
        fPathJbossXml.append(prjDir);
        fPathJbossXml.append("/src/main/webapp/WEB-INF/jboss-deployment-structure.xml");
        structureXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        structureXml.append("<jboss-deployment-structure xmlns=\"urn:jboss:deployment-structure:1.1\">");
        structureXml.append("<deployment><dependencies><module name=\"org.dom4j\"/>");
        structureXml.append(pgModule);
        structureXml.append("</dependencies></deployment></jboss-deployment-structure>");
        Path pathXml = Paths.get(fPathJbossXml.toString());
        Files.createDirectories(pathXml.getParent());
        if (!(new File(pathXml.toString())).exists()) {
            Files.write(pathXml, structureXml.toString().getBytes());
        }
    }
}
