package ca.bc.gov.educ.api.pen.services.mapper.v1;

import ca.bc.gov.educ.api.pen.services.mapper.UUIDMapper;
import ca.bc.gov.educ.api.pen.services.model.StudentMergeEntity;
import ca.bc.gov.educ.api.pen.services.model.StudentMergeSourceCodeEntity;
import ca.bc.gov.educ.api.pen.services.struct.v1.StudentMerge;
import ca.bc.gov.educ.api.pen.services.struct.v1.StudentMergeSourceCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UUIDMapper.class})
@SuppressWarnings("squid:S1214")
public interface StudentMergeMapper {

  StudentMergeMapper mapper = Mappers.getMapper(StudentMergeMapper.class);

  StudentMergeEntity toModel(StudentMerge studentMerge);

  StudentMerge toStructure(StudentMergeEntity studentMergeEntity);

  @Mapping(target = "updateUser", ignore = true)
  @Mapping(target = "updateDate", ignore = true)
  @Mapping(target = "createUser", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  StudentMergeSourceCodeEntity toModel(StudentMergeSourceCode structure);

  StudentMergeSourceCode toStructure(StudentMergeSourceCodeEntity entity);
}