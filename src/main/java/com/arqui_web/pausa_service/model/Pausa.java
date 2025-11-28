package com.arqui_web.pausa_service.model;

import java.time.LocalDateTime;

import com.arqui_web.pausa_service.dto.PausaResponseDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pausa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private LocalDateTime inicio;
	@Column
	private LocalDateTime fin;
	@Column
	private Long viajeId;

	public Pausa() {
		super();
	}

	public Pausa(LocalDateTime inicio, LocalDateTime fin, Long viajeId) {
		super();
		this.inicio = inicio;
		this.fin = fin;
		this.viajeId = viajeId;
	}

	public PausaResponseDTO toPausaDTO() {
		return new PausaResponseDTO(this.getId(), this.getInicio(), this.getFin(), this.getViaje());
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

	public Long getViaje() {
		return viajeId;
	}

	public void setViaje(Long viajeId) {
		this.viajeId = viajeId;
	}

	public Long getId() {
		return id;
	}
}
