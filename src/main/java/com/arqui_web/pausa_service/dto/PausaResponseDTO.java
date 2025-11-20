package com.arqui_web.pausa_service.dto;

import java.time.LocalDate;

public class PausaResponseDTO {
	private Long id;
	private LocalDate inicio;
	private LocalDate fin;
	private Long viajeId;

	public PausaResponseDTO() {
		super();
	}

	public PausaResponseDTO(Long id, LocalDate inicio, LocalDate fin, Long viajeId) {
		super();
		this.id = id;
		this.inicio = inicio;
		this.fin = fin;
		this.viajeId = viajeId;
	}

	public Long getViajeId() {
		return viajeId;
	}

	public void setViajeId(Long viajeId) {
		this.viajeId = viajeId;
	}

	public PausaResponseDTO(Long id, LocalDate inicio, LocalDate fin) {
		super();
		this.id = id;
		this.inicio = inicio;
		this.fin = fin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getInicio() {
		return inicio;
	}

	public void setInicio(LocalDate inicio) {
		this.inicio = inicio;
	}

	public LocalDate getFin() {
		return fin;
	}

	public void setFin(LocalDate fin) {
		this.fin = fin;
	}
}
