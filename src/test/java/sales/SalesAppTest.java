package sales;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class SalesAppTest {

    @Test
    public void testGenerateReport_giveSaleNotFound_thenReturnNull() {
        SalesApp salesApp = spy(new SalesApp());
        doReturn(null).when(salesApp).getSales(any());
        salesApp.generateSalesActivityReport("DUMMY", false);
        Mockito.verify(salesApp, times(1)).getSales(any());
    }

    @Test
    public void testGenerateReport_giveSaleIdAndIsNatTrade() {
        List<String> Headers = new ArrayList<>();
        SalesApp salesApp = spy(new SalesApp());

        doReturn(new Sales()).when(salesApp).getSales(any());
        doReturn(new ArrayList<String>()).when(salesApp).getHeaders(anyBoolean());
        doReturn(new ArrayList<SalesReportData>()).when(salesApp).getSalesReportDataList(any());
        doReturn(new SalesActivityReport()).when(salesApp).generateReport(anyList(), anyList());
        doNothing().when(salesApp).uploadReportDocument(any());

        salesApp.generateSalesActivityReport("DUMMY", false);
        Mockito.verify(salesApp, times(1)).getSales(any());
        Mockito.verify(salesApp, times(1)).getSalesReportDataList(any());
        Mockito.verify(salesApp, times(1)).generateReport(anyList(), anyList());
        Mockito.verify(salesApp, times(1)).getHeaders(anyBoolean());
        Mockito.verify(salesApp, times(1)).uploadReportDocument(any());
    }


}
