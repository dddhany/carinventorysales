package com.mydealer.carinventorysales;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Car implements Serializable {
	private static final long serialVersionUID = 1L;

	private final String manufacturingNumber;
	private final String brand;
	private final long price;

	public Car(String manufacturingNumber, String brand, long price) {
		this.manufacturingNumber = manufacturingNumber;
		this.brand = brand;
		this.price = price;
	}

	public String getManufacturingNumber() {
		return manufacturingNumber;
	}

	public String getBrand() {
		return brand;
	}

	public long getPrice() {
		return price;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(manufacturingNumber).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Car)) {
			return false;
		}
		if(this==o) {
			return true;
		}
		Car that = (Car) o;
		//Equals comparison based on manufacturingNumber field
		return new EqualsBuilder().append(manufacturingNumber, that.manufacturingNumber).isEquals();
	}
}
