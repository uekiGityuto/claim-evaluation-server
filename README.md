# git
```
git clone <github uri>
git config --global --add user.name <name> ex> "Jesica"
git congig --global --add user.pasword <password> ex> "12345678"
git pull origin master
git add .
git commit -m "description"
git push origin master
```

# Java
```
Java 1.8
```

# maven
```
新規ファイルを作成　→　settings.xml

settings.xmlファイルの中に下記のコードを記載（yourFilePath変更）
<localRepository>yourFilePath</localRepository>

eclipseのメニュー　→　ウィンドウ　→　設定　→　maven　→　ユーザー設定　→　参照（上記のファイルpath）
```

# install (local)
```
sourceのpom.xmlで右クリック　→　実行で実行の構成　→　ゴールに「package」記載
sourceのpom.xmlで右クリック　→　実行でinstall
sourceのpom.xmlで右クリック　→　実行でbuild

targetフォルダにwildflyフォルダが存在することを確認。
もし、なかった場合はcleanしてinstallを再実行
```

# Server
```
サーバー新規作成　→　wildfly14.0
サーバーをダブルクリックし、属性画面を開く
ランタイム環境　→　ホーム・ディレクトリを上記のwildfly14.0に指定　→　完了
サーバー起動
```

# その他
```
AI判定RestAPIサーバーが存在しないため、用意される時まで
jsonサーバーなど自ら用意する必要がある。
そして、そのpathをpom.xmlに記載し起動させると連動できることになる。
pom.xml　→　url.fraudScoreに記載
現在のURL： http://localhost:3000/scores
データの構造はScoreモデルを参照
```

# エラー
```
installまたはbuild実行中にエラーになり
次回の実行時にSTANDALONE何とかのエラーがみえた場合、
Windowsタスクマネージャーでjavaを強制終了させ再実行する必要がある。
（原因：既にサーバーが立ち上がっているため衝突）

```