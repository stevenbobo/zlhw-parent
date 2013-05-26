@javax.persistence.TableGenerator(
    name="EMP_GEN",
    table="GENERATOR_TABLE",
    pkColumnName = "key",
    valueColumnName = "hi",
    pkColumnValue="EMP",
    allocationSize=20
)
@javax.persistence.SequenceGenerator(
    name="SEQ_GEN",
    sequenceName="my_sequence"
)
package com.ZLHW.base.HTable;