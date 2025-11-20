package com.arqui_web.pausa_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.arqui_web.pausa_service.model.Pausa;

public interface PausaRepository extends JpaRepository<Pausa, Long> {

	@Query("SELECT p FROM Pausa p WHERE p.viajeId = :viajeId")
	public List<Pausa> getPausasByViaje(@Param("viajeId") Long viajeId);
}
