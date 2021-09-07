package com.mamazinha.profile.service.mapper;

import com.mamazinha.profile.domain.*;
import com.mamazinha.profile.service.dto.ProfileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Profile} and its DTO {@link ProfileDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    ProfileDTO toDto(Profile s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProfileDTO toDtoName(Profile profile);
}
