# 機能概要
Kindle Scribeでnoteを共有する場合、メールで送付する共有しかなく、
Evernoteへ直接共有することができない。

そこで、Evernoteのインポートフォルダー機能とGmail APIを使用して、Kindle ScribeからGmailへ共有されたnote情報を自動でEvernoteにインポートするバッチを作成。

Evernoteのインポートフォルダー機能は、Evernoteの機能でローカルマシンの特定フォルダにファイルを置くと、自動でEvernoteにインポートする機能である。その機能を使用して、Gmail APIを使用したバッチがGmailからEvernoteの共有されたメールを取得して、ファイルをEvernoteにインポートされる特定のフォルダーに置く。

# 機能詳細
## ダウンロードURL取得
Kindle Scribeから共有されるメールには、noteファイル（PDF）をダウンロードするURL情報があるため、ファイルをダウンロードするために、そのURLを取得する。

## ダウンロード
Evernoteのインポートフォルダー機能は、指定したフォルダー毎にEvernoteのどのブックにインポートするか設定が可能である。その機能を使用して、フォルダーへの振り分け機能を追加する。  
  
振り分ける情報としてはKindle Scribeのノート名の先頭1文字に振り分け用の情報をセットする。形式としては、ローマ字1文字カンマをノートの先頭にセットしてもらう。振り分け未指定の場合は、指定のなかったフォルダーに置くこととする。

```
d　ダイアリー用フォルダー
i　情報用フォルダー
w　仕事用フォルダー
m　指定なかった場合フォルダー
```

## 設定情報
以下の２つのテーブルを作成して、設定値を設定する。


#### 設定値テーブル
- APPLICATION_NAME：Gmail APIで設定したアプリケーション名
- CREDENTIALS_JSON：Gmail APIの認証情報 
- TOKENS_DIRECTORY_PATH：Gmail利用時にトークン情報を格納するパス
- MAIL_USER_ID：Gmailメールの利用アカウント
- SENDER_EMAIL：Kindle Scribe共有時のメール送信元
```
CREATE TABLE public.m_settings
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying(1024) COLLATE pg_catalog."default" NOT NULL,
    value character varying(1024) COLLATE pg_catalog."default" NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    version integer NOT NULL DEFAULT 1,
    CONSTRAINT m_settings_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.m_settings
    OWNER to postgres;
```

#### ダウンロード先テーブル
initalに振り分け用の1文字、pathにダウンロードファイル先のフォルダーを設定。複数設定することが可能。

```
CREATE TABLE public.m_output_pdf_paths
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    initial character varying(1) COLLATE pg_catalog."default" NOT NULL,
    path character varying(2056) COLLATE pg_catalog."default" NOT NULL,
    created_at time with time zone NOT NULL,
    updkated_at time with time zone NOT NULL,
    version integer NOT NULL DEFAULT 1,
    CONSTRAINT m_output_pdf_paths_pkey PRIMARY KEY (id, initial)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.m_output_pdf_paths
    OWNER to postgres;
```