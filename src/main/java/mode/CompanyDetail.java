package mode;

public class CompanyDetail {
    private String companyName;
    private String addTime;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "CompanyDetail{" +
                "companyName='" + companyName + '\'' +
                ", addTime='" + addTime + '\'' +
                '}';
    }
}
