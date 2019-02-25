package com.duguyin.mybatissql.tool;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName MybatisKeyWordsTool
 * @Description
 * @Author LiuYin
 * @Date 2019/2/25 13:46
 */
public class MybatisKeyWordsTool {

    /** Mysql中，以A开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_A =
            {"ACCESSIBLE","ACCOUNT","ACTION","ADD","AFTER","AGAINST","AGGREGATE","ALGORITHM","ALL","ALTER","ALWAYS",
                    "ANALYSE","ANALYZE","AND","ANY","AS","ASC","ASCII","ASENSITIVE","AT","AUTOEXTEND_SIZE","AUTO_INCREMENT","AVG","AVG_ROW_LENGTH"};

    /** Mysql中，以B开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_B =
            {"BACKUP","BEFORE","BEGIN","BETWEEN","BIGINT","BINARY","BINLOG","BIT","BLOB","BLOCK","BOOL","BOOLEAN","BOTH","BTREE","BY","BYTE"};

    /** Mysql中，以C开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_C = {"CACHE","CALL","CASCADE","CASCADED","CASE","CATALOG_NAME","CHAIN","CHANGE","CHANGED",
            "CHANNEL","CHAR","CHARACTER","CHARSET","CHECK","CHECKSUM","CIPHER","CLASS_ORIGIN","CLIENT","CLOSE","COALESCE","CODE","COLLATE",
            "COLLATION","COLUMN","COLUMNS","COLUMN_FORMAT","COLUMN_NAME","COMMENT","COMMIT","COMMITTED","COMPACT","COMPLETION","COMPRESSED",
            "COMPRESSION","CONCURRENT","CONDITION","CONNECTION","CONSISTENT","CONSTRAINT","CONSTRAINT_CATALOG","CONSTRAINT_NAME","CONSTRAINT_SCHEMA",
            "CONTAINS","CONTEXT","CONTINUE","CONVERT","CPU","CREATE","CROSS","CUBE","CURRENT","CURRENT_DATE","CURRENT_TIME","CURRENT_TIMESTAMP",
            "CURRENT_USER","CURSOR","CURSOR_NAME"};

    /** Mysql中，以D开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_D =
            {"DATA","DATABASE","DATABASES","DATAFILE","DATE","DATETIME","DAY","DAY_HOUR","DAY_MICROSECOND","DAY_MINUTE","DAY_SECOND",
                    "DEALLOCATE","DEC","DECIMAL","DECLARE","DEFAULT","DEFAULT_AUTH","DEFINER","DELAYED","DELAY_KEY_WRITE","DELETE","DESC",
                    "DESCRIBE","DES_KEY_FILE","DETERMINISTIC","DIAGNOSTICS","DIRECTORY","DISABLE","DISCARD","DISK","DISTINCT","DISTINCTROW",
                    "DIV","DO","DOUBLE","DROP","DUAL","DUMPFILE","DUPLICATE","DYNAMIC"};

    /** Mysql中，以E开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_E =
            {"EACH","ELSE","ELSEIF","ENABLE","ENCLOSED","ENCRYPTION","END","ENDS","ENGINE","ENGINES","ENUM","ERROR","ERRORS","ESCAPE",
                    "ESCAPED","EVENT","EVENTS","EVERY","EXCHANGE","EXECUTE","EXISTS","EXIT","EXPANSION","EXPIRE","EXPLAIN","EXPORT","EXTENDED","EXTENT_SIZE"};

    /** Mysql中，以F开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_F =
            {"FALSE","FAST","FAULTS","FETCH","FIELDS","FILE","FILE_BLOCK_SIZE","FILTER","FIRST","FIXED","FLOAT","FLOAT4","FLOAT8",
                    "FLUSH","FOLLOWS","FOR","FORCE","FOREIGN","FORMAT","FOUND","FROM","FULL","FULLTEXT","FUNCTION"};

    /** Mysql中，以G开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_G =
            {"GENERAL","GENERATED","GEOMETRY","GEOMETRYCOLLECTION","GET","GET_FORMAT","GLOBAL","GRANT","GRANTS","GROUP","GROUP_REPLICATION"};

    /** Mysql中，以H开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_H =
            {"HANDLER","HASH","HAVING","HELP","HIGH_PRIORITY","HOST","HOSTS","HOUR","HOUR_MICROSECOND","HOUR_MINUTE","HOUR_SECOND"};

    /** Mysql中，以I开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_I =
            {"IDENTIFIED","IF","IGNORE","IGNORE_SERVER_IDS","IMPORT","IN","INDEX","INDEXES","INFILE","INITIAL_SIZE","INNER","INOUT",
                    "INSENSITIVE","INSERT","INSERT_METHOD","INSTALL","INSTANCE","INT","INT1","INT2","INT3","INT4","INT8","INTEGER","INTERVAL",
                    "INTO","INVOKER","IO","IO_AFTER_GTIDS","IO_BEFORE_GTIDS","IO_THREAD","IPC","IS","ISOLATION","ISSUER","ITERATE"};

    /** Mysql中，以J开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_J = {"JOIN","JSON"};

    /** Mysql中，以K开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_K = {"KEY","KEYS","KEY_BLOCK_SIZE","KILL"};

    /** Mysql中，以L开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_L =
            {"LANGUAGE","LAST","LEADING","LEAVE","LEAVES","LEFT","LESS","LEVEL","LIKE","LIMIT","LINEAR","LINES","LINESTRING","LIST",
                    "LOAD","LOCAL","LOCALTIME","LOCALTIMESTAMP","LOCK","LOCKS","LOGFILE","LOGS","LONG","LONGBLOB","LONGTEXT","LOOP","LOW_PRIORITY"};

    /** Mysql中，以M开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_M =
            {"MASTER","MASTER_AUTO_POSITION","MASTER_BIND","MASTER_CONNECT_RETRY","MASTER_DELAY","MASTER_HEARTBEAT_PERIOD","MASTER_HOST",
                    "MASTER_LOG_FILE","MASTER_LOG_POS","MASTER_PASSWORD","MASTER_PORT","MASTER_RETRY_COUNT","MASTER_SERVER_ID","MASTER_SSL","MASTER_SSL_CA",
                    "MASTER_SSL_CAPATH","MASTER_SSL_CERT","MASTER_SSL_CIPHER","MASTER_SSL_CRL","MASTER_SSL_CRLPATH","MASTER_SSL_KEY","MASTER_SSL_VERIFY_SERVER_CERT",
                    "MASTER_TLS_VERSION","MASTER_USER","MATCH","MAXVALUE","MAX_CONNECTIONS_PER_HOUR","MAX_QUERIES_PER_HOUR","MAX_ROWS","MAX_SIZE",
                    "MAX_STATEMENT_TIME","MAX_UPDATES_PER_HOUR","MAX_USER_CONNECTIONS","MEDIUM","MEDIUMBLOB","MEDIUMINT","MEDIUMTEXT","MEMORY","MERGE",
                    "MESSAGE_TEXT","MICROSECOND","MIDDLEINT","MIGRATE","MINUTE","MINUTE_MICROSECOND","MINUTE_SECOND","MIN_ROWS","MOD","MODE","MODIFIES",
                    "MODIFY","MONTH","MULTILINESTRING","MULTIPOINT","MULTIPOLYGON","MUTEX","MYSQL_ERRNO"};

    /** Mysql中，以N开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_N =
            {"NAME","NAMES","NATIONAL","NATURAL","NCHAR","NDB","NDBCLUSTER","NEVER","NEW","NEXT","NO","NODEGROUP","NONBLOCKING","NONE","NOT","NO_WAIT",
                    "NO_WRITE_TO_BINLOG","NULL","NUMBER","NUMERIC","NVARCHAR"};

    /** Mysql中，以O开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_O =
            {"OFFSET","OLD_PASSWORD","ON","ONE","ONLY","OPEN","OPTIMIZE","OPTIMIZER_COSTS","OPTION","OPTIONALLY","OPTIONS","OR","ORDER","OUT","OUTER","OUTFILE","OWNER"};

    /** Mysql中，以P开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_P =
            {"PACK_KEYS","PAGE","PARSER","PARSE_GCOL_EXPR","PARTIAL","PARTITION","PARTITIONING","PARTITIONS","PASSWORD","PHASE","PLUGIN","PLUGINS",
                    "PLUGIN_DIR","POINT","POLYGON","PORT","PRECEDES","PRECISION","PREPARE","PRESERVE","PREV","PRIMARY","PRIVILEGES","PROCEDURE","PROCESSLIST",
                    "PROFILE","PROFILES","PROXY","PURGE"};

    /** Mysql中，以Q开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_Q = {"QUARTER","QUERY","QUICK"};

    /** Mysql中，以R开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_R =
            {"RANGE","READ","READS","READ_ONLY","READ_WRITE","REAL","REBUILD","RECOVER","REDOFILE","REDO_BUFFER_SIZE","REDUNDANT","REFERENCES",
                    "REGEXP","RELAY","RELAYLOG","RELAY_LOG_FILE","RELAY_LOG_POS","RELAY_THREAD","RELEASE","RELOAD","REMOVE","RENAME","REORGANIZE","REPAIR",
                    "REPEAT","REPEATABLE","REPLACE","REPLICATE_DO_DB","REPLICATE_DO_TABLE","REPLICATE_IGNORE_DB","REPLICATE_IGNORE_TABLE","REPLICATE_REWRITE_DB",
                    "REPLICATE_WILD_DO_TABLE","REPLICATE_WILD_IGNORE_TABLE","REPLICATION","REQUIRE","RESET","RESIGNAL","RESTORE","RESTRICT","RESUME","RETURN",
                    "RETURNED_SQLSTATE","RETURNS","REVERSE","REVOKE","RIGHT","RLIKE","ROLLBACK","ROLLUP","ROTATE","ROUTINE","ROW","ROWS","ROW_COUNT","ROW_FORMAT","RTREE"};

    /** Mysql中，以S开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_S =
            {"SAVEPOINT","SCHEDULE","SCHEMA","SCHEMAS","SCHEMA_NAME","SECOND","SECOND_MICROSECOND","SECURITY","SELECT","SENSITIVE","SEPARATOR","SERIAL",
                    "SERIALIZABLE","SERVER","SESSION","SET","SHARE","SHOW","SHUTDOWN","SIGNAL","SIGNED","SIMPLE","SLAVE","SLOW","SMALLINT","SNAPSHOT","SOCKET",
                    "SOME","SONAME","SOUNDS","SOURCE","SPATIAL","SPECIFIC","SQL","SQLEXCEPTION","SQLSTATE","SQLWARNING","SQL_AFTER_GTIDS","SQL_AFTER_MTS_GAPS",
                    "SQL_BEFORE_GTIDS","SQL_BIG_RESULT","SQL_BUFFER_RESULT","SQL_CACHE","SQL_CALC_FOUND_ROWS","SQL_NO_CACHE","SQL_SMALL_RESULT","SQL_THREAD",
                    "SQL_TSI_DAY","SQL_TSI_HOUR","SQL_TSI_MINUTE","SQL_TSI_MONTH","SQL_TSI_QUARTER","SQL_TSI_SECOND","SQL_TSI_WEEK","SQL_TSI_YEAR","SSL","STACKED",
                    "START","STARTING","STARTS","STATS_AUTO_RECALC","STATS_PERSISTENT","STATS_SAMPLE_PAGES","STATUS","STOP","STORAGE","STORED","STRAIGHT_JOIN","STRING",
                    "SUBCLASS_ORIGIN","SUBJECT","SUBPARTITION","SUBPARTITIONS","SUPER","SUSPEND","SWAPS","SWITCHES"};

    /** Mysql中，以T开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_T =
            {"TABLE","TABLES","TABLESPACE","TABLE_CHECKSUM","TABLE_NAME","TEMPORARY","TEMPTABLE","TERMINATED","TEXT","THAN","THEN",
                    "TIME","TIMESTAMP","TIMESTAMPADD","TIMESTAMPDIFF","TINYBLOB","TINYINT","TINYTEXT","TO","TRAILING","TRANSACTION","TRIGGER",
                    "TRIGGERS","TRUE","TRUNCATE","TYPE","TYPES"};

    /** Mysql中，以U开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_U =
            {"UNCOMMITTED","UNDEFINED","UNDO","UNDOFILE","UNDO_BUFFER_SIZE","UNICODE","UNINSTALL","UNION","UNIQUE","UNKNOWN","UNLOCK",
                    "UNSIGNED","UNTIL","UPDATE","UPGRADE","USAGE","USE","USER","USER_RESOURCES","USE_FRM","USING","UTC_DATE","UTC_TIME","UTC_TIMESTAMP"};

    /** Mysql中，以V开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_V = {"VALIDATION","VALUE","VALUES","VARBINARY","VARCHAR","VARCHARACTER","VARIABLES","VARYING","VIEW","VIRTUAL"};

    /** Mysql中，以W开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_W = {"WAIT","WARNINGS","WEEK","WEIGHT_STRING","WHEN","WHERE","WHILE","WITH","WITHOUT","WORK","WRAPPER","WRITE"};

    /** Mysql中，以X开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_X = {"X509","XA","XID","XML","XOR"};

    /** Mysql中，以Y开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_Y = {"YEAR","YEAR_MONTH"};

    /** Mysql中，以Z开头的关键字*/
    private static final String[] MYSQL_KEYWORDS_Z = {"ZEROFILL"};

    private static final Map<Character, Set<String>> KEYWORDS_MAP = new HashMap<>();

    static {
        putKeyWords();
    }

    private static void putKeyWords() {
        KEYWORDS_MAP.put('A', arrayToSet(MYSQL_KEYWORDS_A));
        KEYWORDS_MAP.put('B', arrayToSet(MYSQL_KEYWORDS_B));
        KEYWORDS_MAP.put('C', arrayToSet(MYSQL_KEYWORDS_C));
        KEYWORDS_MAP.put('D', arrayToSet(MYSQL_KEYWORDS_D));
        KEYWORDS_MAP.put('E', arrayToSet(MYSQL_KEYWORDS_E));
        KEYWORDS_MAP.put('F', arrayToSet(MYSQL_KEYWORDS_F));
        KEYWORDS_MAP.put('G', arrayToSet(MYSQL_KEYWORDS_G));
        KEYWORDS_MAP.put('H', arrayToSet(MYSQL_KEYWORDS_H));
        KEYWORDS_MAP.put('I', arrayToSet(MYSQL_KEYWORDS_I));
        KEYWORDS_MAP.put('J', arrayToSet(MYSQL_KEYWORDS_J));
        KEYWORDS_MAP.put('K', arrayToSet(MYSQL_KEYWORDS_K));
        KEYWORDS_MAP.put('L', arrayToSet(MYSQL_KEYWORDS_L));
        KEYWORDS_MAP.put('M', arrayToSet(MYSQL_KEYWORDS_M));
        KEYWORDS_MAP.put('N', arrayToSet(MYSQL_KEYWORDS_N));
        KEYWORDS_MAP.put('O', arrayToSet(MYSQL_KEYWORDS_O));
        KEYWORDS_MAP.put('P', arrayToSet(MYSQL_KEYWORDS_P));
        KEYWORDS_MAP.put('Q', arrayToSet(MYSQL_KEYWORDS_Q));
        KEYWORDS_MAP.put('R', arrayToSet(MYSQL_KEYWORDS_R));
        KEYWORDS_MAP.put('S', arrayToSet(MYSQL_KEYWORDS_S));
        KEYWORDS_MAP.put('T', arrayToSet(MYSQL_KEYWORDS_T));
        KEYWORDS_MAP.put('U', arrayToSet(MYSQL_KEYWORDS_U));
        KEYWORDS_MAP.put('V', arrayToSet(MYSQL_KEYWORDS_V));
        KEYWORDS_MAP.put('W', arrayToSet(MYSQL_KEYWORDS_W));
        KEYWORDS_MAP.put('X', arrayToSet(MYSQL_KEYWORDS_X));
        KEYWORDS_MAP.put('Y', arrayToSet(MYSQL_KEYWORDS_Y));
        KEYWORDS_MAP.put('Z', arrayToSet(MYSQL_KEYWORDS_Z));
    }

    private static Set<String> arrayToSet(String[] strs){
        if(Objects.isNull(strs)){
            return new HashSet<>();
        }
        return Arrays.stream(strs).collect(Collectors.toSet());
    }

    private static Map<Character, Set<String>> getKeywordsMap(){
        return KEYWORDS_MAP;
    }

    /**
     * 判断是不是mysql中的关键字
     * @return
     */
    public static boolean isMysqlKeyWord(String word){

        if(StringTool.isNullOrEmpty(word) || word.trim().length() == 0){
            return false;
        }

        final String trim = word.trim().toUpperCase();
        final char beginChar = trim.charAt(0);

        return getKeywordsMap().get(beginChar).contains(trim);
    }
}
