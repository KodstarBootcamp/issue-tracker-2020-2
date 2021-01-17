package com.kodstar.issuetracker.utils.impl;

import com.kodstar.issuetracker.dto.IssueHistoryDTO;
import com.kodstar.issuetracker.entity.IssueHistory;
import com.kodstar.issuetracker.utils.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FromIssueHistoryToIssueHistoryDTO implements Converter<IssueHistory, IssueHistoryDTO> {
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public IssueHistoryDTO convert(IssueHistory issueHistory) {
        IssueHistoryDTO issueHistoryDTO=modelMapper.map(issueHistory,IssueHistoryDTO.class);
        return issueHistoryDTO;
    }
}
