package com.pjatkInz.logReviewer.service;

import com.pjatkInz.logReviewer.dto.PermissionsChangeDto;
import com.pjatkInz.logReviewer.dto.mapper.PermissionsChangeMapper;
import com.pjatkInz.logReviewer.model.PermissionsChange;
import com.pjatkInz.logReviewer.repository.PermissionsChangeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PermissionsChangeService {

    private final PermissionsChangeRepository permissionsChangeRepository;
    private final PermissionsChangeMapper permissionsChangeMapper;

    public PermissionsChangeService(PermissionsChangeRepository permissionsChangeRepository, PermissionsChangeMapper permissionsChangeMapper) {
        this.permissionsChangeRepository = permissionsChangeRepository;
        this.permissionsChangeMapper = permissionsChangeMapper;
    }


    public List<PermissionsChangeDto> getPermissionsChanges(){
        Iterable<PermissionsChange> permissionsChanges = permissionsChangeRepository.findAll();
        return StreamSupport.stream(permissionsChanges.spliterator(),false)
                .map(convertPermissionsChangeToPermissionsChangeDto())
                .collect(Collectors.toList());

    }

    public List<PermissionsChangeDto> getApplicationChanges(String id) {
        Iterable<PermissionsChange> permissionsChanges = permissionsChangeRepository.findPermissionsChangeByApplicationId(UUID.fromString(id));
        return StreamSupport.stream(permissionsChanges.spliterator(),false)
                .map(convertPermissionsChangeToPermissionsChangeDto())
                .collect(Collectors.toList());
    }

    public Function<PermissionsChange, PermissionsChangeDto> convertPermissionsChangeToPermissionsChangeDto() {
        return permissionsChange -> permissionsChangeMapper.permissionsChangeToPermissionsChangeDto(permissionsChange);
    }

    public UUID PermissionsChange(PermissionsChangeDto permissionsChangeDto){

        PermissionsChange addedPermissionsChange = permissionsChangeMapper.permissionsChangeDtoToPermissionsChange(permissionsChangeDto);
        addedPermissionsChange.setId(null);
        return null;

    }

}
