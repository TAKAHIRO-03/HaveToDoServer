# GoodHabitsServer環境構築

## 環境構築
1. 事前にjava17のパスを通す。
```
$ ~ ./mvnw -v

Apache Maven 3.8.4 (9b656c72d54e5bacbed989b64718c159fe39b537)
Maven home: C:\Users\takac\.m2\wrapper\dists\apache-maven-3.8.4-bin\52ccbt68d252mdldqsfsn03jlf\apache-maven-3.8.4
Java version: 17.0.1, vendor: Oracle Corporation, runtime: C:\java\17
Default locale: ja_JP, platform encoding: MS932
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
```
2. PostgreSQLをインストールする。

[ダウンロードリンク](https://www.enterprisedb.com/postgresql-tutorial-resources-training?uuid=7c756686-90b4-4909-89ed-043e0705a76e&campaignId=7012J000001BfmaQAC)

3. PostgreSQLにデータベースとユーザー、パスワードを設定する。
```
DB名:goodhabits ※1
ユーザー名:ghuser ※2
パスワード:ghpass

※1 DBの所有者はghuserとする。
※2 ユーザーはSuperUserを有効化、ログインを有効化する。
```

4. 以下のコマンドでビルド及び起動をする。
```
$ ~ ./mvnw clean package spring-boot:run
```

## コンテナ化
1. Dockerをインストールする
```
$ ~ docker -v
Docker version 20.10.10, build b485636
```

2. 以下のコマンドを実行する。
```
$ ~ ./mvnw compile jib:dockerBuild
```

3. 以下のコマンドでイメージが作成出来たか確認する。
```
$ ~ docker images
REPOSITORY                       TAG                IMAGE ID       CREATED         SIZE
goodhabits/server                latest             b652e2fa6f55   52 years ago    325MB
```

## API仕様書
サーバーを起動すると以下にエンドポイントが表示される。
http://localhost:8080/swagger-ui/#/

## DB初期化
flywayのライブラリを導入しているので、以下のコマンドが使用できる。
```
$ ~ ./mvnw -Dflyway.configFiles=flyway.properties flyway:migrate # マイグレーション実行
$ ~ ./mvnw -Dflyway.configFiles=flyway.properties flyway:clean # DB初期化
```
