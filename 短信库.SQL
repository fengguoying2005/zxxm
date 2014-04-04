CREATE database link SW_SMS_ZJK
CONNECT TO UTAX IDENTIFIED BY pass123
USING ' (DESCRIPTION =
    (ADDRESS_LIST =
        (ADDRESS = (PROTOCOL = TCP)(HOST = 192.168.128.136)(PORT = 1521))
    )
    (CONNECT_DATA =
        (SERVICE_NAME = swzjk)
    )
) '


CREATE OR REPLACE SYNONYM T_SMS_SFCJTXMX FOR T_SMS_SFCJTXMX@SW_SMS_ZJK;
CREATE OR REPLACE SYNONYM T_SMS_SFCBTXMX FOR T_SMS_SFCBTXMX@SW_SMS_ZJK;
CREATE OR REPLACE SYNONYM T_SMS_JKTX FOR T_SMS_JKTX@SW_SMS_ZJK;
CREATE OR REPLACE SYNONYM T_SMS_GMFPTX FOR T_SMS_GMFPTX@SW_SMS_ZJK;
CREATE OR REPLACE SYNONYM T_SMS_KKSBTX FOR T_SMS_KKSBTX@SW_SMS_ZJK;
CREATE OR REPLACE SYNONYM T_SMS_TYDQTX FOR T_SMS_TYDQTX@SW_SMS_ZJK;
CREATE OR REPLACE SYNONYM T_SMS_FPXX FOR T_SMS_FPXX@SW_SMS_ZJK;
CREATE OR REPLACE SYNONYM T_SMS_SFXX FOR T_SMS_SFXX@SW_SMS_ZJK;
CREATE OR REPLACE SYNONYM T_SMS_FPZL FOR T_SMS_FPZL@SW_SMS_ZJK;
CREATE OR REPLACE SYNONYM T_SMS_ZSXM FOR T_SMS_ZSXM@SW_SMS_ZJK;
CREATE OR REPLACE SYNONYM T_SMS_ZSPM FOR T_SMS_ZSPM@SW_SMS_ZJK;
CREATE OR REPLACE SYNONYM NOWDATE FOR NOWDATE@SW_SMS_ZJK;


--修改结构
alter table SMS_INBOX add js_sj VARCHAR2(30) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss');
alter table SMS_INBOX add type_dm VARCHAR2(30);
alter table SMS_INBOX add EXT1 VARCHAR2(30);
alter table SMS_INBOX add EXT2 VARCHAR2(30);
alter table SMS_INBOX add EXT3 VARCHAR2(30);
alter table SMS_INBOX add EXT4 VARCHAR2(30);
alter table SMS_INBOX add EXT5 VARCHAR2(30);
create index IDX_SMS_INBOX_sjtype on SMS_INBOX (js_sj, type_dm);
create index IDX_SMS_INBOX_KZM on SMS_INBOX (kzm);


--新函数
CREATE OR REPLACE FUNCTION lob_to_char(clob_col CLOB) RETURN VARCHAR2 IS
  buffer VARCHAR2(4000);
  amt    BINARY_INTEGER := 4000;
  pos    INTEGER := 1;
  l      CLOB;
  bfils  BFILE;
  l_var  VARCHAR2(4000) := '';
BEGIN
  LOOP
    IF dbms_lob.getlength(clob_col) <= 4000 THEN
      dbms_lob.READ(clob_col, amt, pos, buffer);
      l_var := l_var || buffer;
      pos   := pos + amt;
    ELSE
      l_var := 'Cannot convert to varchar2..Exceeding varchar2 field limit';
      EXIT;
    END IF;
  END LOOP;
  RETURN l_var;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RETURN l_var;
END;

--新表
create table DM_SJSX
(
  sjsx_dm VARCHAR2(3) not null,
  sjsx_mc VARCHAR2(20)
);
comment on column DM_SJSX.sjsx_dm  is '市局缩写代码';
comment on column DM_SJSX.sjsx_mc  is '市局缩写名称';
alter table DM_SJSX  add constraint PK_DM_SJSX primary key (SJSX_DM);


create table DM_JKLX
(
  jklx_dm VARCHAR2(4) not null,
  jklx_mc VARCHAR2(100)
);
alter table DM_JKLX  add constraint PK_DM_JKLX primary key (JKLX_DM);


create table DM_DXLX
(
  dxlx_dm VARCHAR2(20) not null,
  dxlx_mc VARCHAR2(50)
);
alter table DM_DXLX  add constraint PK_DM_DXLX primary key (DXLX_DM);


create table DM_DXYS2
(
  dxys_dm VARCHAR2(50) not null,
  dxys_mc VARCHAR2(100),
  yx_bj   VARCHAR2(1)
);
alter table DM_DXYS2  add constraint PK_DM_DXYS2 primary key (DXYS_DM);


create table DM_JSRLX
(
  dxjsrlx_dm VARCHAR2(20) not null,
  dxjsrlx_mc VARCHAR2(100)
);
alter table DM_JSRLX  add constraint PK_DM_DXJSRLX primary key (DXJSRLX_DM);


create table DX_XYDZ
(
  lsh     VARCHAR2(36) not null,
  nsrbm   VARCHAR2(50),
  nsrmc   VARCHAR2(500),
  org_dm  VARCHAR2(50),
  cbtx    VARCHAR2(1),
  cjtx    VARCHAR2(1),
  jktx    VARCHAR2(1),
  jksbtx  VARCHAR2(1),
  gmfptx  VARCHAR2(1),
  tydqtx  VARCHAR2(1),
  ktrq_q  DATE,
  ktrq_z  DATE,
  lrry_dm VARCHAR2(20),
  xgry_dm VARCHAR2(20),
  lr_sj   DATE,
  xg_sj   DATE,
  bz      VARCHAR2(300)
);
comment on table DX_XYDZ  is '协议定制';
alter table DX_XYDZ  add constraint PK_DX_XYDZ primary key (LSH);
create index IDX_DX_XYDZ_JG on DX_XYDZ (ORG_DM);
create index IDX_DX_XYDZ_MC on DX_XYDZ (NSRMC);
create index IDX_DX_XYDZ_NSR on DX_XYDZ (NSRBM);
create index IDX_DX_XYDZ_RQ on DX_XYDZ (KTRQ_Q, KTRQ_Z);


create table DX_WJDCMX
(
  lsh       VARCHAR2(36) not null,
  zb_lsh    VARCHAR2(36),
  sjhm      VARCHAR2(20),
  nsrbm     VARCHAR2(20),
  nsrmc     VARCHAR2(200),
  smsinfo   VARCHAR2(500),
  lr_sj     DATE,
  dxzt_dm   VARCHAR2(2),
  fk_sj     DATE,
  phonetype VARCHAR2(20)
);
comment on column DX_WJDCMX.lsh  is '主键';
comment on column DX_WJDCMX.zb_lsh  is '主表流水号';
comment on column DX_WJDCMX.sjhm  is '手机号码';
comment on column DX_WJDCMX.nsrbm  is '纳税人编码';
comment on column DX_WJDCMX.nsrmc  is '纳税人名称';
comment on column DX_WJDCMX.smsinfo  is '短信内容';
comment on column DX_WJDCMX.lr_sj  is '录入时间';
comment on column DX_WJDCMX.dxzt_dm  is '短信状态 no use';
comment on column DX_WJDCMX.fk_sj  is '反馈时间';
alter table DX_WJDCMX  add constraint PK_DX_WJDCMX primary key (LSH);
create index IDX_DX_WJDCMX_SJHM on DX_WJDCMX (SJHM);
create index IDX_DX_WJDCMX_ZBLSH on DX_WJDCMX (ZB_LSH);


create table DX_WJDC
(
  lsh     VARCHAR2(36) not null,
  info    VARCHAR2(500),
  bz      VARCHAR2(100),
  swjgbm  VARCHAR2(20),
  sfjs    VARCHAR2(1),
  lr_sj   DATE,
  xg_sj   DATE,
  lrry_dm VARCHAR2(20),
  xgry_dm VARCHAR2(20),
  dcjl    VARCHAR2(500)
);
comment on column DX_WJDC.lsh  is '主键';
comment on column DX_WJDC.info  is '调查问题';
comment on column DX_WJDC.bz  is '备注';
comment on column DX_WJDC.swjgbm  is '税务机关编码';
comment on column DX_WJDC.sfjs  is '调查是否结束';
comment on column DX_WJDC.lr_sj  is '录入时间';
comment on column DX_WJDC.xg_sj  is '修改时间';
comment on column DX_WJDC.lrry_dm  is '录入人员';
comment on column DX_WJDC.xgry_dm  is '修改人员';
comment on column DX_WJDC.dcjl  is '调查结论';
alter table DX_WJDC  add constraint PK_DX_WJDC primary key (LSH);
create index IDX_DX_WJDC_TZM on DX_WJDC (BZ);


create table DX_TYDQTX
(
  lsh      VARCHAR2(36) not null,
  nsrsbm   VARCHAR2(50),
  nsrmc    VARCHAR2(500),
  sjhm     VARCHAR2(11),
  smsinfo  VARCHAR2(500),
  lr_sj    DATE,
  tydq_rq  DATE,
  ty_rq    DATE,
  bz       VARCHAR2(100),
  swjgbm   VARCHAR2(20),
  fszt_dm  VARCHAR2(8),
  fs_sj    DATE,
  dqtx_lsh VARCHAR2(36),
  sjlx     VARCHAR2(20)
);
comment on column DX_TYDQTX.lsh  is '主键';
comment on column DX_TYDQTX.nsrsbm  is '纳税人编码';
comment on column DX_TYDQTX.nsrmc  is '纳税人名称';
comment on column DX_TYDQTX.sjhm  is '手机号码';
comment on column DX_TYDQTX.smsinfo  is '短信内容';
comment on column DX_TYDQTX.lr_sj  is '录入时间';
comment on column DX_TYDQTX.tydq_rq  is '停业到期日期';
comment on column DX_TYDQTX.ty_rq  is '停业日期';
comment on column DX_TYDQTX.bz  is '备注';
comment on column DX_TYDQTX.swjgbm  is '税务机关编码';
comment on column DX_TYDQTX.fszt_dm  is '发送状态';
comment on column DX_TYDQTX.fs_sj  is '发送时间';
alter table DX_TYDQTX  add constraint PK_DX_TYDQTX primary key (LSH);
create index IDX_DX_TYDQTX_FSSJ on DX_TYDQTX (FS_SJ);


create table DX_TSJBHF
(
  lsh    VARCHAR2(36) not null,
  zblsh  VARCHAR2(36),
  hfinfo VARCHAR2(500),
  hfr_dm VARCHAR2(20),
  hf_sj  DATE
);
comment on column DX_TSJBHF.lsh  is '流水号';
comment on column DX_TSJBHF.zblsh  is '主表流水号';
comment on column DX_TSJBHF.hfinfo  is '回复内容';
comment on column DX_TSJBHF.hfr_dm  is '回复人';
comment on column DX_TSJBHF.hf_sj  is '回复时间';
alter table DX_TSJBHF  add constraint PK_DX_TSJBHF primary key (LSH);


create table DX_TSJBCX
(
  lsh    VARCHAR2(36) not null,
  sjhm   VARCHAR2(11),
  tsinfo VARCHAR2(500),
  ts_sj  DATE,
  hf_bj  VARCHAR2(1) default '0'
);
comment on column DX_TSJBCX.lsh  is '流水号';
comment on column DX_TSJBCX.sjhm  is '手机号码';
comment on column DX_TSJBCX.tsinfo  is '投诉内容';
comment on column DX_TSJBCX.ts_sj  is '投诉时间';
alter table DX_TSJBCX  add constraint PK_DX_TSJBCX primary key (LSH);


create table DX_SFCX
(
  lsh      VARCHAR2(36) not null,
  nsrsbm   VARCHAR2(50),
  nsrmc    VARCHAR2(500),
  sjhm     VARCHAR2(11),
  smsinfo  VARCHAR2(500),
  lr_sj    DATE,
  ssny     VARCHAR2(6),
  bz       VARCHAR2(100),
  sf_je    NUMBER(18,2),
  swjgbm   VARCHAR2(20),
  fszt_dm  VARCHAR2(2),
  fs_sj    DATE,
  smsinfo2 VARCHAR2(500)
);
comment on column DX_SFCX.lsh  is '主键';
comment on column DX_SFCX.nsrsbm  is '纳税人编码';
comment on column DX_SFCX.nsrmc  is '纳税人名称';
comment on column DX_SFCX.sjhm  is '手机号码';
comment on column DX_SFCX.smsinfo  is '短信内容';
comment on column DX_SFCX.lr_sj  is '录入时间';
comment on column DX_SFCX.ssny  is '所属年月';
comment on column DX_SFCX.bz  is '备注';
comment on column DX_SFCX.sf_je  is '税费金额';
comment on column DX_SFCX.swjgbm  is '税务机关编码';
comment on column DX_SFCX.fszt_dm  is '发送状态';
comment on column DX_SFCX.fs_sj  is '发送时间';
comment on column DX_SFCX.smsinfo2  is '发送内容';
alter table DX_SFCX  add constraint PK_DX_SFCX primary key (LSH);


create table DX_SFCJTX
(
  lsh      VARCHAR2(36) not null,
  nsrsbm   VARCHAR2(50),
  nsrmc    VARCHAR2(500),
  sjhm     VARCHAR2(11),
  smsinfo  VARCHAR2(500),
  jkqx_rq  DATE,
  lr_sj    DATE,
  skssq    DATE,
  bz       VARCHAR2(100),
  swjgbm   VARCHAR2(20),
  fszt_dm  VARCHAR2(8),
  fs_sj    DATE,
  yzpzxh   VARCHAR2(20),
  skssz    DATE,
  sf_je    NUMBER(18,2),
  cnt      NUMBER(2),
  zsxm_dms VARCHAR2(200),
  zspm_dms VARCHAR2(200),
  sf_jes   VARCHAR2(200),
  sjlx     VARCHAR2(20)
);
alter table DX_SFCJTX  add constraint PK_DX_CJTX primary key (LSH);
create index IDX_DX_SFCJTX_FSSJ on DX_SFCJTX (FS_SJ);


create table DX_SFCBTX
(
  lsh      VARCHAR2(36) not null,
  nsrsbm   VARCHAR2(50),
  nsrmc    VARCHAR2(500),
  sjhm     VARCHAR2(11),
  smsinfo  VARCHAR2(500),
  lr_sj    DATE,
  skssq    DATE,
  bz       VARCHAR2(100),
  swjgbm   VARCHAR2(20),
  fszt_dm  VARCHAR2(8),
  fs_sj    DATE,
  sbqx     DATE,
  skssz    DATE,
  cnt      NUMBER(2),
  zsxm_dms VARCHAR2(200),
  sjlx     VARCHAR2(20)
);
comment on column DX_SFCBTX.lsh  is '主键';
alter table DX_SFCBTX  add constraint PK_DX_CBTX primary key (LSH);
create index IDX_DX_SFCBTX_FSSJ on DX_SFCBTX (FS_SJ);


create table DX_LMZXXX
(
  lsh     VARCHAR2(36) not null,
  lm_lsh  VARCHAR2(100),
  info    VARCHAR2(500),
  swjgbm  VARCHAR2(20),
  fs_bj   VARCHAR2(1),
  lr_sj   DATE,
  xg_sj   DATE,
  lrry_dm VARCHAR2(20),
  xgry_dm VARCHAR2(20)
);
comment on column DX_LMZXXX.lsh  is '主键';
comment on column DX_LMZXXX.lm_lsh  is '短信栏目';
comment on column DX_LMZXXX.info  is '资讯内容';
comment on column DX_LMZXXX.swjgbm  is '税务机关编码';
comment on column DX_LMZXXX.fs_bj  is '发送标记';
comment on column DX_LMZXXX.lr_sj  is '录入时间';
comment on column DX_LMZXXX.xg_sj  is '修改时间';
comment on column DX_LMZXXX.lrry_dm  is '录入人员';
comment on column DX_LMZXXX.xgry_dm  is '修改人员';
alter table DX_LMZXXX  add constraint PK_DX_LMZXXX primary key (LSH);


create table DX_LMZXJL
(
  lsh     VARCHAR2(36) not null,
  lm_lsh  VARCHAR2(100),
  zx_lsh  VARCHAR2(500),
  sjhm    VARCHAR2(11),
  nsrbm   VARCHAR2(50),
  nsrmc   VARCHAR2(500),
  lr_sj   DATE,
  xg_sj   DATE,
  lrry_dm VARCHAR2(20),
  xgry_dm VARCHAR2(20)
);
comment on column DX_LMZXJL.lsh  is '主键';
comment on column DX_LMZXJL.lm_lsh  is '短信栏目';
comment on column DX_LMZXJL.zx_lsh  is '短信资讯';
comment on column DX_LMZXJL.sjhm  is '手机号码';
comment on column DX_LMZXJL.nsrbm  is '纳税人编码';
comment on column DX_LMZXJL.nsrmc  is '纳税人名称';
comment on column DX_LMZXJL.lr_sj  is '录入时间';
comment on column DX_LMZXJL.xg_sj  is '修改时间';
comment on column DX_LMZXJL.lrry_dm  is '录入人员';
comment on column DX_LMZXJL.xgry_dm  is '修改人员';
alter table DX_LMZXJL  add constraint PK_DX_LMZXJL primary key (LSH);


create table DX_KKSBTX
(
  lsh      VARCHAR2(36) not null,
  nsrsbm   VARCHAR2(50),
  nsrmc    VARCHAR2(500),
  sjhm     VARCHAR2(11),
  smsinfo  VARCHAR2(500),
  lr_sj    DATE,
  skssq    DATE,
  jk_rq    DATE,
  bz       VARCHAR2(100),
  sf_je    NUMBER(18,2),
  swjgbm   VARCHAR2(20),
  fszt_dm  VARCHAR2(8),
  fs_sj    DATE,
  xtsphm   VARCHAR2(20),
  jklx_dm  VARCHAR2(20),
  skssz    DATE,
  cnt      NUMBER(2),
  zsxm_dms VARCHAR2(200),
  zspm_dms VARCHAR2(200),
  sf_jes   VARCHAR2(200),
  sjlx     VARCHAR2(20)
);
alter table DX_KKSBTX  add constraint PK_DX_KKSBTX primary key (LSH);
create index IDX_DX_KKSBTX_FSSJ on DX_KKSBTX (FS_SJ);


create table DX_JSRW
(
  type_dm VARCHAR2(15) not null,
  cron    VARCHAR2(100),
  scjs_sj DATE,
  yx_bj   VARCHAR2(1),
  bz      VARCHAR2(100)
);
comment on column DX_JSRW.type_dm  is '任务类型：表名';
comment on column DX_JSRW.cron  is 'CRON表达式';
comment on column DX_JSRW.scjs_sj  is '上传发送时间';
comment on column DX_JSRW.yx_bj  is '有效标记';
comment on column DX_JSRW.bz  is '备注';
alter table DX_JSRW  add constraint PK_DX_JSRW primary key (TYPE_DM);


create table DX_JKTX
(
  lsh      VARCHAR2(36) not null,
  xtsphm   VARCHAR2(20),
  jklx_dm  VARCHAR2(10),
  nsrsbm   VARCHAR2(50),
  jk_rq    DATE,
  skssq    DATE,
  skssz    DATE,
  sf_je    NUMBER(18,2),
  cnt      NUMBER(2),
  zsxm_dms VARCHAR2(200),
  zspm_dms VARCHAR2(200),
  sf_jes   VARCHAR2(200),
  nsrmc    VARCHAR2(500),
  swjgbm   VARCHAR2(20),
  sjhm     VARCHAR2(11),
  sjlx     VARCHAR2(20),
  smsinfo  VARCHAR2(500),
  lr_sj    DATE,
  bz       VARCHAR2(100),
  fszt_dm  VARCHAR2(8),
  fs_sj    DATE
);
comment on table DX_JKTX  is '缴款提醒';
alter table DX_JKTX  add constraint PK_DX_JKTX primary key (LSH);
create index IDX_DX_JKTX_FSSJ on DX_JKTX (FS_SJ);


create table DX_GMFPTX
(
  lsh      VARCHAR2(36) not null,
  nsrsbm   VARCHAR2(50),
  nsrmc    VARCHAR2(500),
  sjhm     VARCHAR2(11),
  smsinfo  VARCHAR2(500),
  lr_sj    DATE,
  fp_rq    DATE,
  bz       VARCHAR2(100),
  fp_je    NUMBER(18,2),
  fpdm     VARCHAR2(20),
  fphm     VARCHAR2(20),
  fpzl     VARCHAR2(100),
  swjgbm   VARCHAR2(20),
  fszt_dm  VARCHAR2(8),
  fs_sj    DATE,
  fptx_lsh VARCHAR2(36),
  sjlx     VARCHAR2(20)
);
comment on column DX_GMFPTX.lsh  is '主键';
comment on column DX_GMFPTX.nsrsbm  is '纳税人编码';
comment on column DX_GMFPTX.nsrmc  is '纳税人名称';
comment on column DX_GMFPTX.sjhm  is '手机号码';
comment on column DX_GMFPTX.smsinfo  is '短信内容';
comment on column DX_GMFPTX.lr_sj  is '录入时间';
comment on column DX_GMFPTX.fp_rq  is '发票日期';
comment on column DX_GMFPTX.bz  is '备注';
comment on column DX_GMFPTX.fp_je  is '发票金额';
comment on column DX_GMFPTX.fpdm  is '发票代码';
comment on column DX_GMFPTX.fphm  is '发票号码';
comment on column DX_GMFPTX.fpzl  is '发票种类名称';
comment on column DX_GMFPTX.swjgbm  is '税务机关编码';
comment on column DX_GMFPTX.fszt_dm  is '发送状态';
comment on column DX_GMFPTX.fs_sj  is '发送时间';
alter table DX_GMFPTX  add constraint PK_DX_GMFPTX primary key (LSH);
create index IDX_DX_GMFPTX_FSSJ on DX_GMFPTX (FS_SJ);


create table DX_FSRW
(
  type_dm VARCHAR2(15) not null,
  cron    VARCHAR2(100),
  scfs_sj DATE,
  yx_bj   VARCHAR2(1),
  bz      VARCHAR2(100)
);
comment on column DX_FSRW.type_dm  is '任务类型：表名';
comment on column DX_FSRW.cron  is 'CRON表达式';
comment on column DX_FSRW.scfs_sj  is '上次发送时间';
comment on column DX_FSRW.yx_bj  is '有效标记';
comment on column DX_FSRW.bz  is '备注';
alter table DX_FSRW  add constraint PK_DX_FSRW primary key (TYPE_DM);


create table DX_FPZWCX
(
  lsh      VARCHAR2(36) not null,
  nsrsbm   VARCHAR2(50),
  nsrmc    VARCHAR2(500),
  sjhm     VARCHAR2(11),
  smsinfo  VARCHAR2(500),
  lr_sj    DATE,
  fp_rq    DATE,
  bz       VARCHAR2(100),
  fp_je    NUMBER(18,2),
  fpdm     VARCHAR2(20),
  fphm     VARCHAR2(20),
  fpzl     VARCHAR2(100),
  swjgbm   VARCHAR2(20),
  fszt_dm  VARCHAR2(2),
  fs_sj    DATE,
  smsinfo2 VARCHAR2(500)
);
comment on column DX_FPZWCX.lsh  is '主键';
comment on column DX_FPZWCX.nsrsbm  is '纳税人编码';
comment on column DX_FPZWCX.nsrmc  is '纳税人名称';
comment on column DX_FPZWCX.sjhm  is '手机号码';
comment on column DX_FPZWCX.smsinfo  is '短信内容';
comment on column DX_FPZWCX.lr_sj  is '录入时间';
comment on column DX_FPZWCX.fp_rq  is '发票日期';
comment on column DX_FPZWCX.bz  is '备注';
comment on column DX_FPZWCX.fp_je  is '发票金额';
comment on column DX_FPZWCX.fpdm  is '发票代码';
comment on column DX_FPZWCX.fphm  is '发票号码';
comment on column DX_FPZWCX.fpzl  is '发票种类名称';
comment on column DX_FPZWCX.swjgbm  is '税务机关编码';
comment on column DX_FPZWCX.fszt_dm  is '发送状态';
comment on column DX_FPZWCX.fs_sj  is '发送时间';
comment on column DX_FPZWCX.smsinfo2  is '发送内容';
alter table DX_FPZWCX  add constraint PK_DX_FPZWCX primary key (LSH);


create table DX_FPZJXX
(
  lsh     VARCHAR2(36) not null,
  info    VARCHAR2(500),
  bz      VARCHAR2(100),
  swjgbm  VARCHAR2(20),
  kjny    VARCHAR2(6),
  ssx     VARCHAR2(10),
  lr_sj   DATE,
  xg_sj   DATE,
  lrry_dm VARCHAR2(20),
  xgry_dm VARCHAR2(20)
);
comment on column DX_FPZJXX.lsh  is '主键';
comment on column DX_FPZJXX.info  is '短信内容';
comment on column DX_FPZJXX.bz  is '备注';
comment on column DX_FPZJXX.swjgbm  is '税务机关编码';
comment on column DX_FPZJXX.kjny  is '开奖年月';
comment on column DX_FPZJXX.ssx  is '市的缩写';
comment on column DX_FPZJXX.lr_sj  is '录入时间';
comment on column DX_FPZJXX.xg_sj  is '修改时间';
comment on column DX_FPZJXX.lrry_dm  is '录入人员';
comment on column DX_FPZJXX.xgry_dm  is '修改人员';
alter table DX_FPZJXX  add constraint PK_DX_FPZJXX primary key (LSH);


create table DX_FPZJCX
(
  lsh      VARCHAR2(36) not null,
  sjhm     VARCHAR2(11),
  smsinfo  VARCHAR2(500),
  lr_sj    DATE,
  bz       VARCHAR2(100),
  swjgbm   VARCHAR2(20),
  kjny     VARCHAR2(6),
  ssx      VARCHAR2(10),
  fszt_dm  VARCHAR2(2),
  fs_sj    DATE,
  smsinfo2 VARCHAR2(500)
);
comment on column DX_FPZJCX.lsh  is '主键';
comment on column DX_FPZJCX.sjhm  is '手机号码';
comment on column DX_FPZJCX.smsinfo  is '短信内容';
comment on column DX_FPZJCX.lr_sj  is '录入时间';
comment on column DX_FPZJCX.bz  is '备注';
comment on column DX_FPZJCX.swjgbm  is '税务机关编码';
comment on column DX_FPZJCX.kjny  is '开奖年月';
comment on column DX_FPZJCX.ssx  is '市的缩写';
comment on column DX_FPZJCX.fszt_dm  is '发送状态';
comment on column DX_FPZJCX.fs_sj  is '发送时间';
comment on column DX_FPZJCX.smsinfo2  is '发送内容';
alter table DX_FPZJCX  add constraint PK_DX_FPZJCX primary key (LSH);


create table DX_DXQY
(
  lsh     VARCHAR2(36) not null,
  lm_lsh  VARCHAR2(100),
  lmnr    VARCHAR2(500),
  sjhm    VARCHAR2(11),
  nsrbm   VARCHAR2(50),
  nsrmc   VARCHAR2(500),
  lr_sj   DATE,
  xg_sj   DATE,
  lrry_dm VARCHAR2(20),
  xgry_dm VARCHAR2(20)
);
comment on column DX_DXQY.lsh  is '主键';
comment on column DX_DXQY.lm_lsh  is '短信栏目';
comment on column DX_DXQY.lmnr  is '签约内容';
comment on column DX_DXQY.sjhm  is '手机号码';
comment on column DX_DXQY.nsrbm  is '纳税人编码';
comment on column DX_DXQY.nsrmc  is '纳税人名称';
comment on column DX_DXQY.lr_sj  is '录入时间';
comment on column DX_DXQY.xg_sj  is '修改时间';
comment on column DX_DXQY.lrry_dm  is '录入人员';
comment on column DX_DXQY.xgry_dm  is '修改人员';
alter table DX_DXQY  add constraint PK_DX_DXQY primary key (LSH);


create table DX_DXMB
(
  lsh      VARCHAR2(36) not null,
  dxlx_dm  VARCHAR2(20),
  info     VARCHAR2(500),
  info2    VARCHAR2(500),
  jsrlx_dm VARCHAR2(20),
  mbmc     VARCHAR2(100),
  bz       VARCHAR2(500),
  xgry_dm  VARCHAR2(20),
  xg_sj    DATE
);
comment on table DX_DXMB  is '短信模板';
alter table DX_DXMB  add constraint PK_DX_DXMB primary key (LSH);


create table DX_DXLM
(
  lsh     VARCHAR2(36) not null,
  lmmc    VARCHAR2(100),
  lmnr    VARCHAR2(500),
  swjgbm  VARCHAR2(20),
  kt_bj   VARCHAR2(1),
  lr_sj   DATE,
  xg_sj   DATE,
  lrry_dm VARCHAR2(20),
  xgry_dm VARCHAR2(20)
);
comment on column DX_DXLM.lsh  is '主键';
comment on column DX_DXLM.lmmc  is '栏目名称';
comment on column DX_DXLM.lmnr  is '栏目内容';
comment on column DX_DXLM.swjgbm  is '税务机关编码';
comment on column DX_DXLM.kt_bj  is '开通标记';
comment on column DX_DXLM.lr_sj  is '录入时间';
comment on column DX_DXLM.xg_sj  is '修改时间';
comment on column DX_DXLM.lrry_dm  is '录入人员';
comment on column DX_DXLM.xgry_dm  is '修改人员';
alter table DX_DXLM  add constraint PK_DX_DXLM primary key (LSH);


-----基础数据
insert into DM_SJSX (SJSX_DM, SJSX_MC) values ('sy', '沈阳市');
insert into DM_SJSX (SJSX_DM, SJSX_MC) values ('as', '鞍山市');
insert into DM_SJSX (SJSX_DM, SJSX_MC) values ('fs', '抚顺市');
insert into DM_SJSX (SJSX_DM, SJSX_MC) values ('bx', '本溪市');
insert into DM_SJSX (SJSX_DM, SJSX_MC) values ('dd', '丹东市');
insert into DM_SJSX (SJSX_DM, SJSX_MC) values ('jz', '锦州市');
insert into DM_SJSX (SJSX_DM, SJSX_MC) values ('yk', '营口市');
insert into DM_SJSX (SJSX_DM, SJSX_MC) values ('fx', '阜新市');
insert into DM_SJSX (SJSX_DM, SJSX_MC) values ('ly', '辽阳市');
insert into DM_SJSX (SJSX_DM, SJSX_MC) values ('pj', '盘锦市');
insert into DM_SJSX (SJSX_DM, SJSX_MC) values ('tl', '铁岭市');
insert into DM_SJSX (SJSX_DM, SJSX_MC) values ('cy', '朝阳市');
insert into DM_SJSX (SJSX_DM, SJSX_MC) values ('hld', '葫芦岛市');

insert into DM_JKLX (JKLX_DM, JKLX_MC) values ('592', '批扣');
insert into DM_JKLX (JKLX_DM, JKLX_MC) values ('593', '正划');
insert into DM_JKLX (JKLX_DM, JKLX_MC) values ('921', '现金');
insert into DM_JKLX (JKLX_DM, JKLX_MC) values ('600', '倒扣');
insert into DM_JKLX (JKLX_DM, JKLX_MC) values ('911', '扣款方式A');
insert into DM_JKLX (JKLX_DM, JKLX_MC) values ('594', '扣款方式B');

insert into DM_DXLX (DXLX_DM, DXLX_MC) values ('SFCBTX', '催报提醒短信');
insert into DM_DXLX (DXLX_DM, DXLX_MC) values ('SFCJTX', '催缴提醒短信');
insert into DM_DXLX (DXLX_DM, DXLX_MC) values ('JKTX', '扣款提醒短信');
insert into DM_DXLX (DXLX_DM, DXLX_MC) values ('KKSBTX', '扣款失败提醒短信');
insert into DM_DXLX (DXLX_DM, DXLX_MC) values ('GMFPTX', '购买发票提醒短信');
insert into DM_DXLX (DXLX_DM, DXLX_MC) values ('TYDQTX', '停业到期提醒短信');
insert into DM_DXLX (DXLX_DM, DXLX_MC) values ('FPZWCX', '发票真伪查询回复');
insert into DM_DXLX (DXLX_DM, DXLX_MC) values ('FPZJCX', '发票中奖查询回复');
insert into DM_DXLX (DXLX_DM, DXLX_MC) values ('SFCX', '税费查询回复');

insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('1', '${纳税人编码}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('2', '${纳税人名称}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('3', '${当前日期}', '');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('4', '${当前时间}', '');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('5', '${税款合计金额}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('6', '#{循环：征收品目（开始）}', '');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('7', '#{循环内部判断：如果有下一个品目（开始）}', '');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('8', '#{循环内部判断：如果有下一个品目（结束）}', '');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('9', '#{循环内部判断：如果无下一个品目（开始）}', '');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('a', '#{循环内部判断：如果无下一个品目（结束）}', '');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('b', '#{循环：征收品目（结束）}', '');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('c', '${征收项目}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('d', '${征收品目}', '');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('e', '${征收品目金额}', '');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('f', '${税款所属期起}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('g', '${税款所属期止}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('A', '${扣款类型}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('h', '${申报日期}', '');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('i', '${缴款日期}', '');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('j', '${发票种类}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('k', '${发票代码}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('l', '${发票号码}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('m', '${发票号码起}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('n', '${发票号码止}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('o', '${发票金额}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('p', '${停业日期}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('q', '${发票日期}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('r', '#{循环：征收项目（开始）}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('s', '#{循环内部判断：如果有下一个项目（开始）}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('t', '#{循环内部判断：如果有下一个项目（结束）}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('u', '#{循环内部判断：如果无下一个项目（开始）}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('v', '#{循环内部判断：如果无下一个项目（结束）}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('w', '#{循环：征收项目（结束）}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('x', '${征收项目金额}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('y', '${税票号码}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('z', '${市级缩写}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('11', '${中奖年月}', '1');
insert into DM_DXYS2 (DXYS_DM, DXYS_MC, YX_BJ) values ('21', '${开奖内容}', '1');

insert into DM_JSRLX (DXJSRLX_DM, DXJSRLX_MC) values ('CWJL', '财务经理');
insert into DM_JSRLX (DXJSRLX_DM, DXJSRLX_MC) values ('ALL', '所有人');
insert into DM_JSRLX (DXJSRLX_DM, DXJSRLX_MC) values ('BSRY', '办税人员');
insert into DM_JSRLX (DXJSRLX_DM, DXJSRLX_MC) values ('FR', '法人');

insert into DX_FSRW (TYPE_DM, CRON, SCFS_SJ, YX_BJ, BZ) values ('SFCBTX', '0 0 8 * * ?', to_date('27-11-2013 14:55:58', 'dd-mm-yyyy hh24:mi:ss'), '1', '02 OK 催报提醒短信8:00 0 0 8 * * ?');
insert into DX_FSRW (TYPE_DM, CRON, SCFS_SJ, YX_BJ, BZ) values ('SFCJTX', '0 0 9 * * ?', to_date('27-11-2013 15:11:58', 'dd-mm-yyyy hh24:mi:ss'), '1', '04 OK 催缴提醒短信9:00 0 0 9 * * ?');
insert into DX_FSRW (TYPE_DM, CRON, SCFS_SJ, YX_BJ, BZ) values ('JKTX', '0 0 10 * * ?', to_date('27-11-2013 15:17:58', 'dd-mm-yyyy hh24:mi:ss'), '1', '06 OK 扣款提醒短信10:00 0 0 10 * * ?');
insert into DX_FSRW (TYPE_DM, CRON, SCFS_SJ, YX_BJ, BZ) values ('KKSBTX', '0 30 9 * * ?', to_date('27-11-2013 15:27:58', 'dd-mm-yyyy hh24:mi:ss'), '1', '08 OK 扣款失败提醒短信 9:30 0 30 9 * * ?');
insert into DX_FSRW (TYPE_DM, CRON, SCFS_SJ, YX_BJ, BZ) values ('GMFPTX', '0 0/20 * * * ?', to_date('28-11-2013 10:59:59', 'dd-mm-yyyy hh24:mi:ss'), '1', '10 OK 购买发票提醒短信 每20分钟');
insert into DX_FSRW (TYPE_DM, CRON, SCFS_SJ, YX_BJ, BZ) values ('TYDQTX', '10 10 10 * * ?', to_date('27-11-2013 15:47:08', 'dd-mm-yyyy hh24:mi:ss'), '1', '12 OK 停业到期提醒短信 10 10 10 * * ?');

insert into DX_JSRW (TYPE_DM, CRON, SCJS_SJ, YX_BJ, BZ) values ('INBOX', '20 0/1 * * * ?', to_date('28-11-2013 11:12:50', 'dd-mm-yyyy hh24:mi:ss'), '1', '00 OK 接收短信 20 1/10 * * * ?');
insert into DX_JSRW (TYPE_DM, CRON, SCJS_SJ, YX_BJ, BZ) values ('SFCBTX', '0 0 18 * * ?', to_date('27-11-2013 14:57:19', 'dd-mm-yyyy hh24:mi:ss'), '1', '03 OK 催报提醒短信状态 0 0 18 * * ?');
insert into DX_JSRW (TYPE_DM, CRON, SCJS_SJ, YX_BJ, BZ) values ('SFCJTX', '0 0 19 * * ?', to_date('27-11-2013 15:12:29', 'dd-mm-yyyy hh24:mi:ss'), '1', '05 OK 催缴提醒短信状态 0 0 19 * * ?');
insert into DX_JSRW (TYPE_DM, CRON, SCJS_SJ, YX_BJ, BZ) values ('JKTX', '0 0 20 * * ?', to_date('27-11-2013 15:20:59', 'dd-mm-yyyy hh24:mi:ss'), '1', '07 OK 扣款提醒短信状态 0 0 20 * * ?');
insert into DX_JSRW (TYPE_DM, CRON, SCJS_SJ, YX_BJ, BZ) values ('KKSBTX', '0 30 19 * * ?', to_date('27-11-2013 15:30:09', 'dd-mm-yyyy hh24:mi:ss'), '1', '09 OK 扣款失败提醒短信状态 0 30 19 * * ?');
insert into DX_JSRW (TYPE_DM, CRON, SCJS_SJ, YX_BJ, BZ) values ('GMFPTX', '0 0 22 * * ?', to_date('27-11-2013 15:41:39', 'dd-mm-yyyy hh24:mi:ss'), '1', '11 OK 购买发票提醒短信状态 0 0 22 * * ?');
insert into DX_JSRW (TYPE_DM, CRON, SCJS_SJ, YX_BJ, BZ) values ('TYDQTX', '10 10 20 * * ?', to_date('27-11-2013 15:44:43', 'dd-mm-yyyy hh24:mi:ss'), '1', '13 OK 停业到期提醒短信状态 10 10 20 * * ?');
insert into DX_JSRW (TYPE_DM, CRON, SCJS_SJ, YX_BJ, BZ) values ('TSJBCX', '20 4/5 * * * ?', to_date('28-11-2013 11:09:19', 'dd-mm-yyyy hh24:mi:ss'), '1', '17 OK 投诉举报内容 20 4/5 * * * ?');
insert into DX_JSRW (TYPE_DM, CRON, SCJS_SJ, YX_BJ, BZ) values ('FPZWCX', '10 3/5 * * * ?', to_date('28-11-2013 11:13:09', 'dd-mm-yyyy hh24:mi:ss'), '1', '15 OK 发票真伪查询内容 10 3/5 * * * ?');
insert into DX_JSRW (TYPE_DM, CRON, SCJS_SJ, YX_BJ, BZ) values ('FPZJCX', '20 1/5 * * * ?', to_date('28-11-2013 11:11:19', 'dd-mm-yyyy hh24:mi:ss'), '1', '14 OK 发票中奖查询内容 20 1/5 * * * ?');
insert into DX_JSRW (TYPE_DM, CRON, SCJS_SJ, YX_BJ, BZ) values ('SFCX', '30 2/5 * * * ?', to_date('28-11-2013 11:12:29', 'dd-mm-yyyy hh24:mi:ss'), '1', '16 OK 税费查询内容 30 2/5 * * * ?');
insert into DX_JSRW (TYPE_DM, CRON, SCJS_SJ, YX_BJ, BZ) values ('WJDCMX', '50 6/30 * * * ?', to_date('28-11-2013 11:06:49', 'dd-mm-yyyy hh24:mi:ss'), '1', '18 OK 问卷调查回复接收 50 6/30 * * * ?');
insert into DX_JSRW (TYPE_DM, CRON, SCJS_SJ, YX_BJ, BZ) values ('HeartbeatJob', '15 0/10 * * * ?', to_date('08-10-2013', 'dd-mm-yyyy'), '1', '01 OK 心跳任务');
insert into DX_JSRW (TYPE_DM, CRON, SCJS_SJ, YX_BJ, BZ) values ('DXZT', '0 10 0-7,12-13,18-23 * * ?', to_date('01-05-2013', 'dd-mm-yyyy'), '1', '一期短信发送状态 0 10 0-7,12-13,18-23 * * ?');

insert into DX_DXMB (LSH, DXLX_DM, INFO, INFO2, JSRLX_DM, MBMC, BZ, XGRY_DM, XG_SJ) values ('FPZJCX', 'FPZJCX', '${市级缩写}（${中奖年月}）：${开奖内容}', '', 'ALL', '发票中奖查询模板', '发票中奖查询模板', '', null);
insert into DX_DXMB (LSH, DXLX_DM, INFO, INFO2, JSRLX_DM, MBMC, BZ, XGRY_DM, XG_SJ) values ('FPZWCX', 'FPZWCX', '此发票(${发票代码}，${发票号码})是${纳税人名称}于${发票日期}开具，请核对。如不符，请拨打12366-2向地税局举报。', '', 'ALL', '发票真伪查询模板', '发票真伪查询模板', '', null);
insert into DX_DXMB (LSH, DXLX_DM, INFO, INFO2, JSRLX_DM, MBMC, BZ, XGRY_DM, XG_SJ) values ('GMFPTX', 'GMFPTX', '${纳税人名称}(${纳税人编码})于${发票日期}开具了发票，发票种类：${发票种类}，发票代码：${发票代码}，发票号码：${发票号码起}-${发票号码止}，发票金额：${发票金额}。', '', 'CWJL', '购买发票提醒模板', '购买发票提醒模板', 'admin', to_date('31-10-2013 22:45:33', 'dd-mm-yyyy hh24:mi:ss'));
insert into DX_DXMB (LSH, DXLX_DM, INFO, INFO2, JSRLX_DM, MBMC, BZ, XGRY_DM, XG_SJ) values ('JKTX', 'JKTX', '${纳税人名称}(${纳税人编码})税款所属期(${税款所属期起}-${税款所属期止})税款缴款成功，共缴纳入库${税款合计金额}元。', '', 'BSRY', '缴款提醒模板', '缴款提醒模板', '', null);
insert into DX_DXMB (LSH, DXLX_DM, INFO, INFO2, JSRLX_DM, MBMC, BZ, XGRY_DM, XG_SJ) values ('KKSBTX', 'KKSBTX', '${纳税人名称}（${纳税人编码}）税款所属期(${税款所属期起}-${税款所属期止})以下税款银行自动扣款未能成功：#{循环：征收项目（开始）}${征收项目}（${征收项目金额}）#{循环内部判断：如果有下一个项目（开始）}；#{循环内部判断：如果有下一个项目（结束）}#{循环内部判断：如果无下一个项目（开始）}。#{循环内部判断：如果无下一个项目（结束）}#{循环：征收项目（结束）}请在规定的缴款期限前尽快联系主管地税机关查询扣款失败原因并缴款入库。', '', 'BSRY', '扣款失败提醒模板', '扣款失败提醒模板', '', null);
insert into DX_DXMB (LSH, DXLX_DM, INFO, INFO2, JSRLX_DM, MBMC, BZ, XGRY_DM, XG_SJ) values ('SFCBTX', 'SFCBTX', '${纳税人名称}(${纳税人编码})税款所属期(${税款所属期起}到${税款所属期止})以下税种尚未进行纳税申报：#{循环：征收项目（开始）}${征收项目}#{循环内部判断：如果有下一个项目（开始）}；#{循环内部判断：如果有下一个项目（结束）}#{循环内部判断：如果无下一个项目（开始）}。#{循环内部判断：如果无下一个项目（结束）}#{循环：征收项目（结束）}且已过了规定的申报期限，请尽快到主管税务机关办理。', '', 'BSRY', '催报提醒模板', '催报提醒模板2', 'admin', to_date('27-11-2013 14:53:26', 'dd-mm-yyyy hh24:mi:ss'));
insert into DX_DXMB (LSH, DXLX_DM, INFO, INFO2, JSRLX_DM, MBMC, BZ, XGRY_DM, XG_SJ) values ('SFCJTX', 'SFCJTX', '${纳税人名称}(${纳税人编码})税款所属期(${税款所属期起}-${税款所属期止})以下税款尚未缴款：#{循环：征收项目（开始）}${征收项目}（${征收项目金额}元）#{循环内部判断：如果有下一个项目（开始）}；#{循环内部判断：如果有下一个项目（结束）}#{循环内部判断：如果无下一个项目（开始）}。#{循环内部判断：如果无下一个项目（结束）}#{循环：征收项目（结束）}税款合计：${税款合计金额}。切已过了规定的缴款期限，请尽快缴纳。', '', 'BSRY', '催缴提醒模板', '催缴提醒模板2', 'admin', to_date('27-11-2013 15:01:16', 'dd-mm-yyyy hh24:mi:ss'));
insert into DX_DXMB (LSH, DXLX_DM, INFO, INFO2, JSRLX_DM, MBMC, BZ, XGRY_DM, XG_SJ) values ('SFCX', 'SFCX', '${纳税人名称}（${纳税人编码}）税款所属期(${税款所属期起}-${税款所属期止})下缴款信息：#{循环：征收项目（开始）}${征收项目}（${征收项目金额}）#{循环内部判断：如果有下一个项目（开始）}；#{循环内部判断：如果有下一个项目（结束）}#{循环内部判断：如果无下一个项目（开始）}。#{循环内部判断：如果无下一个项目（结束）}#{循环：征收项目（结束）}。', '您的号码没有在税务部门登记，请您联系主管税务机关。', 'ALL', '税费查询模板', '税费查询模板', '', null);
insert into DX_DXMB (LSH, DXLX_DM, INFO, INFO2, JSRLX_DM, MBMC, BZ, XGRY_DM, XG_SJ) values ('TYDQTX', 'TYDQTX', '${纳税人名称}(${纳税人编码})，您的停业到${停业日期}到期，请您及时到主管税务机关办理复业手续。', '', 'BSRY', '停业到期提醒模板', '停业到期提醒模板', '', null);


UPDATE QX_AUTH SET YX_BJ='0' WHERE AUTH_DM LIKE 'zjdx%';

insert into QX_AUTH (AUTH_DM, AUTH_MC, AUTH_CC, SJ_AUTH_DM, AUTH_MS, AUTH_LJ, TARGET, XS_SX, YX_BJ, LRRY_DM, XGRY_DM, LR_SJ, XG_SJ, SFDJ_BJ)
values ('TASK', '自动任务管理', '2', 'XTSZ', '自动任务管理', 'xtgl/task!list.action', '_self', 6, '1', '', '', null, null, '1');

insert into QX_AUTH (AUTH_DM, AUTH_MC, AUTH_CC, SJ_AUTH_DM, AUTH_MS, AUTH_LJ, TARGET, XS_SX, YX_BJ, LRRY_DM, XGRY_DM, LR_SJ, XG_SJ, SFDJ_BJ)
values ('ZDDXMB', '自动短信模板', '2', 'XTSZ', '自动短信模板', 'xtgl/dxmb2!list.action', '_self', 7, '1', '', '', null, null, '1');

insert into QX_AUTH (AUTH_DM, AUTH_MC, AUTH_CC, SJ_AUTH_DM, AUTH_MS, AUTH_LJ, TARGET, XS_SX, YX_BJ, LRRY_DM, XGRY_DM, LR_SJ, XG_SJ, SFDJ_BJ)
values ('cbtx', '催报提醒短信记录', '2', 'TJFX', '催报提醒查询', 'comtwo/cbtx!list.action', '_self', '3', '1', '', '', null, null, '1');

insert into QX_AUTH (AUTH_DM, AUTH_MC, AUTH_CC, SJ_AUTH_DM, AUTH_MS, AUTH_LJ, TARGET, XS_SX, YX_BJ, LRRY_DM, XGRY_DM, LR_SJ, XG_SJ, SFDJ_BJ)
values ('cjtx', '催缴提醒短信记录', '2', 'TJFX', '催缴提醒查询', 'comtwo/cjtx!list.action', '_self', '4', '1', '', '', null, null, '1');

insert into QX_AUTH (AUTH_DM, AUTH_MC, AUTH_CC, SJ_AUTH_DM, AUTH_MS, AUTH_LJ, TARGET, XS_SX, YX_BJ, LRRY_DM, XGRY_DM, LR_SJ, XG_SJ, SFDJ_BJ)
values ('fpzjcx', '发票中奖短信记录', '2', 'TJFX', '发票中奖短信记录', 'comtwo/fpzjcx!list.action', '_self', '10', '1', '', '', null, null, '1');

insert into QX_AUTH (AUTH_DM, AUTH_MC, AUTH_CC, SJ_AUTH_DM, AUTH_MS, AUTH_LJ, TARGET, XS_SX, YX_BJ, LRRY_DM, XGRY_DM, LR_SJ, XG_SJ, SFDJ_BJ)
values ('fpzwcx', '发票真伪短信记录', '2', 'TJFX', '发票真伪短信记录', 'comtwo/fpzwcx!list.action', '_self', '9', '1', '', '', null, null, '1');

insert into QX_AUTH (AUTH_DM, AUTH_MC, AUTH_CC, SJ_AUTH_DM, AUTH_MS, AUTH_LJ, TARGET, XS_SX, YX_BJ, LRRY_DM, XGRY_DM, LR_SJ, XG_SJ, SFDJ_BJ)
values ('gmfptx', '购买发票提醒短信记录', '2', 'TJFX', '购买发票提醒短信记录', 'comtwo/gmfptx!list.action', '_self', '7', '1', '', '', null, null, '1');

insert into QX_AUTH (AUTH_DM, AUTH_MC, AUTH_CC, SJ_AUTH_DM, AUTH_MS, AUTH_LJ, TARGET, XS_SX, YX_BJ, LRRY_DM, XGRY_DM, LR_SJ, XG_SJ, SFDJ_BJ)
values ('jktx', '缴款提醒短信记录', '2', 'TJFX', '缴款提醒短信记录', 'comtwo/jktx!list.action', '_self', '5', '1', '', '', null, null, '1');

insert into QX_AUTH (AUTH_DM, AUTH_MC, AUTH_CC, SJ_AUTH_DM, AUTH_MS, AUTH_LJ, TARGET, XS_SX, YX_BJ, LRRY_DM, XGRY_DM, LR_SJ, XG_SJ, SFDJ_BJ)
values ('kksbtx', '扣款失败提醒短信记录', '2', 'TJFX', '扣款失败提醒短信记录', 'comtwo/kksbtx!list.action', '_self', '6', '1', '', '', null, null, '1');

insert into QX_AUTH (AUTH_DM, AUTH_MC, AUTH_CC, SJ_AUTH_DM, AUTH_MS, AUTH_LJ, TARGET, XS_SX, YX_BJ, LRRY_DM, XGRY_DM, LR_SJ, XG_SJ, SFDJ_BJ)
values ('sfcx', '税费查询短信记录', '2', 'TJFX', '税费查询短信记录', 'comtwo/sfcx!list.action', '_self', '11', '1', '', '', null, null, '1');

insert into QX_AUTH (AUTH_DM, AUTH_MC, AUTH_CC, SJ_AUTH_DM, AUTH_MS, AUTH_LJ, TARGET, XS_SX, YX_BJ, LRRY_DM, XGRY_DM, LR_SJ, XG_SJ, SFDJ_BJ)
values ('tydqtx', '停业到期提醒短信记录', '2', 'TJFX', '停业到期提醒短信记录', 'comtwo/tydqtx!list.action', '_self', '8', '1', '', '', null, null, '1');

insert into QX_AUTH (AUTH_DM, AUTH_MC, AUTH_CC, SJ_AUTH_DM, AUTH_MS, AUTH_LJ, TARGET, XS_SX, YX_BJ, LRRY_DM, XGRY_DM, LR_SJ, XG_SJ, SFDJ_BJ)
values ('fpzjwh', '发票中奖信息维护', '2', 'XTSZ', '发票中奖信息维护', 'comtwo/fpzjwh!list.action', '_self', '8', '1', '', '', null, null, '1');

insert into QX_AUTH (AUTH_DM, AUTH_MC, AUTH_CC, SJ_AUTH_DM, AUTH_MS, AUTH_LJ, TARGET, XS_SX, YX_BJ, LRRY_DM, XGRY_DM, LR_SJ, XG_SJ, SFDJ_BJ)
values ('tsjb', '投诉举报', '2', 'XTSZ', '投诉举报', 'comtwo/tsjb!list.action', '_self', '9', '1', '', '', null, null, '1');

insert into QX_AUTH (AUTH_DM, AUTH_MC, AUTH_CC, SJ_AUTH_DM, AUTH_MS, AUTH_LJ, TARGET, XS_SX, YX_BJ, LRRY_DM, XGRY_DM, LR_SJ, XG_SJ, SFDJ_BJ)
values ('WJDC', '问卷调查', '1', '0', '问卷调查', '#', '_self', '6', '1', 'admin', 'admin', to_date('30-11-2013', 'dd-mm-yyyy'), to_date('30-11-2013', 'dd-mm-yyyy'), '1');

insert into QX_AUTH (AUTH_DM, AUTH_MC, AUTH_CC, SJ_AUTH_DM, AUTH_MS, AUTH_LJ, TARGET, XS_SX, YX_BJ, LRRY_DM, XGRY_DM, LR_SJ, XG_SJ, SFDJ_BJ)
values ('WJDCGL', '问卷调查管理', '2', 'WJDC', '问卷调查管理', 'comtwo/wjdc!list.action', '_self', '1', '1', 'admin', 'admin', to_date('30-11-2013', 'dd-mm-yyyy'), to_date('30-11-2013', 'dd-mm-yyyy'), '1');

insert into QX_AUTH (AUTH_DM, AUTH_MC, AUTH_CC, SJ_AUTH_DM, AUTH_MS, AUTH_LJ, TARGET, XS_SX, YX_BJ, LRRY_DM, XGRY_DM, LR_SJ, XG_SJ, SFDJ_BJ)
values ('XYDZ', '协议定制', '1', '0', '协议定制', '#', '_self', '6', '1', 'admin', 'admin', to_date('30-11-2013', 'dd-mm-yyyy'), to_date('30-11-2013', 'dd-mm-yyyy'), '1');

insert into QX_AUTH (AUTH_DM, AUTH_MC, AUTH_CC, SJ_AUTH_DM, AUTH_MS, AUTH_LJ, TARGET, XS_SX, YX_BJ, LRRY_DM, XGRY_DM, LR_SJ, XG_SJ, SFDJ_BJ)
values ('XYDZGL', '协议定制管理', '2', 'XYDZ', '协议定制管理', 'comtwo/xydz!list.action', '_self', '1', '1', 'admin', 'admin', to_date('30-11-2013', 'dd-mm-yyyy'), to_date('30-11-2013', 'dd-mm-yyyy'), '1');

update NSR_JBXX T set T.NSRBM=trim(T.NSRBM) WHERE T.NSRBM LIKE '% %';