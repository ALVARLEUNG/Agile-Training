package sales;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SalesAppTest {

    @Mock
    SalesReportDao salesReportDao;
    @Mock
    SalesDao salesDao;
    @Mock
    EcmService ecmService;

    @InjectMocks
    SalesApp mockSalesApp;

    @Test
    public void testGenerateReport_giveSaleNotFound_thenReturnNull() {
        SalesApp salesApp = spy(new SalesApp());
        doReturn(null).when(salesApp).getSales(any());
        salesApp.generateSalesActivityReport("DUMMY", false);
        Mockito.verify(salesApp, times(1)).getSales(any());
    }

    @Test
    public void testGenerateReport_giveSaleIdAndIsNatTrade() {
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

    @Test
    public void testGetHeaders_giveIsNatTradeIsTrue_thenReturnHeadersContainsStringTime() {
        SalesApp salesApp = new SalesApp();
        List<String> headers = salesApp.getHeaders(true);

        Assert.assertEquals("Time", headers.get(3));
        Assert.assertEquals(4, headers.size());
    }

    @Test
    public void testGetHeaders_giveIsNatTradeIsFalse_thenReturnHeadersContainsStringLocalTime() {
        SalesApp salesApp = new SalesApp();
        List<String> headers = salesApp.getHeaders(false);

        Assert.assertEquals("Local Time", headers.get(3));
        Assert.assertEquals(4, headers.size());
    }

    @Test
    public void testGetSales_giveSalesIdIsNull_thenReturnNull() {
        Sales sales = mockSalesApp.getSales(null);
        Assert.assertNull(sales);
    }

    @Test
    public void testGetSales_giveSalesIdAndHaveActiveSales_thenReturnSales() {
        Sales sales = mock(Sales.class);
        when(sales.getEffectiveFrom()).thenReturn(getYesterday());
        when(sales.getEffectiveTo()).thenReturn(getTomorrow());
        when(salesDao.getSalesBySalesId(anyString())).thenReturn(sales);

        Sales result = mockSalesApp.getSales(anyString());

        Assert.assertNotNull(result);
    }

    @Test
    public void testGetSales_giveSalesIdAndTodayAfterLastDate_thenReturnNull() {
        Sales sales = mock(Sales.class);
        when(sales.getEffectiveTo()).thenReturn(getYesterday());
        when(sales.getEffectiveFrom()).thenReturn(getYesterday());
        when(salesDao.getSalesBySalesId(anyString())).thenReturn(sales);

        Sales result = mockSalesApp.getSales(anyString());

        Assert.assertNull(result);
    }


    @Test
    public void testGetSales_giveSalesIdAndTodayBeforeFromDate_thenReturnNull() {
        Sales sales = mock(Sales.class);
        when(sales.getEffectiveTo()).thenReturn(getTomorrow());
        when(sales.getEffectiveFrom()).thenReturn(getTomorrow());
        when(salesDao.getSalesBySalesId(anyString())).thenReturn(sales);
        Sales result = mockSalesApp.getSales(anyString());

        Assert.assertNull(result);
    }

    @Test
    public void testUploadReportDocument_giveSalesActivityReport_thenCallUploadDocument() {
        mockSalesApp.uploadReportDocument(new SalesActivityReport());
        Mockito.verify(ecmService, times(1)).uploadDocument(anyString());
    }

    @Test
    public void testGetSalesReportDataList_giveSales_thenReturnReportDataList() {
        List<SalesReportData> reportDataList = Arrays.asList(new SalesReportData());
        when(salesReportDao.getReportData(any())).thenReturn(reportDataList);

        List<SalesReportData> result = mockSalesApp.getSalesReportDataList(any());

        Assert.assertEquals(1, result.size());
        Mockito.verify(salesReportDao, times(1)).getReportData(any());

    }

    private Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    private Date getTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +1);
        return calendar.getTime();
    }


}
