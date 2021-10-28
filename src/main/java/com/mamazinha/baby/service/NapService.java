package com.mamazinha.baby.service;

import com.mamazinha.baby.config.Constants;
import com.mamazinha.baby.domain.Nap;
import com.mamazinha.baby.domain.enumeration.Place;
import com.mamazinha.baby.repository.NapRepository;
import com.mamazinha.baby.security.AuthoritiesConstants;
import com.mamazinha.baby.security.SecurityUtils;
import com.mamazinha.baby.service.dto.FavoriteNapPlaceDTO;
import com.mamazinha.baby.service.dto.HumorAverageDTO;
import com.mamazinha.baby.service.dto.NapDTO;
import com.mamazinha.baby.service.dto.NapLastCurrentWeekDTO;
import com.mamazinha.baby.service.dto.NapTodayDTO;
import com.mamazinha.baby.service.dto.NapWeekDTO;
import com.mamazinha.baby.service.mapper.NapMapper;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
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

    public NapTodayDTO getTodaySumNapsHoursByBabyProfile(Long id, String timeZone) {
        verifyAuthorizedOperation(id);

        LocalDate nowLocalDate = LocalDate.now(clock);
        if (timeZone != null) {
            nowLocalDate = LocalDate.now(clock.withZone(ZoneId.of(timeZone)));
        }

        return new NapTodayDTO().sleepHours(sumTotalNapsInHoursByDate(id, timeZone, nowLocalDate)).sleepHoursGoal(16);
    }

    public NapLastCurrentWeekDTO getLastWeekCurrentWeekSumNapsHoursEachDayByBabyProfile(Long id, String timeZone) {
        verifyAuthorizedOperation(id);

        LocalDate nowLocalDate = LocalDate.now(clock);
        if (timeZone != null) {
            nowLocalDate = LocalDate.now(clock.withZone(ZoneId.of(timeZone)));
        }
        // Get first day of week
        LocalDate startOfWeek = nowLocalDate.with(DayOfWeek.MONDAY);
        // Get last day of week
        LocalDate endOfWeek = nowLocalDate.with(DayOfWeek.SUNDAY);
        // Get first day of last week
        LocalDate startOfLastWeek = startOfWeek.minusDays(1).with(DayOfWeek.MONDAY);
        // Get last day of last week
        LocalDate endOfLastWeek = startOfLastWeek.with(DayOfWeek.SUNDAY);

        NapLastCurrentWeekDTO napLastCurrentWeekDTO = new NapLastCurrentWeekDTO().sleepHoursGoal(16);
        napLastCurrentWeekDTO.currentWeekNaps(sumEachDayTotalNapsInHours(startOfWeek, endOfWeek, id, timeZone));
        napLastCurrentWeekDTO.lastWeekNaps(sumEachDayTotalNapsInHours(startOfLastWeek, endOfLastWeek, id, timeZone));

        return napLastCurrentWeekDTO;
    }

    public HumorAverageDTO getTodayAverageNapHumorByBabyProfile(Long id, String timeZone) {
        verifyAuthorizedOperation(id);

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

        List<Nap> napList = napRepository.findByBabyProfileIdAndStartGreaterThanEqualAndEndLessThan(id, todayMidnight, tomorrowMidnight);

        if (napList.isEmpty()) {
            return null;
        }
        return new HumorAverageDTO()
            .dayOfWeek(nowLocalDate.getDayOfWeek().getValue())
            .humorAverage(
                napList
                    .stream()
                    .mapToInt(nap -> {
                        if (nap.getHumor() != null && nap.getHumor().getValue() != null) {
                            return nap.getHumor().getValue();
                        }
                        return 0;
                    })
                    .sum() /
                napList.size()
            );
    }

    private void verifyAuthorizedOperation(Long id) {
        if (!SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN)) {
            String userId = SecurityUtils.getCurrentUserId().orElse(null);
            if (napRepository.existsByBabyProfileId(id) && !napRepository.existsByBabyProfileIdAndBabyProfileUserId(id, userId)) {
                throw new AccessDeniedException(Constants.THAT_IS_NOT_YOUR_BABY_PROFILE);
            }
        }
    }

    public FavoriteNapPlaceDTO getFavoriteNapPlaceFromLastDaysByBabyProfile(Long id, Integer lastDays, String timeZone) {
        verifyAuthorizedOperation(id);

        LocalDate nowLocalDate = LocalDate.now(clock);
        ZonedDateTime rightNow = ZonedDateTime.now(ZoneId.systemDefault());
        ZonedDateTime daysAgo = ZonedDateTime.of(nowLocalDate.minusDays(lastDays).atStartOfDay(), ZoneId.systemDefault());
        if (timeZone != null) {
            nowLocalDate = LocalDate.now(clock.withZone(ZoneId.of(timeZone)));
            rightNow = ZonedDateTime.now(ZoneId.of(timeZone));
            daysAgo = ZonedDateTime.of(nowLocalDate.minusDays(lastDays).atStartOfDay(), ZoneId.of(timeZone));
        }

        List<Nap> napList = napRepository.findByBabyProfileIdAndStartGreaterThanEqualAndEndLessThan(id, daysAgo, rightNow);

        if (napList.isEmpty()) {
            return new FavoriteNapPlaceDTO().periodInDays(lastDays);
        }
        //https://stackoverflow.com/a/47844261/1184154
        Map<Place, Long> groupByPlaceMap = napList
            .stream()
            .filter(nap -> nap.getPlace() != null)
            .collect(Collectors.groupingBy(Nap::getPlace, Collectors.counting()));
        //https://www.baeldung.com/java-find-map-max
        Optional<Entry<Place, Long>> maxPlaceEntry = groupByPlaceMap.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue));
        return new FavoriteNapPlaceDTO()
            .periodInDays(lastDays)
            .favoritePlace(maxPlaceEntry.isPresent() ? maxPlaceEntry.get().getKey() : null)
            .amountOfTimes(maxPlaceEntry.isPresent() ? maxPlaceEntry.get().getValue() : 0l);
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

    private List<NapWeekDTO> sumEachDayTotalNapsInHours(LocalDate startDate, LocalDate endDate, Long babyProfileId, String timeZone) {
        List<NapWeekDTO> napWeekDTOList = new ArrayList<>();
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            napWeekDTOList.add(
                new NapWeekDTO()
                    .dayOfWeek(currentDate.getDayOfWeek().getValue())
                    .sleepHours(sumTotalNapsInHoursByDate(babyProfileId, timeZone, currentDate))
            );
            currentDate = currentDate.plusDays(1);
        }
        return napWeekDTOList;
    }

    private Double sumTotalNapsInHoursByDate(Long babyProfileId, String timeZone, LocalDate localDate) {
        ZonedDateTime todayMidnight = ZonedDateTime.of(localDate.atStartOfDay(), ZoneId.systemDefault());
        ZonedDateTime tomorrowMidnight = ZonedDateTime.of(localDate.plusDays(1l).atStartOfDay(), ZoneId.systemDefault());
        if (timeZone != null) {
            todayMidnight = ZonedDateTime.of(localDate.atStartOfDay(), ZoneId.of(timeZone));
            tomorrowMidnight = ZonedDateTime.of(localDate.plusDays(1l).atStartOfDay(), ZoneId.of(timeZone));
        }

        List<Nap> napList = napRepository.findByBabyProfileIdAndStartBetweenOrBabyProfileIdAndEndBetween(
            babyProfileId,
            todayMidnight,
            tomorrowMidnight,
            babyProfileId,
            todayMidnight,
            tomorrowMidnight
        );

        return sumTotalNapsInHoursByNapList(napList, todayMidnight, tomorrowMidnight);
    }

    private Double sumTotalNapsInHoursByNapList(List<Nap> napList, ZonedDateTime todayMidnight, ZonedDateTime tomorrowMidnight) {
        return (
            napList
                .stream()
                .mapToDouble(nap -> {
                    if (nap.getEnd() == null) {
                        return 0;
                    }
                    if (
                        (nap.getStart().isEqual(todayMidnight) || nap.getStart().isAfter(todayMidnight)) &&
                        nap.getStart().isBefore(tomorrowMidnight) &&
                        nap.getEnd().isAfter(todayMidnight) &&
                        (nap.getEnd().isEqual(tomorrowMidnight) || nap.getEnd().isBefore(tomorrowMidnight))
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
