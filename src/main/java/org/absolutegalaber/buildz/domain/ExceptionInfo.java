package org.absolutegalaber.buildz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Data
@NoArgsConstructor
public class ExceptionInfo {
    String errorMessage;

    public ExceptionInfo(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
