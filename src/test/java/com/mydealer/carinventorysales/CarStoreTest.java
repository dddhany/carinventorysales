package com.mydealer.carinventorysales;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mydealer.carinventorysales.core.WarehouseImpl;
import com.mydealer.carinventorysales.store.CarStore;

public class CarStoreTest {
	private CarStore store;
	
	@BeforeEach
	void setupStore() {
		WarehouseImpl w = new WarehouseImpl();
		store = w;
		w.addCar(new Car("1", "brand1", 100L));
		w.addCar(new Car("2", "brand1", 50L));
		w.addCar(new Car("3", "brand2", 200L));
		w.addCar(new Car("4", "brand3", 500L));
		w.addCar(new Car("5", "brand2", 80L));
	}
	
	@Test
	void testListing() {
		List<Car> carList = store.listByPrice();
		assertEquals(5, carList.size());
		assertEquals("2", carList.get(0).getManufacturingNumber());
		assertEquals(50L, carList.get(0).getPrice());
		assertEquals(80L, carList.get(1).getPrice());
		assertEquals(500L, carList.get(carList.size()-1).getPrice());
	}
	
	@Test
	void testPurchase() {
		List<Car> carList = store.listByPrice();
		Optional<Car> carToPurchaseOpt = carList.stream().filter(c->c.getPrice()==200L).findAny();
		assertTrue(carToPurchaseOpt.isPresent());
		assertTrue(store.purchaseCar(carToPurchaseOpt.get()));
		assertEquals(4,store.listByPrice().size());
	}
}
