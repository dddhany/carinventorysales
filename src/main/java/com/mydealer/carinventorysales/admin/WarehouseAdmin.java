package com.mydealer.carinventorysales.admin;

import com.mydealer.carinventorysales.Car;

public interface WarehouseAdmin {
	boolean addCar(Car c);
	boolean removeCar(Car c);
}
