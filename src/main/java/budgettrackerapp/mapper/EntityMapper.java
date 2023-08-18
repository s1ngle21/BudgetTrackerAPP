package budgettrackerapp.mapper;



import java.util.List;

public interface EntityMapper<ENTITY, DTO> {

    DTO mapToDto(ENTITY entity);

    ENTITY mapToEntity(DTO entityDto);

    List<DTO> mapToDto(List<ENTITY> entities);
}
