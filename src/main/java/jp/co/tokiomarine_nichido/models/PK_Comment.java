package jp.co.tokiomarine_nichido.models;

import java.io.Serializable;

public class PK_Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	private String claimId;
	private Integer idx;

	public PK_Comment() {}

	public PK_Comment(String claimId, Integer idx) {
		this.claimId = claimId;
		this.idx = idx;
	}

	public String getClaimId() {
		return claimId;
	}
	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}
	public Integer getIdx() {
		return idx;
	}
	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	public int hashCode() {
        return this.claimId.hashCode();
    }
	public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof PK_Comment)) return false;
        PK_Comment pk = (PK_Comment) obj;
        return pk.claimId.equals(this.claimId) && pk.idx == this.idx;
    }
}
