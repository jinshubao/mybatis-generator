package com.jean.mybatis.generator.support.provider.mysql;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.support.meta.ICatalogMetaData;
import com.jean.mybatis.generator.support.meta.IColumnMetaData;
import com.jean.mybatis.generator.support.meta.ISchemaMetaData;
import com.jean.mybatis.generator.support.meta.ITableMetaData;
import com.jean.mybatis.generator.support.meta.mysql.MySQColumnMetaData;
import com.jean.mybatis.generator.support.meta.mysql.MySQLCatalogMetaData;
import com.jean.mybatis.generator.support.meta.mysql.MySQLSchemaMetaData;
import com.jean.mybatis.generator.support.meta.mysql.MySQLTableMetaData;
import com.jean.mybatis.generator.support.provider.AbstractMetadataProvider;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jinshubao on 2017/4/9.
 */
@Component
public class MySQLMetadataProvider extends AbstractMetadataProvider {

    @Override
    protected ICatalogMetaData getCatalogMetaData(ResultSet rs) throws SQLException {
        //表类别（可能为空）
        String tableCat = rs.getString("TABLE_CAT");
        return new MySQLCatalogMetaData(tableCat);
    }

    @Override
    protected ISchemaMetaData getSchemaMetaData(ResultSet rs) throws SQLException {
        //表类别（可能为空）
        String tableCat = rs.getString("TABLE_CAT");
        //表模式（可能为空）,在oracle中获取的是命名空间,其它数据库未知
        String tableSchema = rs.getString("TABLE_SCHEM");
        return new MySQLSchemaMetaData(tableCat, tableSchema);
    }

    @Override
    protected ITableMetaData getTableMetaData(ResultSet rs) throws SQLException {
        //表类别（可能为空）
        String tableCat = rs.getString("TABLE_CAT");
        //表模式（可能为空）,在oracle中获取的是命名空间,其它数据库未知
        String tableSchema = rs.getString("TABLE_SCHEM");
        //表名
        String tableName = rs.getString("TABLE_NAME");
        //表类型,典型的类型是 "TABLE"、"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS" 和 "SYNONYM"。
        String tableType = rs.getString("TABLE_TYPE");
        //表备注
        String remarks = rs.getString("REMARKS");
        MySQLTableMetaData data = new MySQLTableMetaData();
        data.setTableCatalog(tableCat);
        data.setTableSchema(tableSchema);
        data.setTableName(tableName);
        data.setTableType(tableType);
        data.setRemarks(remarks);
        return data;
    }

    @Override
    protected IColumnMetaData getColumnMetaData(ResultSet rs) throws SQLException {
        //表类别（可能为空）
        String tableCat = rs.getString("TABLE_CAT");
        //表模式（可能为空）,在oracle中获取的是命名空间,其它数据库未知
        String tableSchema = rs.getString("TABLE_SCHEM");
        //表名
        String tableName = rs.getString("TABLE_NAME");
        //列名
        String columnName = rs.getString("COLUMN_NAME");
        //对应的java.sql.Types的SQL类型(列类型ID)
        int dataType = rs.getInt("DATA_TYPE");
        //java.sql.Types类型名称(列类型名称)
        String dataTypeName = rs.getString("TYPE_NAME");
        //列大小
        int columnSize = rs.getInt("COLUMN_SIZE");
        //小数位数
        int decimalDigits = rs.getInt("DECIMAL_DIGITS");
        //基数（通常是10或2） --未知
        int numPrecRadix = rs.getInt("NUM_PREC_RADIX");
        //是否允许为null
        int nullAble = rs.getInt("NULLABLE");
        //列描述
        String remarks = rs.getString("REMARKS");
        //默认值
        String columnDef = rs.getString("COLUMN_DEF");
        // 对于 char 类型，该长度是列中的最大字节数
        int charOctetLength = rs.getInt("CHAR_OCTET_LENGTH");
        /*
         * YES,NO,""
         */
        String isNullAble = rs.getString("IS_NULLABLE");
        MySQColumnMetaData data = new MySQColumnMetaData();
        data.setTableCatalog(tableCat);
        data.setTableSchema(tableSchema);
        data.setTableName(tableName);
        data.setColumnName(columnName);
        data.setDataType(dataType);
        data.setDataTypeName(dataTypeName);
        data.setColumnSize(columnSize);
        data.setDecimalDigits(decimalDigits);
        data.setNumPrecRadix(numPrecRadix);
        data.setNullAble(nullAble);
        data.setRemarks(remarks);
        data.setColumnDef(columnDef);
        data.setCharOctetLength(charOctetLength);
        data.setIsNullAble(isNullAble);
        return data;
    }


    @Override
    public boolean support(DatabaseType databaseType) {
        return databaseType == DatabaseType.MySql;
    }

}
