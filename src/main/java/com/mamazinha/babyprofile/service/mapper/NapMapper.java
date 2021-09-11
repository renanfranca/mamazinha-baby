package com.mamazinha.babyprofile.service.mapper;

import com.mamazinha.babyprofile.domain.*;
import com.mamazinha.babyprofile.service.dto.NapDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nap} and its DTO {@link NapDTO}.
 */
@Mapper(componentModel = "spring", uses = { BabyProfileMapper.class })
public interface NapMapper extends EntityMapper<NapDTO, Nap> {
    @Mapping(target = "babyProfile", source = "babyProfile", qualifiedByName = "name")
    NapDTO toDto(Nap s);
}
