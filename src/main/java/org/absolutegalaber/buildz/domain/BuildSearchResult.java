package org.absolutegalaber.buildz.domain;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Data
public class BuildSearchResult {
    private List<Build> builds = new ArrayList<>();
    private Integer page;
    private Long totalElements;
    private Integer totalPages;
    private Boolean hasNext;
    private Boolean hasPrevious;

    public static BuildSearchResult emptyResult() {
        BuildSearchResult toReturn = new BuildSearchResult();
        toReturn.setPage(0);
        toReturn.setTotalPages(0);
        toReturn.setTotalElements(0L);
        toReturn.setHasNext(false);
        toReturn.setHasPrevious(false);
        return toReturn;
    }

    public static BuildSearchResult fromPageResult(Page<Build> result) {
        BuildSearchResult toReturn = new BuildSearchResult();
        toReturn.getBuilds().addAll(result.getContent());
        toReturn.setPage(result.getNumber());
        toReturn.setTotalPages(result.getTotalPages());
        toReturn.setTotalElements(result.getTotalElements());
        toReturn.setHasNext(result.hasNext());
        toReturn.setHasPrevious(result.hasPrevious());
        return toReturn;
    }
}
