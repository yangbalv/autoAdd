package mode;

public class CompanyResponseDto {
    private String AUTHOR;
    private String CREATE_TIME;
    private RECORD RECORD;
    private String addTime;

    @Override
    public String toString() {
        return "CompanyResponseDto{" +
                "AUTHOR='" + AUTHOR + '\n' +
                ", CREATE_TIME='" + CREATE_TIME + '\n' +
                ", RECORD=" + RECORD +
                ", addTime='" + addTime + '\n' +
                '}';
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getAUTHOR() {
        return AUTHOR;
    }

    public void setAUTHOR(String AUTHOR) {
        this.AUTHOR = AUTHOR;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public mode.RECORD getRECORD() {
        return RECORD;
    }

    public void setRECORD(mode.RECORD RECORD) {
        this.RECORD = RECORD;
    }

}
