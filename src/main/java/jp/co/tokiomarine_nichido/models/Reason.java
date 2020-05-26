package jp.co.tokiomarine_nichido.models;

import java.util.Map;

import lombok.Data;

/**
 * Scoreに影響を与えた要因情報
 *
 * @author SKK231099 李
 */
@Data
public class Reason {
    private String factor;
    private Integer effect;

    public Reason(Map<String, Object> obj) {
        this.factor = String.valueOf(obj.get("factor"));
        this.effect = Math.round(Float.valueOf(String.valueOf(obj.get("effect"))));
    }

}
