package com.mamazinha.baby.service;

import com.mamazinha.baby.domain.Nap;
import com.mamazinha.baby.repository.NapRepository;
import com.mamazinha.baby.security.AuthoritiesConstants;
import com.mamazinha.baby.security.SecurityUtils;
import com.mamazinha.baby.service.dto.NapDTO;
import com.mamazinha.baby.service.dto.NapTodayDTO;
import com.mamazinha.baby.service.mapper.NapMapper;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
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
 * Service Implementation for managing {@link Nap}.
 */
@Service
@Transactional
public class NapService {

    private final Logger log = LoggerFactory.getLogger(NapService.class);

    private final NapRepository napRepository;

    private final NapMapper napMapper;

    private final Clock clock;

    public NapService(NapRepository napRepository, NapMapper napMapper, Clock clock) {
        this.napRepository = napRepository;
        this.napMapper = napMapper;
        this.clock = clock;
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
            .map(existingNap -> {
                napMapper.partialUpdate(existingNap, napDTO);

                return existingNap;
            })
            .map(napRepository::save)
            .map(napMapper::toDto);
    }

    /**
     * Get all the naps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NapDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Naps");
        if (SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN)) {
            return napRepository.findAll(pageable).map(napMapper::toDto);
        }
        Optional<String> userId = SecurityUtils.getCurrentUserId();
        if (userId.isPresent()) {
            return napRepository.findByBabyProfileUserId(pageable, userId.get()).map(napMapper::toDto);
        }
        return Page.empty();
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

    public NapTodayDTO getTodaySumNapsHoursByBabyProfile(Long id) {
        if (!SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN)) {
            String userId = SecurityUtils.getCurrentUserId().orElse(null);
            if (napRepository.existsByBabyProfileId(id) && !napRepository.existsByBabyProfileIdAndBabyProfileUserId(id, userId)) {
                throw new AccessDeniedException("That is not your baby profile!");
            }
        }

        LocalDate nowLocalDate = LocalDate.now(clock);
        ZonedDateTime todayMidnight = ZonedDateTime.of(nowLocalDate.atStartOfDay(), ZoneOffset.UTC);
        ZonedDateTime tomorrowMidnight = ZonedDateTime.of(nowLocalDate.plusDays(1l).atStartOfDay(), ZoneOffset.UTC);

        List<Nap> napList = napRepository.findByBabyProfileIdAndStartBetweenOrBabyProfileIdAndEndBetween(
            id,
            todayMidnight,
            tomorrowMidnight,
            id,
            todayMidnight,
            tomorrowMidnight
        );

        return new NapTodayDTO().sleepHours(sumTotalNapsInHours(napList, todayMidnight, tomorrowMidnight)).sleepHoursGoal(16);
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

    private Double sumTotalNapsInHours(List<Nap> napList, ZonedDateTime todayMidnight, ZonedDateTime tomorrowMidnight) {
        return (
            napList
                .stream()
                .mapToDouble(nap -> {
                    if (nap.getEnd() == null) {
                        return 0;
                    }
                    if (
                        nap.getStart().isAfter(todayMidnight) &&
                        nap.getStart().isBefore(tomorrowMidnight) &&
                        nap.getEnd().isAfter(todayMidnight) &&
                        nap.getEnd().isBefore(tomorrowMidnight)
                    ) {
                        return ChronoUnit.MINUTES.between(nap.getStart(), nap.getEnd());
                    }
                    if (
                        nap.getStart().isBefore(todayMidnight) &&
                        nap.getEnd().isAfter(todayMidnight) &&
                        nap.getEnd().isBefore(tomorrowMidnight)
                    ) {
                        return ChronoUnit.MINUTES.between(todayMidnight, nap.getEnd());
                    }
                    if (
                        nap.getStart().isAfter(todayMidnight) &&
                        nap.getStart().isBefore(tomorrowMidnight) &&
                        nap.getEnd().isAfter(tomorrowMidnight)
                    ) {
                        return ChronoUnit.MINUTES.between(nap.getStart(), tomorrowMidnight);
                    }

                    return 0;
                })
                .sum() /
            60
        );
    }
}
