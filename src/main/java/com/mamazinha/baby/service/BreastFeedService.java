package com.mamazinha.baby.service;

import com.mamazinha.baby.domain.BreastFeed;
import com.mamazinha.baby.repository.BreastFeedRepository;
import com.mamazinha.baby.security.AuthoritiesConstants;
import com.mamazinha.baby.security.SecurityUtils;
import com.mamazinha.baby.service.dto.BreastFeedDTO;
import com.mamazinha.baby.service.mapper.BreastFeedMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final BabyProfileService babyProfileService;

    public BreastFeedService(
        BreastFeedRepository breastFeedRepository,
        BreastFeedMapper breastFeedMapper,
        BabyProfileService babyProfileService
    ) {
        this.breastFeedRepository = breastFeedRepository;
        this.breastFeedMapper = breastFeedMapper;
        this.babyProfileService = babyProfileService;
    }

    /**
     * Save a breastFeed.
     *
     * @param breastFeedDTO the entity to save.
     * @return the persisted entity.
     */
    public BreastFeedDTO save(BreastFeedDTO breastFeedDTO) {
        log.debug("Request to save BreastFeed : {}", breastFeedDTO);
        babyProfileService.verifyBabyProfileOwner(breastFeedDTO.getBabyProfile());
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
            .map(existingBreastFeed -> {
                babyProfileService.verifyBabyProfileOwner(existingBreastFeed.getBabyProfile());
                breastFeedMapper.partialUpdate(existingBreastFeed, breastFeedDTO);

                return existingBreastFeed;
            })
            .map(breastFeedRepository::save)
            .map(breastFeedMapper::toDto);
    }

    /**
     * Get all the breastFeeds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BreastFeedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BreastFeeds");
        if (SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN)) {
            return breastFeedRepository.findAll(pageable).map(breastFeedMapper::toDto);
        }
        Optional<String> userId = SecurityUtils.getCurrentUserId();
        if (userId.isPresent()) {
            return breastFeedRepository.findAllByBabyProfileUserId(pageable, userId.get()).map(breastFeedMapper::toDto);
        }
        return Page.empty();
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
        Optional<BreastFeedDTO> breastFeedDTOOptional = breastFeedRepository.findById(id).map(breastFeedMapper::toDto);
        if (breastFeedDTOOptional.isPresent()) {
            babyProfileService.verifyBabyProfileOwner(breastFeedDTOOptional.get().getBabyProfile());
        }
        return breastFeedDTOOptional;
    }

    @Transactional(readOnly = true)
    public List<BreastFeedDTO> getAllIncompleteBreastFeedsByBabyProfile(Long id) {
        babyProfileService.verifyBabyProfileOwner(id);

        return breastFeedRepository
            .findAllByBabyProfileIdAndEndIsNullOrderByStartDesc(id)
            .stream()
            .map(breastFeedMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Delete the breastFeed by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BreastFeed : {}", id);
        Optional<BreastFeedDTO> breastFeedDTOOptional = breastFeedRepository.findById(id).map(breastFeedMapper::toDto);
        if (breastFeedDTOOptional.isPresent()) {
            babyProfileService.verifyBabyProfileOwner(breastFeedDTOOptional.get().getBabyProfile());
        }
        breastFeedRepository.deleteById(id);
    }
}
