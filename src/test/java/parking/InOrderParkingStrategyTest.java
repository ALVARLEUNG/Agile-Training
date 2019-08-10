package parking;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static parking.ParkingStrategy.NO_PARKING_LOT;

public class InOrderParkingStrategyTest {

	@Test
    public void testCreateReceipt_givenACarAndAParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 1, Write a test case on InOrderParkingStrategy.createReceipt()
	    * With using Mockito to mock the input parameter */
	    Car car = mock(Car.class);
	    ParkingLot parkingLot = mock(ParkingLot.class);
	    InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();

	    when(car.getName()).thenReturn("BMW");
	    when(parkingLot.getName()).thenReturn("Lot");

	    Receipt receipt = inOrderParkingStrategy.createReceipt(parkingLot, car);

        Assert.assertEquals("BMW", receipt.getCarName());
        Assert.assertEquals("Lot", receipt.getParkingLotName());
    }

    @Test
    public void testCreateNoSpaceReceipt_givenACar_thenGiveANoSpaceReceipt() {

        /* Exercise 1, Write a test case on InOrderParkingStrategy.createNoSpaceReceipt()
         * With using Mockito to mock the input parameter */
        Car car = mock(Car.class);
        InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();

        when(car.getName()).thenReturn("BMW");

        Receipt receipt = inOrderParkingStrategy.createNoSpaceReceipt(car);

        Assert.assertEquals("BMW", receipt.getCarName());
        Assert.assertEquals(NO_PARKING_LOT, receipt.getParkingLotName());

    }

    @Test
    public void testPark_givenNoAvailableParkingLot_thenCreateNoSpaceReceipt(){

	    /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for no available parking lot */

        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
        List<ParkingLot> parkingLots = new ArrayList<>();

        Car car = new Car("BMW");

        inOrderParkingStrategy.park(parkingLots, car);

        Mockito.verify(inOrderParkingStrategy, times(1)).createNoSpaceReceipt(car);
        Mockito.verify(inOrderParkingStrategy, times(0)).createReceipt(any(), any());
    }

    @Test
    public void testPark_givenThereIsOneParkingLotWithSpace_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot */

        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingLot parkingLot = new ParkingLot("Lot", 10);
        parkingLots.add(parkingLot);

        Car car = new Car("BMW");

        inOrderParkingStrategy.park(parkingLots, car);

        Mockito.verify(inOrderParkingStrategy, times(0)).createNoSpaceReceipt(any());
        Mockito.verify(inOrderParkingStrategy, times(1)).createReceipt(any(), any());
    }

    @Test
    public void testPark_givenThereIsOneFullParkingLot_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot but it is full */

        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingLot parkingLot = new ParkingLot("Lot", 10);
        parkingLots.add(parkingLot);

        Car car = new Car("BMW");

        inOrderParkingStrategy.park(parkingLots, car);

        Mockito.verify(inOrderParkingStrategy, times(0)).createNoSpaceReceipt(any());
        Mockito.verify(inOrderParkingStrategy, times(1)).createReceipt(any(), any());


    }

    @Test
    public void testPark_givenThereIsMultipleParkingLotAndFirstOneIsFull_thenCreateReceiptWithUnfullParkingLot(){

        /* Exercise 3: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for multiple parking lot situation */

    }


}
