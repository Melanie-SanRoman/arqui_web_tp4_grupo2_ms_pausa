package com.arqui_web.pausa_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arqui_web.pausa_service.model.Pausa;

public interface PausaRepository extends JpaRepository<Pausa, Long> {

}
