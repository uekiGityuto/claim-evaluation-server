package jp.co.tokiomarine_nichido.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.ws.rs.QueryParam;

import jp.co.tokiomarine_nichido.models.StringParameterUnmarshal.DateFormat;
import lombok.Data;

/**
 * Base Entity
 *
 * @author SKK231099 李
 *
 */
@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {

    @Column(name = "create_date", updatable = false)
    @QueryParam("createDate")
    @DateFormat
    protected Timestamp createDate;

    @Column(name = "update_date")
    @Version
    @QueryParam("updateDate")
    @DateFormat
    protected Timestamp updateDate;

    @PrePersist
    public void onCreate() {
        Timestamp now = new Timestamp((new Date()).getTime());
        updateDate = createDate = now;
    }

    @PreUpdate
    public void onUpdate() {
        Timestamp now = new Timestamp((new Date()).getTime());
        updateDate = now;
    }

    @Transient
    abstract public Object getPrimaryKey();

    @Transient
    public static Integer status = null;

}



