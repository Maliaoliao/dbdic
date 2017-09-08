package fssc.plan.core.db


import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()

databaseChangeLog(logicalFilePath:"fssc/plan/core/db/2017-09-01-init-table-migration.groovy"){

    changeSet(author: "gxchan", id: "20170831-gxchan-1") {
        if(mhi.isDbType('oracle')){
        <#list sequences as seq>
            createSequence(sequenceName: '${seq}',startValue:"10001")
        </#list>
        }
        <#list tables as table>
        createTable (tableName: "${table.name}", remarks: "${table.comments}") {
            <#list table.columns as col>
            column(name: "${col.name}", type:"${col.type}<#if col.length gt 0>(${col.length})</#if>",remarks: "${col.comments}") {
                constraints(nullable: "<#if col.nullable == 'N'>false<#else>true</#if>")
            }
            </#list>
        }
        <#list table.indexes as idx>
        addUniqueConstraint(columnNames:"${idx.columnNames}",tableName:"${idx.tableName}",constraintName: "${idx.indexName}")
        </#list>
        </#list>

    }
}
