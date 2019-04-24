public class MyException {
    private String reason;
    private String context;
    private String info;

    public static final String EXIST = "0001";
    public static final String NOT_EXISTED = "0002";
    public static final String INVALID = "0003";
    public static final String NO_ITEM = "0004";
    public static final String NOT_EMPTY = "0005";

    public MyException(String reason) {
        this.setReason(reason);
    }

    public MyException(String reason, String context) {
        this.setReason(reason);
        this.context = context;
    }

    public MyException(String reason, String context, String info) {
        this.setReason(reason);
        this.context = context;
        this.info = info;
    }

    public MyException(Exception cause, String context) {
        this.context = context;
        this.reason = cause.getMessage();
        this.info = cause.toString();
    }

    public String getMessage() {
        String s = "";
        if (reason != null)
            s += ("Reason: " + this.reason + " attribute;");
        if (context != null)
            s += ("\nContext: " + this.context + ";");
        if (info != null)
            s += ("\nAdditional Info: " + this.info);
        return s;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        if (reason == null)
            this.reason = "Null pointer exception!";
        else if (reason.equals("0001"))
            this.reason = "The id of the item you've entered is already existed!";
        else if (reason.equals("0002"))
            this.reason = "The item you want to update is not existed!";
        else if (reason.equals("0003"))
            this.reason = "The item you've entered is not valid!";
        else if (reason.equals("0004"))
            this.reason = "There is no item!";
        else if (reason.equals("0005"))
            this.reason = "The id of the item you cannot enter is empty!";
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return this.getClass().toString() + ":" + this.getMessage();
    }
}
