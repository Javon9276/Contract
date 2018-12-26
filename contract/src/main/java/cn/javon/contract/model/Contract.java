package cn.javon.contract.model;

/**
 * @author Javon
 * @since 2018-12-14
 **/
public class Contract {

    /**
     * 合同号
     */
    private String contractNo;

    /**
     * 甲方
     */
    private String partyA;

    /**
     * 己方
     */
    private String partyB;

    /**
     * 开始时间
     */
    private String startYear;
    private String startMonth;
    private String startDay;

    /**
     * 结束时间
     */
    private String endYear;
    private String endMonth;
    private String endDay;

    /**
     * 当天
     */
    private String nowDate;

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getPartyA() {
        return partyA;
    }

    public void setPartyA(String partyA) {
        this.partyA = partyA;
    }

    public String getPartyB() {
        return partyB;
    }

    public void setPartyB(String partyB) {
        this.partyB = partyB;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getNowDate() {
        return nowDate;
    }

    public void setNowDate(String nowDate) {
        this.nowDate = nowDate;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "contractNo='" + contractNo + '\'' +
                ", partyA='" + partyA + '\'' +
                ", partyB='" + partyB + '\'' +
                ", startYear='" + startYear + '\'' +
                ", startMonth='" + startMonth + '\'' +
                ", startDay='" + startDay + '\'' +
                ", endYear='" + endYear + '\'' +
                ", endMonth='" + endMonth + '\'' +
                ", endDay='" + endDay + '\'' +
                ", nowDate='" + nowDate + '\'' +
                '}';
    }
}
