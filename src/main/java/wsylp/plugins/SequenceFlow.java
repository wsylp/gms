package wsylp.plugins;

import java.io.Serializable;
import java.util.Date;

public class SequenceFlow implements Serializable {

    private static final long serialVersionUID = -1164661902871465047L;
    private String message;//信息（重要，不重要）
    private String createLoginName;// 申请人
    private Date date;// 申请日期

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreateLoginName() {
        return createLoginName;
    }

    public void setCreateLoginName(String createLoginName) {
        this.createLoginName = createLoginName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
