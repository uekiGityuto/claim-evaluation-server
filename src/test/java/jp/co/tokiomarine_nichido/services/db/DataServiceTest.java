package jp.co.tokiomarine_nichido.services.db;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

//@RunWith(Arquillian.class)
public class DataServiceTest {

    public DataServiceTest() throws Exception {
        System.out.println("prepare files for DataService");
        ResourceBundle bundle = ResourceBundle.getBundle("config");
        String mode = bundle.getString("mode");
        String prjDir = System.getProperty("user.dir");
        String pgModule = "";

        System.out.println("Mode: " + mode );
        if ("local".equals(mode)) {
            String postgresqlVer = "42.2.6";
            String postgresqlJar = "postgresql-" + postgresqlVer + ".jar";
            StringBuilder fPath = new StringBuilder();
            StringBuilder module = new StringBuilder();
            StringBuilder fPathPgJar = new StringBuilder();
            fPath.append(prjDir);
            fPath.append("/target/wildfly-14.0.1.Final/modules/system/layers/base/org/postgresql/main/module.xml");
            module.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            module.append("<module name=\"org.postgresql\" xmlns=\"urn:jboss:module:1.5\">");
            module.append("<resources><resource-root path=\"" + postgresqlJar + "\"/></resources></module>");
            fPathPgJar.append(System.getProperty("localRepository"));
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

            pgModule = "<module name=\"org.postgresql\"/>";
        }

        StringBuilder fPathJbossXml = new StringBuilder();
        StringBuilder structureXml = new StringBuilder();
        fPathJbossXml.append(prjDir);
        fPathJbossXml.append("/src/main/webapp/WEB-INF/jboss-deployment-structure.xml");
        structureXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><jboss-deployment-structure>");
        structureXml.append("<deployment><dependencies><module name=\"org.dom4j\"/>");
        structureXml.append(pgModule);
        structureXml.append("</dependencies></deployment></jboss-deployment-structure>");
        Path pathXml = Paths.get(fPathJbossXml.toString());

        Files.createDirectories(pathXml.getParent());
        if (!(new File(pathXml.toString())).exists()) {
            Files.write(pathXml, structureXml.toString().getBytes());
        }
    }

    @Deployment
    public static JavaArchive createDeployment() {

        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClass(DataService.class)
                .addPackage("org.arquillian.example")
                .addPackage("org.arquillian.annotation")
                .addPackage("org.arquillian.interceptor")
                .addAsManifestResource(new File("src/main/webapp/WEB-INF/beans.xml"));
        System.out.println("■ CreateDeployment: " + jar.toString(true));
        return jar;
    }

}
