package org.absolutegalaber.buildz.api.v1;

import org.absolutegalaber.buildz.domain.BuildCount;
import org.absolutegalaber.buildz.service.BuildCountService;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@RestController
@RequestMapping(value = "v1/buildNumbers")
public class BuildNumberResourceV1 {
    private final BuildCountService buildCountService;

    public BuildNumberResourceV1(BuildCountService buildCountService) {
        this.buildCountService = buildCountService;
    }

    @RequestMapping(value = "/next", method = RequestMethod.POST)
    public BuildCount next(@RequestBody BuildCount buildCount) {
        return buildCountService.next(buildCount.getProject(), buildCount.getBranch());
    }

    @RequestMapping(value = "/current/{project}/{branch}", method = RequestMethod.GET)
    public BuildCount current(@PathVariable("project") String project, @PathVariable("branch") String branch) {
        return buildCountService.current(project, branch);
    }

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public void set(@RequestBody BuildCount buildCount) {
        buildCountService.set(buildCount.getProject(), buildCount.getBranch(), buildCount.getCount());
    }
}
