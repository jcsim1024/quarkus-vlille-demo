package org.acme.vlille.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Station response DTO.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class StationResponseDTO {

	/**
	 * Stations.
	 */
	private List<StationDTO> stations;
	private Long time;

	public List<StationDTO> getStations() {
		return stations;
	}

	public void setStations(List<StationDTO> stations) {
		this.stations = stations;
	}

	public void setTime(Long l) {
		this.time= l;
	}
}
