package org.absolutegalaber.buildz.domain;

import com.google.common.base.Strings;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Data
public class BuildSearch {
    private String project;
    private String branch;
    private Long minBuildNumber;
    private Long maxBuildNumber;
    private Map<String, String> labels = new HashMap<>();
    private Integer pageSize;
    private Integer page;
    private String sortAttribute;
    private String sortDirection;

    public PageRequest page() {
        int thePage = getPage() != null ? getPage() : 0;
        int thePageSize = getPageSize() != null ? getPageSize() : 10;
        Sort.Direction theDirection = Strings.isNullOrEmpty(getSortDirection()) ? Sort.Direction.DESC : Sort.Direction.fromString(getSortDirection());
        String theSortAttribute = Strings.isNullOrEmpty(getSortAttribute()) ? "buildNumber" : getSortAttribute();
        return new PageRequest(thePage, thePageSize, new Sort(theDirection, theSortAttribute));
    }
}
