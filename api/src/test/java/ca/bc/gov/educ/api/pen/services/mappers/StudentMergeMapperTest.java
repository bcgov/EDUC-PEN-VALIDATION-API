package ca.bc.gov.educ.api.pen.services.mappers;

import ca.bc.gov.educ.api.pen.services.mapper.v1.StudentMergeMapper;
import ca.bc.gov.educ.api.pen.services.struct.v1.StudentMergeSourceCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class StudentMergeMapperTest {
  @Test
  public void testToModel_WhenStudentMergeSourceCode_ShouldReturnStudentMergeSourceCodeEntity() {
    var struct = StudentMergeSourceCode.builder().mergeSourceCode("MINISTRY").label("Ministry Identified").effectiveDate("2020-01-01T01:01:01").expiryDate("2090-01-01T01:01:01").build();
    var entity = StudentMergeMapper.mapper.toModel(struct);
    assertEquals(struct.getMergeSourceCode(), entity.getMergeSourceCode());
    assertNull(entity.getCreateUser());
    assertNull(entity.getCreateDate());
    assertNull(entity.getUpdateUser());
    assertNull(entity.getUpdateDate());
  }
}
