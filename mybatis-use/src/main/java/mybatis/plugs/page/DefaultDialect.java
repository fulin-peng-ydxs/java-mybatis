package mybatis.plugs.page;

public class DefaultDialect{


    public String getLimitString(String sql, int offset, int limit) {
        return this.getLimitString(sql, offset, Integer.toString(offset), limit, Integer.toString(limit));
    }

    public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        return offset > 0 ? sql + " limit " + offsetPlaceholder + "," + limitPlaceholder : sql + " limit " + limitPlaceholder;
    }
}
