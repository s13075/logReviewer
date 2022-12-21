package com.pjatkInz.logReviewer.service;

import com.pjatkInz.logReviewer.dto.PermissionsChangeDto;
import com.pjatkInz.logReviewer.dto.PermissionsRequestDto;
import com.pjatkInz.logReviewer.dto.mapper.PermissionsChangeMapper;
import com.pjatkInz.logReviewer.dto.mapper.PermissionsRequestMapper;
import com.pjatkInz.logReviewer.model.PermissionsChange;
import com.pjatkInz.logReviewer.model.PermissionsRequest;
import com.pjatkInz.logReviewer.repository.PermissionsChangeRepository;
import com.pjatkInz.logReviewer.repository.PermissionsRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PermissionsRequestService {

    private final PermissionsRequestRepository permissionsRequestRepository;
    private final PermissionsRequestMapper permissionsRequestMapper;

    public PermissionsRequestService(PermissionsRequestRepository permissionsRequestRepository, PermissionsRequestMapper permissionsRequestMapper) {
        this.permissionsRequestRepository = permissionsRequestRepository;
        this.permissionsRequestMapper = permissionsRequestMapper;
    }


    public List<PermissionsRequestDto> getPermissionsRequests(){
        Iterable<PermissionsRequest> permissionsRequests = permissionsRequestRepository.findAll();
        return StreamSupport.stream(permissionsRequests.spliterator(),false)
                .map(convertPermissionsRequestToPermissionsRequestDto())
                .collect(Collectors.toList());

    }

    private Function<PermissionsRequest, PermissionsRequestDto> convertPermissionsRequestToPermissionsRequestDto() {
        return permissionsRequest -> permissionsRequestMapper.PermissionsChangeToPermissionsChangeDto(permissionsRequest);
    }

}
