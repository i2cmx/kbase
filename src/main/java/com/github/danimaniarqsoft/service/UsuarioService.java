package com.github.danimaniarqsoft.service;

import com.github.danimaniarqsoft.service.dto.UsuarioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.github.danimaniarqsoft.domain.Usuario}.
 */
public interface UsuarioService {

    /**
     * Save a usuario.
     *
     * @param usuarioDTO the entity to save.
     * @return the persisted entity.
     */
    UsuarioDTO save(UsuarioDTO usuarioDTO);

    /**
     * Get all the usuarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UsuarioDTO> findAll(Pageable pageable);


    /**
     * Get the "id" usuario.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UsuarioDTO> findOne(String id);

    /**
     * Delete the "id" usuario.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
