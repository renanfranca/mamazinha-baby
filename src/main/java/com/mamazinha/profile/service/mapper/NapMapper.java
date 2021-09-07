package com.mamazinha.profile.service.mapper;

import com.mamazinha.profile.domain.*;
import com.mamazinha.profile.service.dto.NapDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nap} and its DTO {@link NapDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProfileMapper.class })
public interface NapMapper extends EntityMapper<NapDTO, Nap> {
    @Mapping(target = "profile", source = "profile", qualifiedByName = "name")
    NapDTO toDto(Nap s);
}
