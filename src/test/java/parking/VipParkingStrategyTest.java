package parking;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VipParkingStrategyTest {

    @Mock
    CarDao carDao;

    @InjectMocks
    VipParkingStrategy vipParkingStrategy;

	@Test
    public void testPark_givenAVipCarAndAFullParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 4, Write a test case on VipParkingStrategy.park()
	    * With using Mockito spy, verify and doReturn */
        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingLot parkingLot = mock(ParkingLot.class);
        parkingLots.add(parkingLot);

        when(parkingLot.isFull()).thenReturn(true);
        doReturn(true).when(vipParkingStrategy).isAllowOverPark(any());

        vipParkingStrategy.park(parkingLots, new Car("Audi"));

        Mockito.verify(vipParkingStrategy, times(1)).createReceipt(any(), any());
        Mockito.verify(vipParkingStrategy, times(0)).createNoSpaceReceipt(any());

    }

    @Test
    public void testPark_givenCarIsNotVipAndAFullParkingLog_thenGiveNoSpaceReceipt() {

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */
        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingLot parkingLot = mock(ParkingLot.class);
        parkingLots.add(parkingLot);

        when(parkingLot.isFull()).thenReturn(true);
        doReturn(false).when(vipParkingStrategy).isAllowOverPark(any());

        vipParkingStrategy.park(parkingLots, new Car("BMW"));

        Mockito.verify(vipParkingStrategy, times(0)).createReceipt(any(), any());
        Mockito.verify(vipParkingStrategy, times(1)).createNoSpaceReceipt(any());

    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsVipCar_thenReturnTrue(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        Car car = new Car("Audi");
        when(carDao.isVip(any())).thenReturn(true);

        boolean result = vipParkingStrategy.isAllowOverPark(car);

        Assert.assertTrue(result);

    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsVipCar_thenReturnFalse(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        Car car = new Car("BMW");
        when(carDao.isVip(any())).thenReturn(true);

        boolean result = vipParkingStrategy.isAllowOverPark(car);

        Assert.assertFalse(result);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsNotVipCar_thenReturnFalse(){
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        Car car = new Car("Alto");
        when(carDao.isVip(any())).thenReturn(false);

        boolean result = vipParkingStrategy.isAllowOverPark(car);

        Assert.assertFalse(result);

    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        Car car = new Car("Buick");
        when(carDao.isVip(any())).thenReturn(false);

        boolean result = vipParkingStrategy.isAllowOverPark(car);

        Assert.assertFalse(result);
    }

    private Car createMockCar(String carName) {
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(carName);
        return car;
    }
}
