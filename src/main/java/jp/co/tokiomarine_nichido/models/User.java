package jp.co.tokiomarine_nichido.models;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/***
 * ユーザ情報を格納するクラス。
 *
 * @author SKK229783 中山真吾
 *
 */
@Data
public class User {

    // ID
    public int id;

    // ユーザID
    @NotBlank
    public String userId;

    // メンバー名（フルネーム）
    @NotBlank
    public String name;

    // 権限（1:一般、2:管理者）
    public int role;

    public User() {
    }

    public User(int id, String userId, String name, int role) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.role = role;
    }
}
