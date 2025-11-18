package com.arqui_web.pausa_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.arqui_web.pausa_service.service.PausaService;

@RestController
public class PausaController {
	@Autowired
	private PausaService service;
	
	public ResponseEntitity<PausaResponseDTO> putPausa(@RequestBody PausaResponseDTO dto, @PathVariable Long viajeId){
		return 
	}
}
