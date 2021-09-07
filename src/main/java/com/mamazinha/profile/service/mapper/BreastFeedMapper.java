package com.mamazinha.profile.service.mapper;

import com.mamazinha.profile.domain.*;
import com.mamazinha.profile.service.dto.BreastFeedDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BreastFeed} and its DTO {@link BreastFeedDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProfileMapper.class })
public interface BreastFeedMapper extends EntityMapper<BreastFeedDTO, BreastFeed> {
    @Mapping(target = "profile", source = "profile", qualifiedByName = "name")
    BreastFeedDTO toDto(BreastFeed s);
}
