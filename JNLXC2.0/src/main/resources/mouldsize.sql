update jnlxc2.jnlxc_mould t1 set t1.mouldSize_dbId=
(SELECT t.dbId FROM jnlxc2.jnlxc_mouldsize t where t.size=t1.name limit 1) where t1.dbId<60000;
