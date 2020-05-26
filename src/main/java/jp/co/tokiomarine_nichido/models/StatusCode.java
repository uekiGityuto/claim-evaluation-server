package jp.co.tokiomarine_nichido.models;

import javax.ws.rs.core.Response;

/**
 * StatusCode
 * @author SKK231099 李
 */
public class StatusCode {

    // 200
    static public Integer SUCCESS = Integer.valueOf(Response.Status.OK.toString());

    // 400
    static public Integer UNAUTHENTICATED = 401; // 未認証,Session
    static public Integer UNAUTHORIZED = Integer.valueOf(Response.Status.UNAUTHORIZED.toString()); // 権限なし
    static public Integer PRECONDITION_FAILED = Integer.valueOf(Response.Status.PRECONDITION_FAILED.toString()); // 412

    // 500
    static public Integer INTERNAL_SERVER_ERROR = Integer.valueOf(Response.Status.INTERNAL_SERVER_ERROR.toString());
    static public Integer EXTERNAL_SERVER_ERROR = 510;
    static public Integer EXECUTE_EXCEPTION = 520;
    static public Integer ENTITY_EXISTS_EXCEPTION = 521;
    static public Integer ILLEGAL_STATE_EXCEPTION = 522;
    static public Integer SQL_GRAMMAR_EXCEPTION = 523;
    static public Integer TRANSACTION_EXCEPTION = 524;
    static public Integer ROLLBACK_EXCEPTION = 525;
    static public Integer EXCLUSIVE_EXCEPTION = 526;

    StatusCode() {
    }
}
