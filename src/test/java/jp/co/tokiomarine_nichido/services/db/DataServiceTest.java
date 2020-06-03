package jp.co.tokiomarine_nichido.services.db;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class DataServiceTest {

    @Test
    public void testGetFeedbacks() throws Exception {
        System.out.println("prepare postgresql jar");
        String postgresqlVer = "42.2.6";
        String postgresqlJar = "postgresql-" + postgresqlVer + ".jar";
        StringBuilder fPath = new StringBuilder();
        StringBuilder module = new StringBuilder();
        StringBuilder fPathPgJar = new StringBuilder();
        StringBuilder fPathJbossXml = new StringBuilder();
        StringBuilder structureXml = new StringBuilder();
        String prjDir = System.getProperty("user.dir");
        fPath.append(prjDir);
        fPath.append("/target/wildfly-14.0.1.Final/modules/system/layers/base/org/postgresql/main/module.xml");
        module.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        module.append("<module name=\"org.postgresql\" xmlns=\"urn:jboss:module:1.5\">");
        module.append("<resources><resource-root path=\"" + postgresqlJar + "\"/></resources></module>");
        fPathPgJar.append(System.getProperty("localRepository"));
        fPathPgJar.append("/org/postgresql/postgresql/" + postgresqlVer + "/" + postgresqlJar);
        fPathJbossXml.append(prjDir);
        fPathJbossXml.append("/src/main/webapp/WEB-INF/jboss-deployment-structure.xml");
        structureXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><jboss-deployment-structure>");
        structureXml.append("<deployment><dependencies><module name=\"org.dom4j\"/><module name=\"org.postgresql\"/>");
        structureXml.append("</dependencies></deployment></jboss-deployment-structure>");
        Path path = Paths.get(fPath.toString());
        Path pathXml = Paths.get(fPathJbossXml.toString());
        Path pathJar = Paths.get(fPathPgJar.toString());
        Path pathTargetJar = Paths.get(path.getParent().toString() + "/" + postgresqlJar);

        Files.createDirectories(path.getParent());
        if (!(new File(path.toString())).exists()) {
            Files.write(path, module.toString().getBytes());
        }
        if (!(new File(pathTargetJar.toString())).exists()) {
            Files.copy(pathJar, pathTargetJar);
        }

        Files.createDirectories(pathXml.getParent());
        if (!(new File(pathXml.toString())).exists()) {
            Files.write(pathXml, structureXml.toString().getBytes());
        }

    }

}
