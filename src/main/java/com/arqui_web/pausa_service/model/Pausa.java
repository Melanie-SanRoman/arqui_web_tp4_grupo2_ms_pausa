package com.arqui_web.pausa_service.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Pausa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private LocalDate inicio;
	@Column
	private LocalDate fin;
	private Long viajeId;

	public Pausa() {
		super();
	}

	public Pausa(LocalDate inicio, LocalDate fin, Long viajeId) {
		super();
		this.inicio = inicio;
		this.fin = fin;
		this.viajeId = viajeId;
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
