package com.mydealer.carinventorysales;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import com.mydealer.carinventorysales.admin.StockNotifier;
import com.mydealer.carinventorysales.admin.StockNotifierImpl;
import com.mydealer.carinventorysales.admin.WarehouseAdmin;
import com.mydealer.carinventorysales.core.WarehouseImpl;
import com.mydealer.carinventorysales.store.CarStore;

public class CarDealerAdminTest {
	
	@Test
	void testCarFields() {
		String manufacturingNo = "abc";
		String brand = "brand1";
		long price = 100L;
		Car c = new Car(manufacturingNo, brand, price);
		assertEquals(manufacturingNo, c.getManufacturingNumber());
		assertEquals(brand, c.getBrand());
		assertEquals(price, c.getPrice());
	}

	@Test
	void testAddRemoveCar() {
		WarehouseImpl w =new WarehouseImpl();
		CarStore store = w;
		WarehouseAdmin admin=w;
		Car c1 = new Car("1", "brand1", 100L);
		assertTrue(admin.addCar(c1));
		assertFalse(admin.addCar(new Car("1", "brand1", 300L)));
		assertEquals(1, store.listByPrice().size());
		Car c2 = new Car("2", "brand1", 200L);
		assertTrue(admin.addCar(c2));
		assertTrue(admin.addCar(new Car("3", "brand2", 500L)));
		assertTrue(admin.removeCar(c2));
		assertFalse(admin.removeCar(c2));
		assertEquals(2, store.listByPrice().size());
		assertEquals(c1.getPrice(), store.listByPrice().get(0).getPrice());
	}
	
	@Test
	void testLowStockNotifier() {
		StockNotifier notifier = mock(StockNotifier.class);
		WarehouseImpl w = new WarehouseImpl(notifier);
		WarehouseAdmin admin=w;
		Car c1 = new Car("1", "brand1", 100L);
		Car c2 = new Car("2", "brand1", 300L);
		assertTrue(admin.addCar(c1));
		assertTrue(admin.addCar(c2));
		assertTrue(admin.addCar(new Car("3", "brand2", 500L)));
		verify(notifier, never()).notifyLowStock(anyInt());
		assertTrue(admin.removeCar(c2));
		verify(notifier).notifyLowStock(anyInt());
		assertTrue(admin.removeCar(c1));
		verify(notifier, times(2)).notifyLowStock(anyInt());
	}
	
	@Test
	void testLowStockNotifierImpl() {
		StockNotifier notifier = new StockNotifierImpl();
		notifier.notifyLowStock(1);
	}
}
