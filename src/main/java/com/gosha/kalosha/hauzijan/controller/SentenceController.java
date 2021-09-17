package com.gosha.kalosha.hauzijan.controller;

import com.gosha.kalosha.hauzijan.dto.SentenceDto;
import com.gosha.kalosha.hauzijan.service.SentenceService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.gosha.kalosha.hauzijan.util.Util.decodeJsonToObject;

@RestController
@RequestMapping("sentence")
public class SentenceController
{
    private final SentenceService sentenceService;

    @Autowired
    public SentenceController(SentenceService sentenceService)
    {
        this.sentenceService = sentenceService;
    }

    /**
     * @param query query string
     * @param page page's numeration starts from 1, default value 1
     * @param maxResults maximum number of results per page, default value 10
     * @return list of sentences if any is found
     */
    @GetMapping("simple")
    public List<SentenceDto> makeSimpleQuery(@RequestParam String query,
                                             @RequestParam(required = false) Integer page,
                                             @RequestParam(required = false, name = "max_results") Integer maxResults)
    {
        return sentenceService.getBySimpleQuery(query, page, maxResults);
    }

    /**
     * @param encoded base64 encoded query map
     * @param page page's numeration starts from 1, default value 1
     * @param maxResults maximum number of results per page, default value 10
     * @return list of sentences if any is found
     */
    @SneakyThrows
    @GetMapping("complex")
    public List<SentenceDto> makeComplexQuery(@RequestParam String encoded,
                                              @RequestParam(required = false) Integer page,
                                              @RequestParam(required = false, name = "max_results") Integer maxResults)
    {
        Map<String, Object> query = (Map<String, Object>) decodeJsonToObject(encoded, Map.class);
        return sentenceService.getByParameters(query, page, maxResults);
    }
}
