package com.arqui_web.pausa_service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arqui_web.pausa_service.dto.PausaResponseDTO;
import com.arqui_web.pausa_service.dto.PausaTotalDTO;
import com.arqui_web.pausa_service.service.PausaService;

@RestController
@RequestMapping("/pausas")
public class PausaController {
	@Autowired
	private PausaService service;

	@PostMapping
	public ResponseEntity<PausaResponseDTO> postViaje(@RequestBody PausaResponseDTO dto, @PathVariable Long viajeId) {
		PausaResponseDTO creada = service.postPausa(dto, viajeId);
		return ResponseEntity.status(HttpStatus.CREATED).body(creada);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PausaResponseDTO> getPausaById(@PathVariable Long id) {
		Optional<PausaResponseDTO> encontrada = service.getPausaById(id);
		return encontrada.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<Iterable<PausaResponseDTO>> getPausas() {
		Iterable<PausaResponseDTO> it = service.getPausas();
		return ResponseEntity.ok(it);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PausaResponseDTO> updatePausa(@RequestBody PausaResponseDTO dto, @PathVariable Long id) {
		return service.updatePausa(dto, id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePausa(@PathVariable Long id) {
		boolean eliminada = service.deletePausa(id);
		if (eliminada) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/viaje/{idViaje}/total-minutos")
	public ResponseEntity<PausaTotalDTO> getMinutosPausaByViaje(@PathVariable Long viajeId) {
		PausaTotalDTO dto = service.getMinutosPausaByViaje(viajeId);

		if (dto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/viaje/{viajeId}/pausas")
	public ResponseEntity<List<PausaResponseDTO>> getPausasByViaje(@PathVariable Long viajeId) {
		List<PausaResponseDTO> pausas = service.getPausasByViaje(viajeId);

		if (pausas == null || pausas.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(pausas);
	}
}
