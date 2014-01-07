import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import Constants.Constants;

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
		reservationStations.add(new LoadStoreReservationStation(configuration.get("mem_delay"), configuration.get("mem_nr_load_buffers"), configuration.get("mem_nr_store_buffers")));
		
		CDBFloatValues = new HashSet<>();
		CDBIntValues = new HashSet<>();
	}

	@Override
	public ReservationStation getReservationStation(
			Constants.ReservationStationNames rsName) {
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
			return getReservationStation(Constants.ReservationStationNames.INTADD).issue(inst);
			
		case ADDS:
		case SUBS:
			return getReservationStation(Constants.ReservationStationNames.FPADD).issue(inst);
		
		case MULTS:
			return getReservationStation(Constants.ReservationStationNames.FPMULT).issue(inst);
		case LD:
		case ST:
			return getReservationStation(Constants.ReservationStationNames.LDST).issue(inst);
		default:
			throw new Error("internal error"); // should not get here			
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
				if (register.getState() == Constants.State.Queued
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
				if (register.getState() == Constants.State.Queued
						&& register.getStationName() == cdbValue.getStationName()
						&& register.getDock() == cdbValue.getDock()) {
					register.setData(cdbValue.getData());
					register.setState(Constants.State.Value);
				}
			}
			
			//update reservation stations
			for (ReservationStation rs : reservationStations) {
				rs.updateWithRegister(cdbValue);
			}
		}

	}

}
