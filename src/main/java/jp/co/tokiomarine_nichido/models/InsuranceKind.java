package jp.co.tokiomarine_nichido.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 事案一覧照会のリクエストボディをマッピングするBean
 * （publicなフィールド、または、privateなフィールド+getterのみにする必要がある）
 *
 * @author SKK231527 植木宥登
 *
 */
@XmlRootElement
public class InsuranceKind {

	public String INSURANCEKIND;
}
