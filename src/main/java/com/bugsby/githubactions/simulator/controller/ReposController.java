package com.bugsby.githubactions.simulator.controller;

import com.bugsby.githubactions.simulator.swagger.model.ActionsListWorkflowRunsForRepo200Response;
import com.bugsby.githubactions.simulator.swagger.model.FullRepository;
import com.bugsby.githubactions.simulator.swagger.model.WorkflowRun;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.OffsetDateTime;
import java.util.stream.IntStream;

@RestController
@CrossOrigin
public class ReposController implements com.bugsby.githubactions.simulator.swagger.api.ReposApi {
    @Value("${number.workflow.runs}")
    private int numberWorkflowRuns;
    @Value("${simulator.url}")
    private String simulatorUrl;

    @Override
    public ResponseEntity<Void> actionsDownloadWorkflowRunLogs(String owner, String repo, Long runId) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, UriComponentsBuilder.fromHttpUrl(simulatorUrl)
                        .path("logs")
                        .build(false)
                        .toString())
                .build();
    }

    @Override
    public ResponseEntity<ActionsListWorkflowRunsForRepo200Response> actionsListWorkflowRunsForRepo(String owner, String repo, String actor, String branch, String event, String status, Integer perPage, Integer page, OffsetDateTime created, Boolean excludePullRequests, Integer checkSuiteId, String headSha) {
        ActionsListWorkflowRunsForRepo200Response response = new ActionsListWorkflowRunsForRepo200Response()
                .totalCount(numberWorkflowRuns)
                .workflowRuns(IntStream.range(0, numberWorkflowRuns)
                        .mapToObj(i -> new WorkflowRun()
                                .id(Integer.toUnsignedLong(i))
                                .workflowId(i)
                                .name("name")
                                .displayTitle("displayTitle")
                                .status("failure")
                                .conclusion("failure")
                                .htmlUrl("htmlUrl"))
                        .toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<FullRepository> reposGet(String owner, String repo) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
