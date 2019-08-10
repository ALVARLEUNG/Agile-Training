package sales;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

    SalesReportDao salesReportDao;
    SalesDao salesDao;
    EcmService ecmService;

    public SalesApp() {
        this.salesReportDao = new SalesReportDao();
        this.salesDao = new SalesDao();
        this.ecmService = new EcmService();
    }

    public void generateSalesActivityReport(String salesId, boolean isNatTrade) {

        Sales sales = getSales(salesId);
        if (sales == null) return;

        List<SalesReportData> reportDataList = getSalesReportDataList(sales);

        List<String> headers = getHeaders(isNatTrade);

        SalesActivityReport report = this.generateReport(headers, reportDataList);

        uploadReportDocument(report);
    }

    protected void uploadReportDocument(SalesActivityReport report) {
        ecmService.uploadDocument(report.toXml());
    }

    public List<String> getHeaders(boolean isNatTrade) {
        List<String> headers = null;
        if (isNatTrade) {
            headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
        } else {
            headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
        }
        return headers;
    }

    public List<SalesReportData> getSalesReportDataList(Sales sales) {
        return salesReportDao.getReportData(sales);
    }

    public Sales getSales(String salesId) {
        if (salesId == null) return null;

        Sales sales = salesDao.getSalesBySalesId(salesId);
        Date today = new Date();
        if (today.after(sales.getEffectiveTo())
                || today.before(sales.getEffectiveFrom())) {
            return null;
        }
        return sales;
    }

    protected SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
        // TODO Auto-generated method stub
        return null;
    }

}
