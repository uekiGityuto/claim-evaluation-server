package jp.co.tokiomarine_nichido.util;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

/**
 * Arquillianを使用したintegration testで使用するWebArchiveを作成。
 * 
 * @author SKK229873 中山真吾
 *
 */
public class WebArchiveBuilder {

	/**
	 * Arquillianを使用したintegration testで使用するWebArchiveを作成。
	 * 
	 * @return Arquillianを使用したintegration testで使用するWebArchive。
	 */
	public static WebArchive build() {
		File[] files = Maven.resolver().loadPomFromFile("pom.xml")
		        .importRuntimeDependencies().resolve().withTransitivity().asFile();
		
		WebArchive archive =  ShrinkWrap.create(WebArchive.class)
				.addPackages(true, "jp.co.tokiomarine_nichido")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsResource(new File("src/main/java/jp/co/tokiomarine_nichido/dbaccess/sql.properties"), "jp/co/tokiomarine_nichido/dbaccess/sql.properties")
				.addAsResource(new File("target/classes/config.properties"))
				.addAsResource(new File("target/classes/log4j2.xml"))
				.addAsLibraries(files);
		
		// デバッグ用
//		System.out.println(archive.toString(true));
		
		return archive;
	}
}
