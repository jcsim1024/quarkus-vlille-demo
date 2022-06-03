package org.acme.vlille.dto;

import java.util.List;



public class StationResponseDTO {

	/**
	 * Stations.
	 */
	private List<StationDTO> stations;
	private Long time;

	public StationResponseDTO(List<StationDTO> stations, Long time) {
		this.stations = stations;
		this.time = time;
	}

	public List<StationDTO> getStations() {
		return stations;
	}

	public void setStations(List<StationDTO> stations) {
		this.stations = stations;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}
}
