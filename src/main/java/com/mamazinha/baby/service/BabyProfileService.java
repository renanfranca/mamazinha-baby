package com.mamazinha.baby.service;

import com.mamazinha.baby.domain.BabyProfile;
import com.mamazinha.baby.repository.BabyProfileRepository;
import com.mamazinha.baby.security.AuthoritiesConstants;
import com.mamazinha.baby.security.SecurityUtils;
import com.mamazinha.baby.service.dto.BabyProfileDTO;
import com.mamazinha.baby.service.mapper.BabyProfileMapper;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BabyProfile}.
 */
@Service
@Transactional
public class BabyProfileService {

    private final Logger log = LoggerFactory.getLogger(BabyProfileService.class);

    private final BabyProfileRepository babyProfileRepository;

    private final BabyProfileMapper babyProfileMapper;

    public BabyProfileService(BabyProfileRepository babyProfileRepository, BabyProfileMapper babyProfileMapper) {
        this.babyProfileRepository = babyProfileRepository;
        this.babyProfileMapper = babyProfileMapper;
    }

    /**
     * Save a babyProfile.
     *
     * @param babyProfileDTO the entity to save.
     * @return the persisted entity.
     */
    public BabyProfileDTO save(BabyProfileDTO babyProfileDTO) {
        log.debug("Request to save BabyProfile : {}", babyProfileDTO);
        BabyProfile babyProfile = babyProfileMapper.toEntity(babyProfileDTO);
        if (!SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN)) {
            Optional<String> userId = SecurityUtils.getCurrentUserId();
            if (userId.isPresent()) {
                babyProfile.userId(userId.get());
            }
        }
        babyProfile = babyProfileRepository.save(babyProfile);
        if (Boolean.TRUE.equals(babyProfile.getMain())) {
            updateOthersBabyProfileFromSameUserToMainFalse(babyProfile.getUserId(), babyProfile.getId());
        }
        return babyProfileMapper.toDto(babyProfile);
    }

    /**
     * Partially update a babyProfile.
     *
     * @param babyProfileDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BabyProfileDTO> partialUpdate(BabyProfileDTO babyProfileDTO) {
        log.debug("Request to partially update BabyProfile : {}", babyProfileDTO);

        return babyProfileRepository
            .findById(babyProfileDTO.getId())
            .map(
                existingBabyProfile -> {
                    babyProfileMapper.partialUpdate(existingBabyProfile, babyProfileDTO);

                    return existingBabyProfile;
                }
            )
            .map(babyProfileRepository::save)
            .map(babyProfileMapper::toDto);
    }

    /**
     * Get all the babyProfiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BabyProfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BabyProfiles");
        if (SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN)) {
            return babyProfileRepository.findAll(pageable).map(babyProfileMapper::toDto);
        }
        Optional<String> userId = SecurityUtils.getCurrentUserId();
        if (userId.isPresent()) {
            return babyProfileRepository.findByUserId(pageable, userId.get()).map(babyProfileMapper::toDto);
        }
        return Page.empty();
    }

    /**
     * Get one babyProfile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BabyProfileDTO> findOne(Long id) {
        log.debug("Request to get BabyProfile : {}", id);
        return babyProfileRepository.findById(id).map(babyProfileMapper::toDto);
    }

    /**
     * Delete the babyProfile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BabyProfile : {}", id);
        babyProfileRepository.deleteById(id);
    }

    private void updateOthersBabyProfileFromSameUserToMainFalse(String userId, Long id) {
        babyProfileRepository.saveAll(
            babyProfileRepository
                .findByUserIdAndIdNotIn(userId, Arrays.asList(id))
                .stream()
                .map(
                    babyProfile -> {
                        babyProfile.main(false);
                        return babyProfile;
                    }
                )
                .collect(Collectors.toList())
        );
    }
}
