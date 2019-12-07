package org.acme.vlille.dto;

import java.util.List;

/**
 * Station response DTO.
 */
public class StationResponseDTO {

	/**
	 * Stations.
	 */
	private List<StationDTO> stations;
	private Long time;

	public List<StationDTO> getStations() {
		return stations;
	}

	public void setStations(final List<StationDTO> stations) {
		this.stations = stations;
	}

	public void setTime(final Long l) {
		time = l;
	}
}
