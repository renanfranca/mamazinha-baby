package com.mamazinha.profile.service;

import com.mamazinha.profile.domain.BreastFeed;
import com.mamazinha.profile.repository.BreastFeedRepository;
import com.mamazinha.profile.service.dto.BreastFeedDTO;
import com.mamazinha.profile.service.mapper.BreastFeedMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BreastFeed}.
 */
@Service
@Transactional
public class BreastFeedService {

    private final Logger log = LoggerFactory.getLogger(BreastFeedService.class);

    private final BreastFeedRepository breastFeedRepository;

    private final BreastFeedMapper breastFeedMapper;

    public BreastFeedService(BreastFeedRepository breastFeedRepository, BreastFeedMapper breastFeedMapper) {
        this.breastFeedRepository = breastFeedRepository;
        this.breastFeedMapper = breastFeedMapper;
    }

    /**
     * Save a breastFeed.
     *
     * @param breastFeedDTO the entity to save.
     * @return the persisted entity.
     */
    public BreastFeedDTO save(BreastFeedDTO breastFeedDTO) {
        log.debug("Request to save BreastFeed : {}", breastFeedDTO);
        BreastFeed breastFeed = breastFeedMapper.toEntity(breastFeedDTO);
        breastFeed = breastFeedRepository.save(breastFeed);
        return breastFeedMapper.toDto(breastFeed);
    }

    /**
     * Partially update a breastFeed.
     *
     * @param breastFeedDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BreastFeedDTO> partialUpdate(BreastFeedDTO breastFeedDTO) {
        log.debug("Request to partially update BreastFeed : {}", breastFeedDTO);

        return breastFeedRepository
            .findById(breastFeedDTO.getId())
            .map(
                existingBreastFeed -> {
                    breastFeedMapper.partialUpdate(existingBreastFeed, breastFeedDTO);

                    return existingBreastFeed;
                }
            )
            .map(breastFeedRepository::save)
            .map(breastFeedMapper::toDto);
    }

    /**
     * Get all the breastFeeds.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BreastFeedDTO> findAll() {
        log.debug("Request to get all BreastFeeds");
        return breastFeedRepository.findAll().stream().map(breastFeedMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one breastFeed by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BreastFeedDTO> findOne(Long id) {
        log.debug("Request to get BreastFeed : {}", id);
        return breastFeedRepository.findById(id).map(breastFeedMapper::toDto);
    }

    /**
     * Delete the breastFeed by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BreastFeed : {}", id);
        breastFeedRepository.deleteById(id);
    }
}
