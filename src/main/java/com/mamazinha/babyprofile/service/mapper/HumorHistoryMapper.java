package com.mamazinha.babyprofile.service.mapper;

import com.mamazinha.babyprofile.domain.*;
import com.mamazinha.babyprofile.service.dto.HumorHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HumorHistory} and its DTO {@link HumorHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = { BabyProfileMapper.class })
public interface HumorHistoryMapper extends EntityMapper<HumorHistoryDTO, HumorHistory> {
    @Mapping(target = "babyProfile", source = "babyProfile", qualifiedByName = "name")
    HumorHistoryDTO toDto(HumorHistory s);
}
