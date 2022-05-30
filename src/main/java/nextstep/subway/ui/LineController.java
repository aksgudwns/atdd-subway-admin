package nextstep.subway.ui;

import nextstep.subway.NotFoundException;
import nextstep.subway.application.LineService;
import nextstep.subway.dto.LineRequest;
import nextstep.subway.dto.LineResponse;
import nextstep.subway.dto.LineUpdateRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lines")
public class LineController {
    private final LineService lineService;

    public LineController(LineService lineService) {
        this.lineService = lineService;
    }

    @DeleteMapping("/{id}")
    public void deleteLine(@PathVariable Long id) {
        lineService.deleteLine(id);
    }

    @PutMapping("/{id}")
    public void updateLine(@PathVariable Long id, @RequestBody LineUpdateRequest lineUpdateRequest) {
        lineService.updateLine(id, lineUpdateRequest);
    }

    @PostMapping
    public ResponseEntity<LineResponse> createLine(@RequestBody LineRequest lineRequest) {
        LineResponse response = lineService.saveLine(lineRequest);
        return ResponseEntity.created(URI.create("/lines/" + response.getId())).body(response);
    }

    @GetMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public LineResponse showLine(@PathVariable Long id) {
        return lineService.findLine(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LineResponse> showAllLine() {
        return lineService.findAllLines();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Void> handleIllegalArgsException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Void> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }
}
