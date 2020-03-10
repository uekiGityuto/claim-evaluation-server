package jp.co.tokiomarine_nichido.resources;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import jp.co.tokiomarine_nichido.util.SQLUtil;
import jp.co.tokiomarine_nichido.util.WebArchiveBuilder;

@RunWith(Arquillian.class)
public class SpeechTextResourceSpec {
	@Deployment
	public static WebArchive createDeployment() {
		return WebArchiveBuilder.build();
	}

	@Inject
//	private SpeechTextResource testSubject;

	@Resource(lookup = "java:/PostgresDS")
	private DataSource ds;

	@BeforeClass
	public static void setup() {
		// TODO: 【山田】schema.sqlを実行してDBを初期化する。
	}

	@Before
	public void clearData() throws Exception {
		SQLUtil.truncateTable(ds, "SPEECH_TEXT");
	}

	@Test
	public void testGetSpeechText() throws Exception {

		// テストデータを登録
//		SpeechText speechtext = SQLUtil.createObject("/testdata/speechtext/speech2019-04-01-5001-6002.json", SpeechText.class);
//		testSubject.createSpeechText(speechtext);
//		speechtext = SQLUtil.createObject("/testdata/speechtext/speech2019-04-02-5001-6002.json", SpeechText.class);
//		testSubject.createSpeechText(speechtext);
//
//		List<SpeechText> speechText = testSubject.getSpeechText("5001", "2019-04-01", false, 5, 0, "");
//		
//		speechText.forEach(System.out::println);
//
//		assertEquals(1, speechText.size());
//		// speechtext.forEach(System.out::println);
//		// jsonで追加した2件のデータのうち、パラメータの条件に合致するデータは1件存在する。
//		assertEquals(1, speechText.size());
	}
}
