package jp.co.tokiomarine_nichido.models;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.tokiomarine_nichido.util.DateUtil;
import lombok.Data;

/**
 * Claim Model
 *
 * @author SKK231099 李
 */

@Data
public class Claim {

    private String claimId;
    private String userName;
    private String name;
    private String kind;
    private Date birthday;
    private Timestamp occurenceDate;
    private Timestamp createDate;
    private Timestamp updateDate;

    private List<Comment> commentList;

    private DateUtil dateUtil;

    public Claim () {
        commentList = new ArrayList<Comment>();
    }
    public Claim (    String claimId,
                    String userName,
                    String name,
                    String kind,
                    Date birthday,
                    Timestamp occurenceDate,
                    Timestamp createDate,
                    Timestamp updateDate) {
        this.claimId = claimId;
        this.userName = userName;
        this.name = name;
        this.kind = kind;
        this.birthday = birthday;
        this.occurenceDate = occurenceDate;
        this.createDate = createDate;
        this.updateDate = updateDate;
        commentList = new ArrayList<Comment>();
    }
    public Claim (Map<String, Object> obj) {
        this.claimId = String.valueOf(obj.get("claimId"));
        this.userName = String.valueOf(obj.get("userName"));
        this.name = String.valueOf(obj.get("name"));
        this.kind = String.valueOf(obj.get("kind"));
        dateUtil = new DateUtil();
        this.birthday = dateUtil.toDate(String.valueOf(obj.get("birthday")));
        this.occurenceDate = dateUtil.toTimestamp(String.valueOf(obj.get("occurenceDate")));
        this.createDate = dateUtil.toTimestamp(String.valueOf(obj.get("createDate")));
        this.updateDate = dateUtil.toTimestamp(String.valueOf(obj.get("updateDate")));
        commentList = new ArrayList<Comment>();
    }

}