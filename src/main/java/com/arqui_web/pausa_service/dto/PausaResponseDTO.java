package com.arqui_web.pausa_service.dto;

import java.time.LocalDateTime;

public class PausaResponseDTO {
	private Long id;
	private LocalDateTime inicio;
	private LocalDateTime fin;
	private Long viajeId;

	public PausaResponseDTO() {
		super();
	}

	public PausaResponseDTO(Long id, LocalDateTime inicio, LocalDateTime fin, Long viajeId) {
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

	public PausaResponseDTO(Long id, LocalDateTime inicio, LocalDateTime fin) {
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

	public LocalDateTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public LocalDateTime getFin() {
		return fin;
	}

	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}
}
