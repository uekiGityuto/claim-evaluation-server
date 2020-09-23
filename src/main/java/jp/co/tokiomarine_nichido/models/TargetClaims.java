package jp.co.tokiomarine_nichido.models;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * 事案一覧照会のリクエストボディをマッピングするBean
 *
 * @author SKK231527 植木宥登
 *
 */
@XmlRootElement
public class TargetClaims {

	// JAX-RSでアンマーシャリングするためにpublicにする必要がある
	public String REQ_USER_ID;
	public String CLAIMNUMBER;
	public List<ClaimCategory> CLAIMCATEGORYINFO;
	public List<InsuranceKind> INSURANCEKINDINFO;
	public String  FROMLOSSDATE;
	public String  TOLOSSDATE;
	public String  INSUREDNAMEKANA;
	public String  INSUREDNAMEKANJI;
	public String  CONTRACTORNAMEKANA;
	public String  CONTRACTORNAMEKANJI;
	public String  BUTENKANJI;
	public String  KYOTENKANJI;
	public String  LABELTYPE;
	public String  ORDER;
	public String  DISPLAYFROM;
}
