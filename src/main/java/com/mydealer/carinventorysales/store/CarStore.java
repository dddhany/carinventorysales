package com.mydealer.carinventorysales.store;

import java.util.List;

import com.mydealer.carinventorysales.Car;

public interface CarStore {
	List<Car> listByPrice();
	boolean purchaseCar(Car c);
}
