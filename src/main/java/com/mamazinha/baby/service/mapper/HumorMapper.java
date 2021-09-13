package com.mamazinha.baby.service.mapper;

import com.mamazinha.baby.domain.*;
import com.mamazinha.baby.service.dto.HumorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Humor} and its DTO {@link HumorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HumorMapper extends EntityMapper<HumorDTO, Humor> {
    @Named("description")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", source = "description")
    HumorDTO toDtoDescription(Humor humor);
}
