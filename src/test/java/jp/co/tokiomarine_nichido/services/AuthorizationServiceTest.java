package jp.co.tokiomarine_nichido.services;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.junit.Test;

// TODO: CDIを使用したテストを出来るようにする（Arquillian）
// TODO: Junit5でArquillianを使用できるようにする
// TODO: サンプルの問題でURL生成時刻のチェックNGになるのでテスト方法を工夫する
//@RunWith(Arquillian.class)
@RunAsClient
public class AuthorizationServiceTest {

	@Test
	public void AuthorizeTest() {
		AuthorizationService authorizeService = new AuthorizationService();
		String encryptedParam = "14DE3FE9D8A4B354C1D6EF0984491E6558E00D31D55D4B51AB0FD6083E34B33AA71354E7EE182ED7BCA37C2736AA5C1E15FB37131F20EC02C988176E0438EFE2D20B21B268D9D7120A8A32E56C19E64E";
		String userId = "AAA123456";
//		String authResult = authorizeService.authorize(encryptedParam, userId);
//		System.out.println(authResult);
	}

}
