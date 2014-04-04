CREATE or replace VIEW NOWDATE AS select sysdate AS NOW,(sysdate-1/24/60/60) AS MNOW,to_char(sysdate,'yyyy-mm-dd HH24:MI:SS') AS SNOW,to_char(sysdate-1/24/60/60,'yyyy-mm-dd HH24:MI:SS') AS SMNOW from dual;


create table T_SMS_ZSXM
(
  zsxm_dm VARCHAR2(2) not null,
  zsxm_mc VARCHAR2(100)
);
alter table T_SMS_ZSXM  add constraint KEY_T_SMS_ZSXM primary key (ZSXM_DM);


create table T_SMS_ZSPM
(
  zsxm_dm VARCHAR2(2) not null,
  zspm_dm VARCHAR2(4) not null,
  zspm_mc VARCHAR2(100)
);
alter table T_SMS_ZSPM  add constraint KEY_T_SMS_ZSPM primary key (ZSXM_DM, ZSPM_DM);


create table T_SMS_TYDQTX
(
  lsh    VARCHAR2(36) not null,
  nsrsbm VARCHAR2(50) not null,
  lr_sj  DATE default sysdate not null,
  yxq_q  DATE,
  yxq_z  DATE
);
comment on table T_SMS_TYDQTX  is '停业到期提醒';
comment on column T_SMS_TYDQTX.lsh  is '主键';
comment on column T_SMS_TYDQTX.nsrsbm  is '纳税人编码';
comment on column T_SMS_TYDQTX.lr_sj  is '录入时间YYYY-MM-DD HH24:MI:SS';
comment on column T_SMS_TYDQTX.yxq_q  is '停业有效日期起YYYY-MM-DD';
comment on column T_SMS_TYDQTX.yxq_z  is '停业有效日期止YYYY-MM-DD';
alter table T_SMS_TYDQTX  add constraint PK_T_SMS_TYDQTX primary key (LSH);
create index IDX_T_SMS_TYDQTX_LRSJ on T_SMS_TYDQTX (LR_SJ);


create table T_SMS_SFXX
(
  lsh     VARCHAR2(36) not null,
  nsrsbm  VARCHAR2(50) not null,
  skssq   DATE,
  skssz   DATE,
  zsxm_dm VARCHAR2(2) not null,
  zspm_dm VARCHAR2(4) not null,
  sf_je   NUMBER(18,2) default 0
);
comment on table T_SMS_SFXX  is '税费查询表';
comment on column T_SMS_SFXX.lsh  is '主键';
comment on column T_SMS_SFXX.nsrsbm  is '纳税人编码';
alter table T_SMS_SFXX  add constraint PK_T_SMS_SFXX primary key (LSH);
create index IDX_T_SMS_SFXX_01 on T_SMS_SFXX (NSRSBM, SKSSQ, SKSSZ);


create table T_SMS_SFCJTXMX
(
  lsh     VARCHAR2(36) not null,
  nsrsbm  VARCHAR2(50) not null,
  jkqx_rq DATE not null,
  lr_sj   DATE default sysdate not null,
  skssq   DATE,
  skssz   DATE,
  zsxm_dm VARCHAR2(2) not null,
  zspm_dm VARCHAR2(4) not null,
  sf_je   NUMBER(18,2) default 0,
  yzpzxh  NUMBER(20) not null
);
comment on table T_SMS_SFCJTXMX  is '税费催缴提醒';
comment on column T_SMS_SFCJTXMX.lsh  is '主键';
comment on column T_SMS_SFCJTXMX.nsrsbm  is '纳税人编码';
comment on column T_SMS_SFCJTXMX.jkqx_rq  is '缴款期限';
comment on column T_SMS_SFCJTXMX.lr_sj  is '录入时间YYYY-MM-DD HH24:MI:SS';
comment on column T_SMS_SFCJTXMX.skssq  is '税款所属期';
comment on column T_SMS_SFCJTXMX.skssz  is '税款所属期';
alter table T_SMS_SFCJTXMX  add constraint PK_T_SMS_SFCJTX primary key (LSH);
create index IDX_T_SMS_SFCJTX_LRSJ on T_SMS_SFCJTXMX (LR_SJ);


create table T_SMS_SFCBTXMX
(
  lsh     VARCHAR2(36) not null,
  nsrsbm  VARCHAR2(50) not null,
  lr_sj   DATE default sysdate not null,
  skssq   DATE,
  skssz   DATE,
  zsxm_dm VARCHAR2(2) not null,
  sbqx    DATE
);
comment on table T_SMS_SFCBTXMX  is '税费催报提醒';
comment on column T_SMS_SFCBTXMX.lsh  is '主键';
comment on column T_SMS_SFCBTXMX.nsrsbm  is '纳税人编码';
comment on column T_SMS_SFCBTXMX.lr_sj  is '录入时间YYYY-MM-DD HH24:MI:SS';
comment on column T_SMS_SFCBTXMX.skssq  is '税款所属期YYYY-MM-DD';
comment on column T_SMS_SFCBTXMX.skssz  is '税款所属期YYYY-MM-DD';
alter table T_SMS_SFCBTXMX  add constraint PK_T_SMS_SFCBTX primary key (LSH);
create index IDX_T_SMS_SFCBTX_LRSJ on T_SMS_SFCBTXMX (LR_SJ);


create table T_SMS_KKSBTX
(
  lsh     VARCHAR2(36) not null,
  nsrsbm  VARCHAR2(50) not null,
  lr_sj   DATE default sysdate not null,
  skssq   DATE,
  jk_rq   DATE,
  sf_je   NUMBER(18,2) default 0,
  skssz   DATE,
  zsxm_dm VARCHAR2(2) not null,
  zspm_dm VARCHAR2(4) not null,
  jklx_dm VARCHAR2(4) not null,
  xtsphm  VARCHAR2(20) not null
);
comment on table T_SMS_KKSBTX  is 'T_SMS_KKSBTX';
comment on column T_SMS_KKSBTX.lsh  is '主键';
comment on column T_SMS_KKSBTX.nsrsbm  is '纳税人编码';
comment on column T_SMS_KKSBTX.lr_sj  is '录入时间YYYY-MM-DD HH24:MI:SS';
comment on column T_SMS_KKSBTX.skssq  is '税款所属期YYYY-MM-DD';
comment on column T_SMS_KKSBTX.skssz  is '税款所属期YYYY-MM-DD';
alter table T_SMS_KKSBTX  add constraint PK_T_SMS_KKSBTX primary key (LSH);
create index IDX_T_SMS_KKSBTX_LRSJ on T_SMS_KKSBTX (LR_SJ);


create table T_SMS_JKTX
(
  lsh     VARCHAR2(36) not null,
  nsrsbm  VARCHAR2(50) not null,
  lr_sj   DATE default sysdate not null,
  skssq   DATE,
  jk_rq   DATE,
  sf_je   NUMBER(18,2) default 0,
  skssz   DATE,
  zsxm_dm VARCHAR2(2) not null,
  zspm_dm VARCHAR2(4) not null,
  jklx_dm VARCHAR2(4) not null,
  xtsphm  NUMBER(20) not null
);
comment on table T_SMS_JKTX  is '缴款成功反馈提醒';
comment on column T_SMS_JKTX.lsh  is '主键';
comment on column T_SMS_JKTX.nsrsbm  is '纳税人编码';
comment on column T_SMS_JKTX.lr_sj  is '录入时间YYYY-MM-DD HH24:MI:SS';
comment on column T_SMS_JKTX.skssq  is '税款所属期YYYY-MM-DD';
comment on column T_SMS_JKTX.skssz  is '税款所属期YYYY-MM-DD';
alter table T_SMS_JKTX  add constraint PK_T_SMS_JKTX primary key (LSH);
create index IDX_T_SMS_JKTX_LRSJ on T_SMS_JKTX (LR_SJ);


create table T_SMS_GMFPTX
(
  lsh    VARCHAR2(36) not null,
  nsrsbm VARCHAR2(50) not null,
  lr_sj  DATE default sysdate not null,
  fp_rq  DATE,
  fp_je  NUMBER(18,2) default 0,
  fpdm   VARCHAR2(20),
  fpzl   VARCHAR2(100),
  fpqh   VARCHAR2(10) not null,
  fpzh   VARCHAR2(10) not null
);
comment on table T_SMS_GMFPTX  is '购买发票提醒';
comment on column T_SMS_GMFPTX.lsh  is '主键';
comment on column T_SMS_GMFPTX.nsrsbm  is '纳税人编码';
comment on column T_SMS_GMFPTX.lr_sj  is '录入时间YYYY-MM-DD HH24:MI:SS';
comment on column T_SMS_GMFPTX.fp_rq  is '发票日期';
comment on column T_SMS_GMFPTX.fp_je  is '发票金额';
comment on column T_SMS_GMFPTX.fpdm  is '发票代码';
comment on column T_SMS_GMFPTX.fpzl  is '发票种类名称';
alter table T_SMS_GMFPTX  add constraint PK_T_SMS_GMFPTX primary key (LSH);
create index IDX_T_SMS_GMFPTX_LRSJ on T_SMS_GMFPTX (LR_SJ);


create table T_SMS_FPZL
(
  fpzl_dm VARCHAR2(30) not null,
  fpzl_mc VARCHAR2(100) not null
);
alter table T_SMS_FPZL  add constraint PK_FPZL_MC primary key (FPZL_DM);


create table T_SMS_FPXX
(
  fpdm  VARCHAR2(20) not null,
  fp_rq DATE not null,
  nsrbm VARCHAR2(50) not null,
  fp_je NUMBER(18,2) default 0 not null,
  fpzl  VARCHAR2(100),
  fpqh  VARCHAR2(10) not null,
  fpzh  VARCHAR2(10) not null
);
comment on table T_SMS_FPXX  is '发票真伪查询表';
comment on column T_SMS_FPXX.fpdm  is '发票代码';
comment on column T_SMS_FPXX.fp_rq  is '发票开具日期';
comment on column T_SMS_FPXX.nsrbm  is '纳税人编码';
comment on column T_SMS_FPXX.fp_je  is '发票金额';
comment on column T_SMS_FPXX.fpzl  is '发票种类名称，如服务业定额发票';
create index IDX_T_SMS_FPXX_FP on T_SMS_FPXX (FPDM, FPQH, FPZH);

/*
alter table T_SMS_JKTX modify jklx_dm VARCHAR2(4);
alter table T_SMS_KKSBTX modify jklx_dm VARCHAR2(4);
T_SMS_FPXX去除主键
*/