package com.mamazinha.profile.service;

import com.mamazinha.profile.domain.HumorHistory;
import com.mamazinha.profile.repository.HumorHistoryRepository;
import com.mamazinha.profile.service.dto.HumorHistoryDTO;
import com.mamazinha.profile.service.mapper.HumorHistoryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HumorHistory}.
 */
@Service
@Transactional
public class HumorHistoryService {

    private final Logger log = LoggerFactory.getLogger(HumorHistoryService.class);

    private final HumorHistoryRepository humorHistoryRepository;

    private final HumorHistoryMapper humorHistoryMapper;

    public HumorHistoryService(HumorHistoryRepository humorHistoryRepository, HumorHistoryMapper humorHistoryMapper) {
        this.humorHistoryRepository = humorHistoryRepository;
        this.humorHistoryMapper = humorHistoryMapper;
    }

    /**
     * Save a humorHistory.
     *
     * @param humorHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public HumorHistoryDTO save(HumorHistoryDTO humorHistoryDTO) {
        log.debug("Request to save HumorHistory : {}", humorHistoryDTO);
        HumorHistory humorHistory = humorHistoryMapper.toEntity(humorHistoryDTO);
        humorHistory = humorHistoryRepository.save(humorHistory);
        return humorHistoryMapper.toDto(humorHistory);
    }

    /**
     * Partially update a humorHistory.
     *
     * @param humorHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HumorHistoryDTO> partialUpdate(HumorHistoryDTO humorHistoryDTO) {
        log.debug("Request to partially update HumorHistory : {}", humorHistoryDTO);

        return humorHistoryRepository
            .findById(humorHistoryDTO.getId())
            .map(
                existingHumorHistory -> {
                    humorHistoryMapper.partialUpdate(existingHumorHistory, humorHistoryDTO);

                    return existingHumorHistory;
                }
            )
            .map(humorHistoryRepository::save)
            .map(humorHistoryMapper::toDto);
    }

    /**
     * Get all the humorHistories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<HumorHistoryDTO> findAll() {
        log.debug("Request to get all HumorHistories");
        return humorHistoryRepository.findAll().stream().map(humorHistoryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one humorHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HumorHistoryDTO> findOne(Long id) {
        log.debug("Request to get HumorHistory : {}", id);
        return humorHistoryRepository.findById(id).map(humorHistoryMapper::toDto);
    }

    /**
     * Delete the humorHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HumorHistory : {}", id);
        humorHistoryRepository.deleteById(id);
    }
}
