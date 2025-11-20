package com.arqui_web.pausa_service.dto;

public class PausaTotalDTO {
	private Long viajeId;
	private Double minutosTotales;

	public PausaTotalDTO() {
		super();
	}

	public PausaTotalDTO(Long viajeId, Double minutosTotales) {
		super();
		this.viajeId = viajeId;
		this.minutosTotales = minutosTotales;
	}

	public Long getViajeId() {
		return viajeId;
	}

	public void setViajeId(Long viajeId) {
		this.viajeId = viajeId;
	}

	public Double getMinutosTotales() {
		return minutosTotales;
	}

	public void setMinutosTotales(Double minutosTotales) {
		this.minutosTotales = minutosTotales;
	}
}
