package hospital.dto;

import java.util.ArrayList;
import java.util.List;

import hospital.Elevator;
import hospital.Person;

public class ElevatorInfoDTO {
	
	private String identification;
	private int peopleCounter;
	private List<Integer> peopleDestinations;
	
	public ElevatorInfoDTO(Elevator elevator) {
		this.identification = elevator.getIdentification();
		this.peopleCounter =  elevator.peopleInElevator();
		this.peopleDestinations = new ArrayList<>();
		for (Person person: elevator.getSpace()) {
			this.peopleDestinations.add(person.getTargetFloor());
		}
	}

	public String getIdentification() {
		return identification;
	}

	public int getPeopleCounter() {
		return peopleCounter;
	}
	
	public String getPeopleCounterAsString() {
		return String.valueOf(peopleCounter);
	}

	public List<Integer> getPeopleDestinations() {
		return peopleDestinations;
	}
	
	public String getPeopleDestinationsAsString() {
		String peopleDest = "";
		for (int i: this.peopleDestinations) {
			peopleDest = peopleDest +  i + ", ";
		}
		if (peopleDest.length() > 0) {
			peopleDest = peopleDest.substring(0, peopleDest.length()-2);
		}
		return peopleDest;
	}
	
	


}
