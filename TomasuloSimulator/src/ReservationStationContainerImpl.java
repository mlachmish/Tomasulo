import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReservationStationContainerImpl implements
		ReservationStationContainer {
	
	List<ReservationStation> reservationStations;
	public static Set<Register<Float>> CDBFloatValues;
	public static Set<Register<Integer>> CDBIntValues;

	
	public ReservationStationContainerImpl(Map<String, Integer> configuration) {
		super();
		//generate all rs from configuration
		reservationStations = new ArrayList<>();
		reservationStations.add(new IntAddReservationStation(configuration.get("int_delay"), configuration.get("int_nr_reservation")));
		reservationStations.add(new FpAddReservationStation(configuration.get("add_delay"), configuration.get("add_nr_reservation")));
		reservationStations.add(new FpMulReservationStation(configuration.get("mul_delay"), configuration.get("mul_nr_reservation")));
		
		CDBFloatValues = new HashSet<>();
		CDBIntValues = new HashSet<>();
	}

	@Override
	public ReservationStation getReservationStation(
			Constatns.ReservationStationNames rsName) {
		for (ReservationStation rs : reservationStations) {
			if (rs.getName() == rsName)
				return rs;
		}
		//Maybe throw an exception
		return null;
	}

	@Override
	public boolean issueInstruction(Instruction inst) {
		switch (inst.getOpcode()) {
		case ADD:
		case ADDI:
		case SUB:
		case SUBI:
			return getReservationStation(Constatns.ReservationStationNames.INTADD).issue(inst);
			
		case ADDS:
		case SUBS:
			return getReservationStation(Constatns.ReservationStationNames.FPADD).issue(inst);
		
		case MULTS:
			return getReservationStation(Constatns.ReservationStationNames.FPMULT).issue(inst);
			
		default:
			return false;
		}
	}

	@Override
	public void excecute() {
		for (ReservationStation rs : reservationStations) {
			rs.excecute();
		}
	}

	@Override
	public void updateFromCDB() {
		
		//update float cdb
		for (Register<Float> cdbValue : CDBFloatValues) {
			//update registers container from Sim
			for (Register<Float> register : Sim.floatRegistersContainer) {
				if (register.getState() == Constatns.State.Queued
						&& register.getStationName() == cdbValue.getStationName()
						&& register.getDock() == cdbValue.getDock()) {
					register.setData(cdbValue.getData());
				}
			}
			
			//update reservation stations
			for (ReservationStation rs : reservationStations) {
				rs.updateWithRegister(cdbValue);
			}
		}
		
		//update int cdb
		for (Register<Integer> cdbValue : CDBIntValues) {
			//update registers container from Sim
			for (Register<Integer> register : Sim.intRegistersContainer) {
				if (register.getState() == Constatns.State.Queued
						&& register.getStationName() == cdbValue.getStationName()
						&& register.getDock() == cdbValue.getDock()) {
					register.setData(cdbValue.getData());
					register.setState(Constatns.State.Value);
				}
			}
			
			//update reservation stations
			for (ReservationStation rs : reservationStations) {
				rs.updateWithRegister(cdbValue);
			}
		}

	}

}
