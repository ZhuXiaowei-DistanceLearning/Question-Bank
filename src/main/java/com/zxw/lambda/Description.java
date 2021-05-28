package com.zxw.lambda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.runner.Runner;

/**
 * @author zxw
 * @date 2021-05-25 23:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Description {
    private String name;

    public void should(String description, Specification specification) {
        try {
            Expect expect = new Expect();
            specification.specifyBehaviour(expect);
//            Runner.current.recordSuccess(suite, description);
        } catch (AssertionError cause) {
//            Runner.current.recordFailure(suite, description, cause);
        } catch (Throwable cause) {
//            Runner.current.recordError(suite, description, cause);
        }
    }
}
