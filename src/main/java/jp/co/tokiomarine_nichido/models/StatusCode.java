package jp.co.tokiomarine_nichido.models;

import javax.ws.rs.ext.Provider;

/**
 * StatusCode
 * @author SKK231099 李
 */
@Provider
public class StatusCode {

    // 200
    public final static Integer SUCCESS;

    // 400
    public final static Integer UNAUTHENTICATED; // 未認証,Session
    public final static Integer UNAUTHORIZED; // 権限なし
    public final static Integer PRECONDITION_FAILED; // 412

    // 500
    public final static Integer INTERNAL_SERVER_ERROR;
    public final static Integer EXTERNAL_SERVER_ERROR;
    public final static Integer EXECUTE_EXCEPTION;
    public final static Integer ENTITY_EXISTS_EXCEPTION;
    public final static Integer ILLEGAL_STATE_EXCEPTION;
    public final static Integer SQL_GRAMMAR_EXCEPTION;
    public final static Integer TRANSACTION_EXCEPTION;
    public final static Integer ROLLBACK_EXCEPTION;
    public final static Integer EXCLUSIVE_EXCEPTION;

    static {
        SUCCESS = 200;//Integer.valueOf(Response.Status.OK.toString());
        UNAUTHENTICATED = 401;
        UNAUTHORIZED = 403; //Integer.valueOf(Response.Status.UNAUTHORIZED.toString());
        PRECONDITION_FAILED = 412; //Integer.valueOf(Response.Status.PRECONDITION_FAILED.toString()); // 412
        INTERNAL_SERVER_ERROR = 500; //Integer.valueOf(Response.Status.INTERNAL_SERVER_ERROR.toString());
        EXTERNAL_SERVER_ERROR = 510;
        EXECUTE_EXCEPTION = 520;
        ENTITY_EXISTS_EXCEPTION = 521;
        ILLEGAL_STATE_EXCEPTION = 522;
        SQL_GRAMMAR_EXCEPTION = 523;
        TRANSACTION_EXCEPTION = 524;
        ROLLBACK_EXCEPTION = 525;
        EXCLUSIVE_EXCEPTION = 526;
    }

}
