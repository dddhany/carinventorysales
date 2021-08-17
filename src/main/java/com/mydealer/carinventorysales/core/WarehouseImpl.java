package com.mydealer.carinventorysales.core;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import com.mydealer.carinventorysales.Car;
import com.mydealer.carinventorysales.admin.StockNotifier;
import com.mydealer.carinventorysales.admin.StockNotifierImpl;
import com.mydealer.carinventorysales.admin.WarehouseAdmin;
import com.mydealer.carinventorysales.store.CarStore;

public final class WarehouseImpl implements CarStore, WarehouseAdmin {
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final HashSet<Car> cars = new HashSet<>();
	private final StockNotifier notifier;
	
	public WarehouseImpl() {
		this(new StockNotifierImpl());
	}
	
	public WarehouseImpl(StockNotifier notifier) {
		this.notifier = notifier;
	}
	
	@Override
	public boolean addCar(Car c) {
		rwl.writeLock().lock();
		try {
			return cars.add(c);
		} finally {
			rwl.writeLock().unlock();
		}
	}
	@Override
	public boolean removeCar(Car c) {
		rwl.writeLock().lock();
		try {
			boolean removeResult = cars.remove(c);
			notifier.notifyLowStock(cars.size());
			return removeResult;
		} finally {
			rwl.writeLock().unlock();
		}
	}
	@Override
	public List<Car> listByPrice() {
		rwl.readLock().lock();
		try {
			return cars.stream().sorted(Comparator.comparingLong(Car::getPrice)).collect(Collectors.toList());
		} finally {
			rwl.readLock().unlock();
		}
	}
	@Override
	public boolean purchaseCar(Car c) {
		return removeCar(c);
	}
	
}
