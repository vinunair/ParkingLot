package com.github.vinunair.parkinglot.entity;

public class Slot implements Comparable<Slot>{
	
  private Integer slotNumber;
  private Vehicle vehicle;
  private boolean available = true;
  
  public Integer getSlotNumber() {
	return slotNumber;
}

public Slot(int num) {
	  this.slotNumber = num;
  }

public Vehicle getVehicle() {
	return vehicle;
}

public void setVehicle(Vehicle vehicle) {
	this.vehicle = vehicle;
}

public boolean isAvailable() {
	return available;
}

public void setAvailable(boolean available) {
	this.available = available;
}

@Override
public int compareTo(Slot s) {
	return this.slotNumber.compareTo(s.slotNumber);
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((slotNumber == null) ? 0 : slotNumber.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Slot other = (Slot) obj;
	if (slotNumber == null) {
		if (other.slotNumber != null)
			return false;
	} else if (!slotNumber.equals(other.slotNumber))
		return false;
	return true;
}


}
