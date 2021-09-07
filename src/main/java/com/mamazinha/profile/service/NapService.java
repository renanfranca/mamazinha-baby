package com.mamazinha.profile.service;

import com.mamazinha.profile.domain.Nap;
import com.mamazinha.profile.repository.NapRepository;
import com.mamazinha.profile.service.dto.NapDTO;
import com.mamazinha.profile.service.mapper.NapMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Nap}.
 */
@Service
@Transactional
public class NapService {

    private final Logger log = LoggerFactory.getLogger(NapService.class);

    private final NapRepository napRepository;

    private final NapMapper napMapper;

    public NapService(NapRepository napRepository, NapMapper napMapper) {
        this.napRepository = napRepository;
        this.napMapper = napMapper;
    }

    /**
     * Save a nap.
     *
     * @param napDTO the entity to save.
     * @return the persisted entity.
     */
    public NapDTO save(NapDTO napDTO) {
        log.debug("Request to save Nap : {}", napDTO);
        Nap nap = napMapper.toEntity(napDTO);
        nap = napRepository.save(nap);
        return napMapper.toDto(nap);
    }

    /**
     * Partially update a nap.
     *
     * @param napDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NapDTO> partialUpdate(NapDTO napDTO) {
        log.debug("Request to partially update Nap : {}", napDTO);

        return napRepository
            .findById(napDTO.getId())
            .map(
                existingNap -> {
                    napMapper.partialUpdate(existingNap, napDTO);

                    return existingNap;
                }
            )
            .map(napRepository::save)
            .map(napMapper::toDto);
    }

    /**
     * Get all the naps.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<NapDTO> findAll() {
        log.debug("Request to get all Naps");
        return napRepository.findAll().stream().map(napMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one nap by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NapDTO> findOne(Long id) {
        log.debug("Request to get Nap : {}", id);
        return napRepository.findById(id).map(napMapper::toDto);
    }

    /**
     * Delete the nap by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Nap : {}", id);
        napRepository.deleteById(id);
    }
}
