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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/pausas")
@Tag(name = "Pausas", description = "Endpoints de gestion de pausas")
public class PausaController {
	@Autowired
	private PausaService service;

	@Operation(summary = "Registra una pausa", description = "Crea una pausa vinculada a un viaje")
	@ApiResponse(responseCode = "201", description = "Pausa creada correctamente")
	@PostMapping("/{idViaje}")
	public ResponseEntity<PausaResponseDTO> postPausa(
			@Parameter(description = "DTO que contiene los datos de la pausa a crear") @RequestBody PausaResponseDTO dto,
			@Parameter(description = "ID del viaje a vincular") @PathVariable Long viajeId) {
		PausaResponseDTO creada = service.postPausa(dto, viajeId);
		return ResponseEntity.status(HttpStatus.CREATED).body(creada);
	}

	@Operation(summary = "Obtener una pausa por ID", description = "Devuelve la informacion de una pausa especifica")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Pausa encontrada"),
			@ApiResponse(responseCode = "404", description = "Pausa no encontrada") })
	@GetMapping("/{id}")
	public ResponseEntity<PausaResponseDTO> getPausaById(
			@Parameter(description = "ID de la pausa solicitada") @PathVariable Long id) {
		Optional<PausaResponseDTO> encontrada = service.getPausaById(id);
		return encontrada.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Lista todas las pausas", description = "Devuelve todos las pausas registradas")
	@ApiResponse(responseCode = "200", description = "Lista devuelta correctamente")
	@GetMapping
	public ResponseEntity<Iterable<PausaResponseDTO>> getPausas() {
		Iterable<PausaResponseDTO> it = service.getPausas();
		return ResponseEntity.ok(it);
	}

	@Operation(summary = "Actualiza una pausa", description = "Modifica los datos de una pausa existente")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Pausa actualizada"),
			@ApiResponse(responseCode = "404", description = "Pausa no encontrada") })
	@PutMapping("/{id}")
	public ResponseEntity<PausaResponseDTO> updatePausa(
			@Parameter(description = "DTO que contiene los nuevos datos de la pausa") @RequestBody PausaResponseDTO dto,
			@Parameter(description = "ID de la pausa a actualizar") @PathVariable Long id) {
		return service.updatePausa(dto, id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Elimina una pausa", description = "Elimina una pausa por ID")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Pausa eliminada"),
			@ApiResponse(responseCode = "404", description = "Pausa no encontrada") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePausa(
			@Parameter(description = "ID de la pausa a eliminar") @PathVariable Long id) {
		boolean eliminada = service.deletePausa(id);
		if (eliminada) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Calcula los minutos de pausa en un viaje", description = "Devuelve el total de minutos en pausa para un viaje especfico")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Datos calculados"),
			@ApiResponse(responseCode = "404", description = "Pausas no encontradas") })
	@GetMapping("/viaje/{idViaje}/total-minutos")
	public ResponseEntity<PausaTotalDTO> getMinutosPausaByViaje(
			@Parameter(description = "ID del viaje a calcular sus pausas") @PathVariable Long idViaje) {
		PausaTotalDTO dto = service.getMinutosPausaByViaje(idViaje);

		if (dto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(dto);
	}

	@Operation(summary = "Lista pausas de un viaje", description = "Devuelve todas las pausas asociadas a un viaje")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "No se encontraron pausas asociadas"),
			@ApiResponse(responseCode = "200", description = "Lista devuelta correctamente") })
	@GetMapping("/viaje/{viajeId}/pausas")
	public ResponseEntity<List<PausaResponseDTO>> getPausasByViaje(
			@Parameter(description = "ID del viaje a obtener sus pausas") @PathVariable Long viajeId) {
		List<PausaResponseDTO> pausas = service.getPausasByViaje(viajeId);

		if (pausas == null || pausas.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(pausas);
	}

	@Operation(summary = "Inicia una pausa", description = "Crea una pausa asociado a un viaje")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Pausa iniciada"),
			@ApiResponse(responseCode = "404", description = "Viaje no encontrado") })
	@PostMapping("/iniciar/{viajeId}")
	public ResponseEntity<PausaResponseDTO> iniciarPausa(
			@Parameter(description = "ID del Viaje a pausar") @PathVariable Long viajeId) {
		try {
			PausaResponseDTO dto = service.iniciarPausa(viajeId);
			return ResponseEntity.ok(dto);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Finaliza una pausa", description = "Marca una pausa como finalizada")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Pausa finalizada"),
			@ApiResponse(responseCode = "404", description = "Pausa no encontrada") })
	@PutMapping("/{id}/finalizar")
	public ResponseEntity<PausaResponseDTO> finalizarPausa(
			@Parameter(description = "ID de la pausa a finalizar") @PathVariable Long id) {
		try {
			PausaResponseDTO dto = service.finalizarPausa(id);
			return ResponseEntity.ok(dto);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
