package com.pjatkInz.logReviewer.dto.mapper;

import com.pjatkInz.logReviewer.dto.ReconciliationDto;
import com.pjatkInz.logReviewer.model.Reconciliation;
import org.mapstruct.Mapper;

@Mapper(uses = {PermissionsChangeMapper.class, PermissionsRequestMapper.class, JustificationMapper.class })
public interface ReconciliationMapper {

    Reconciliation reconciliationDtoToReconciliation(ReconciliationDto reconciliationDto);
    ReconciliationDto reconciliationToReconciliationDto(Reconciliation reconciliation);
}
