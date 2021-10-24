package com.mamazinha.baby.service;

import com.mamazinha.baby.config.Constants;
import com.mamazinha.baby.domain.HumorHistory;
import com.mamazinha.baby.repository.HumorHistoryRepository;
import com.mamazinha.baby.security.AuthoritiesConstants;
import com.mamazinha.baby.security.SecurityUtils;
import com.mamazinha.baby.service.dto.HumorAverageDTO;
import com.mamazinha.baby.service.dto.HumorHistoryDTO;
import com.mamazinha.baby.service.mapper.HumorHistoryMapper;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
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

    private final Clock clock;

    public HumorHistoryService(HumorHistoryRepository humorHistoryRepository, HumorHistoryMapper humorHistoryMapper, Clock clock) {
        this.humorHistoryRepository = humorHistoryRepository;
        this.humorHistoryMapper = humorHistoryMapper;
        this.clock = clock;
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
            .map(existingHumorHistory -> {
                humorHistoryMapper.partialUpdate(existingHumorHistory, humorHistoryDTO);

                return existingHumorHistory;
            })
            .map(humorHistoryRepository::save)
            .map(humorHistoryMapper::toDto);
    }

    /**
     * Get all the humorHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HumorHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HumorHistories");
        return humorHistoryRepository.findAll(pageable).map(humorHistoryMapper::toDto);
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

    public HumorAverageDTO getTodayAverageHumorHistoryByBabyProfile(Long id, String timeZone) {
        if (!SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN)) {
            String userId = SecurityUtils.getCurrentUserId().orElse(null);
            if (
                humorHistoryRepository.existsByBabyProfileId(id) &&
                !humorHistoryRepository.existsByBabyProfileIdAndBabyProfileUserId(id, userId)
            ) {
                throw new AccessDeniedException(Constants.THAT_IS_NOT_YOUR_BABY_PROFILE);
            }
        }

        LocalDate nowLocalDate = LocalDate.now(clock);
        if (timeZone != null) {
            nowLocalDate = LocalDate.now(clock.withZone(ZoneId.of(timeZone)));
        }

        ZonedDateTime todayMidnight = ZonedDateTime.of(nowLocalDate.atStartOfDay(), ZoneId.systemDefault());
        ZonedDateTime tomorrowMidnight = ZonedDateTime.of(nowLocalDate.plusDays(1l).atStartOfDay(), ZoneId.systemDefault());
        if (timeZone != null) {
            todayMidnight = ZonedDateTime.of(nowLocalDate.atStartOfDay(), ZoneId.of(timeZone));
            tomorrowMidnight = ZonedDateTime.of(nowLocalDate.plusDays(1l).atStartOfDay(), ZoneId.of(timeZone));
        }

        List<HumorHistory> humorHistoryList = humorHistoryRepository.findByBabyProfileIdAndDateGreaterThanEqualAndDateLessThan(
            id,
            todayMidnight,
            tomorrowMidnight
        );

        return new HumorAverageDTO()
            .dayOfWeek(nowLocalDate.getDayOfWeek().getValue())
            .humorAverage(
                humorHistoryList
                    .stream()
                    .mapToInt(humorHistory -> {
                        if (humorHistory.getHumor() != null && humorHistory.getHumor().getValue() != null) {
                            return humorHistory.getHumor().getValue();
                        }
                        return 0;
                    })
                    .sum() /
                humorHistoryList.size()
            );
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
