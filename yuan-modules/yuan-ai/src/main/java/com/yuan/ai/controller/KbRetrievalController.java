package com.yuan.ai.controller;

import com.yuan.ai.domain.dto.KbRetrievalRequest;
import com.yuan.ai.domain.dto.KbRetrievalResponse;
import com.yuan.ai.service.KbRetrievalService;
import com.yuan.common.core.domain.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai/kbRetrieval")
@Tag(name = "KbRetrievalController", description = "Knowledge base retrieval")
public class KbRetrievalController {

    private final KbRetrievalService retrievalService;

    @PostMapping("/search")
    @Operation(summary = "Search knowledge base", operationId = "KbRetrieval_search")
    public R<KbRetrievalResponse> search(@Valid @RequestBody KbRetrievalRequest request) {
        return R.ok(retrievalService.retrieve(request));
    }
}
