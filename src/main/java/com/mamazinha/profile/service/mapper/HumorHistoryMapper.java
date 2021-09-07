package com.mamazinha.profile.service.mapper;

import com.mamazinha.profile.domain.*;
import com.mamazinha.profile.service.dto.HumorHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HumorHistory} and its DTO {@link HumorHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProfileMapper.class })
public interface HumorHistoryMapper extends EntityMapper<HumorHistoryDTO, HumorHistory> {
    @Mapping(target = "profile", source = "profile", qualifiedByName = "name")
    HumorHistoryDTO toDto(HumorHistory s);
}
