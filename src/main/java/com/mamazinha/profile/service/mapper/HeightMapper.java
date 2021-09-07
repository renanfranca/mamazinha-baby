package com.mamazinha.profile.service.mapper;

import com.mamazinha.profile.domain.*;
import com.mamazinha.profile.service.dto.HeightDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Height} and its DTO {@link HeightDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProfileMapper.class })
public interface HeightMapper extends EntityMapper<HeightDTO, Height> {
    @Mapping(target = "profile", source = "profile", qualifiedByName = "name")
    HeightDTO toDto(Height s);
}
