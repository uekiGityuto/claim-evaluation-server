package jp.co.tokiomarine_nichido.models;

import java.util.List;

/**
 * IF15事案一覧照会APIへのリクエストボディ
 *
 * @author SKK231527 植木宥登
 *
 */
public class ClaimsReqBody {
	private String REQ_USER_ID;
	private String CLAIMNUMBER;
	private List<ClaimCategory> CLAIMCATEGORYINFO;
	private List<InsuranceKind> INSURANCEKINDINFO;
	private String  FROMLOSSDATE;
	private String  TOLOSSDATE;
	private String  INSUREDNAMEKANA;
	private String  INSUREDNAMEKANJI;
	private String  CONTRACTORNAMEKANA;
	private String  CONTRACTORNAMEKANJI;
	private String  BUTENKANJI;
	private String  KYOTENKANJI;
	private String  LABELTYPE;
	private String  ORDER;
	private String  DISPLAYFROM;

	public ClaimsReqBody(String REQ_USER_ID, String CLAIMNUMBER, List<ClaimCategory> CLAIMCATEGORYINFO,
			List<InsuranceKind> INSURANCEKINDINFO, String FROMLOSSDATE, String TOLOSSDATE, String INSUREDNAMEKANA,
			String INSUREDNAMEKANJI, String CONTRACTORNAMEKANA, String CONTRACTORNAMEKANJI, String BUTENKANJI,
			String KYOTENKANJI, String LABELTYPE, String ORDER, String DISPLAYFROM) {
		REQ_USER_ID = REQ_USER_ID;
		CLAIMNUMBER = CLAIMNUMBER;
		CLAIMCATEGORYINFO = CLAIMCATEGORYINFO;
		INSURANCEKINDINFO = INSURANCEKINDINFO;
		FROMLOSSDATE = FROMLOSSDATE;
		TOLOSSDATE = TOLOSSDATE;
		INSUREDNAMEKANA = INSUREDNAMEKANA;
		INSUREDNAMEKANJI = INSUREDNAMEKANJI;
		CONTRACTORNAMEKANA = CONTRACTORNAMEKANA;
		CONTRACTORNAMEKANJI = CONTRACTORNAMEKANJI;
		BUTENKANJI = BUTENKANJI;
		KYOTENKANJI = KYOTENKANJI;
		LABELTYPE = LABELTYPE;
		ORDER = ORDER;
		DISPLAYFROM = DISPLAYFROM;
	}

}
