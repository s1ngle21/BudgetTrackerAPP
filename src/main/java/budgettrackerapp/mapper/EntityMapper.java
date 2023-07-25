package budgettrackerapp.mapper;



import java.util.List;

public interface EntityMapper<ENTITY, ENTITY_DTO> {

    ENTITY_DTO mapToDto(ENTITY entity);

    ENTITY mapToEntity(ENTITY_DTO entityDto);

    List<ENTITY_DTO> mapToDto(List<ENTITY> entities);
}
