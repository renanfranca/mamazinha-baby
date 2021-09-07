package com.mamazinha.profile.service.mapper;

import com.mamazinha.profile.domain.*;
import com.mamazinha.profile.service.dto.WeightDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Weight} and its DTO {@link WeightDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProfileMapper.class })
public interface WeightMapper extends EntityMapper<WeightDTO, Weight> {
    @Mapping(target = "profile", source = "profile", qualifiedByName = "name")
    WeightDTO toDto(Weight s);
}
