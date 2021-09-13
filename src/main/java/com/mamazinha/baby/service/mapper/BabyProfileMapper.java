package com.mamazinha.baby.service.mapper;

import com.mamazinha.baby.domain.*;
import com.mamazinha.baby.service.dto.BabyProfileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BabyProfile} and its DTO {@link BabyProfileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BabyProfileMapper extends EntityMapper<BabyProfileDTO, BabyProfile> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    BabyProfileDTO toDtoName(BabyProfile babyProfile);
}
