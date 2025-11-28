package com.arqui_web.pausa_service.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arqui_web.pausa_service.dto.PausaResponseDTO;
import com.arqui_web.pausa_service.dto.PausaTotalDTO;
import com.arqui_web.pausa_service.model.Pausa;
import com.arqui_web.pausa_service.repository.PausaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PausaService {
	@Autowired
	private PausaRepository repository;

	private static final Logger log = LoggerFactory.getLogger(PausaService.class);

	public PausaResponseDTO postPausa(PausaResponseDTO dto, Long viajeId) {
		Pausa p = new Pausa(dto.getInicio(), dto.getFin(), viajeId);

		Pausa guardada = repository.save(p);
		log.info("Pausa creada con ID {}", guardada.getId());

		return p.toPausaDTO();
	}

	public Optional<PausaResponseDTO> getPausaById(Long id) {
		return repository.findById(id).map(p -> {

			PausaResponseDTO dto = p.toPausaDTO();
			return dto;
		});
	}

	public Iterable<PausaResponseDTO> getPausas() {
		return repository.findAll().stream().map(p -> p.toPausaDTO()).toList();
	}

	public Optional<PausaResponseDTO> updatePausa(PausaResponseDTO dto, Long id) {
		return repository.findById(id).map(p -> {
			p.setInicio(dto.getInicio());
			p.setFin(dto.getFin());
			p.setViaje(dto.getViajeId());

			repository.save(p);
			log.info("Pausa con ID {} actualizada correctamente", id);

			return p.toPausaDTO();
		});
	}

	public Boolean deletePausa(Long id) {
		Optional<Pausa> pausa = repository.findById(id);

		try {
			if (pausa == null) {
				log.error("Error, no se encontro pausa con id {}", id);
				return false;
			} else {
				repository.deleteById(id);
				log.info("Pausa con ID {} eliminada correctamente", id);
				return true;
			}
		} catch (Exception e) {
			log.error("Error eliminando pausa con id {}", id);
			return false;
		}
	}

	public PausaTotalDTO getMinutosPausaByViaje(Long viajeId) {
		List<Pausa> pausas = repository.getPausasByViaje(viajeId);

		if (pausas == null || pausas.isEmpty()) {
			return new PausaTotalDTO(viajeId, 0D);
		}

		double minutosTotales = pausas.stream().mapToDouble(p -> {
			LocalDateTime inicio = p.getInicio();
			LocalDateTime fin = p.getFin();

			if (fin == null) {
				fin = LocalDateTime.now();
			}

			long segundos = ChronoUnit.SECONDS.between(inicio, fin);

			return segundos / 60.0;
		}).sum();

		return new PausaTotalDTO(viajeId, minutosTotales);
	}

	public List<PausaResponseDTO> getPausasByViaje(Long viajeId) {
		List<PausaResponseDTO> pausas = repository.getPausasByViaje(viajeId).stream().map(Pausa::toPausaDTO)
				.collect(Collectors.toList());
		return pausas;
	}

	public PausaResponseDTO iniciarPausa(Long idViaje) {
		Pausa pausa = new Pausa();
		pausa.setInicio(LocalDateTime.now());
		pausa.setViaje(idViaje);

		Pausa guardada = repository.save(pausa);
		log.info("Pausa iniciado con ID {}", guardada.getId());

		return pausa.toPausaDTO();
	}

	public PausaResponseDTO finalizarPausa(Long idPausa) {
		Pausa pausa = repository.findById(idPausa).orElseThrow(() -> new EntityNotFoundException("Pausa no existe"));

		pausa.setFin(LocalDateTime.now());

		Pausa guardada = repository.saveAndFlush(pausa);
		log.info("Pausa finalizada con ID {}", guardada.getId());

		return pausa.toPausaDTO();
	}
}
