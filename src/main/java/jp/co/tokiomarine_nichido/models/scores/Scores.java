package jp.co.tokiomarine_nichido.models.scores;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement
public class Scores {

	@SerializedName("CLAIM")
    public DetailClaim claim;

}
