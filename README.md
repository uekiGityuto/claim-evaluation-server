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

# eclipse
```
-vmargsより上部に下記のコードを記載
-vm
YourJavaDirectoryPath(javaw.exe)

日本語Version以外はWildfly起動後文字化けデータ（日本語）を転送する可能性あり。
```

# PostgreSQL
```
・ install postgreDB
・ DB作成： tmnf-fraud-detection
・ Schema作成： fraud-detection
・ name/passwordはpom.xmlのprofileを参照
・ 上記のユーザーの基本schemaを指定: fraud-detection
・ 権限付与

ex>
  create role muser
  GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA fraud-detection TO muser
  alter role muser set search_path to fraud-detection
```

# maven
```
・ 新規ファイルを作成　→　settings.xml

・ settings.xmlファイルの中に下記のコードを保存（YourMavenRepositoryPath変更）
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
<localRepository>YourMavenRepositoryPath</localRepository>
</settings>

・ eclipseのメニュー　→　ウィンドウ　→　設定　→　maven　→　ユーザー設定　→　参照（上記のファイルpath）
```

# install (local)
```
・ sourceのpom.xmlで右クリック　→　実行で実行の構成　→　ゴールに「package」記載
 (no test option when build: -Dmaven.test.skip=true)
・ sourceのpom.xmlで右クリック　→　実行でinstall
 (１回ではlibraryが全てDownloadされない場合がある。そうなると途中失敗になるのでエラー参照)
・ sourceのpom.xmlで右クリック　→　実行でbuild

・ targetフォルダにwildflyフォルダが存在することを確認。
   もし、なかった場合はcleanしてinstallを再実行
```

# Server
```
・ サーバー新規作成　→　Joss選択し、install → wildfly14.0
・ サーバーをダブルクリックし、属性画面を開く
・ ランタイム環境　→　ホーム・ディレクトリを上記のwildfly14.0に指定　→　次ぎ
・ リースース追加： fraud-detection
・ サーバー起動
```

# その他
```
AI判定RestAPIサーバーが存在しないため、用意される時まで
jsonサーバーなど自ら用意する必要がある。
そして、そのpathをpom.xmlに記載し起動させると連動できることになる。
pom.xml　→　url.fraudScoreに記載
現在のURL： http://localhost:3000/scores
データの構造はScoreモデルを参照
jsonサーバーがなくても正常にinstallできるようにRESTAPIDAOTestはコメント処理してある。
準備ができてから@Testを活性にすとinstall時に自動でテストを行うことになる。
claimIdは存在するものに変更する必要がある。
```

# エラー
```
・ installまたはbuild実行中にエラーになり次回の実行時に
　「STANDALONE server is already running?」エラーがみえた場合、
　　Windowsタスクマネージャーでjavaを強制終了させ再実行する必要がある。
　　（原因：既にサーバーが立ち上がっているため衝突）

・ たまにプロジェクトクリーンを要する場合がある。

```