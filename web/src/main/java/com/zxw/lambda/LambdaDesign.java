package com.zxw.lambda;


import java.util.Stack;

/**
 * @author zxw
 * @date 2021-05-25 23:06
 */
public class LambdaDesign {

}

class StackSpec {
    {
        describe("a stack", it -> {
            it.should("be empty when created", expect -> {
//                expect.that(new Stack()).isEmpty();
            });
            it.should("push new elements onto the top of the stack", expect -> {
                Stack<Integer> stack = new Stack<>();
                stack.push(1);
//                expect.that(stack.get(0)).isEqualTo(1);
            });
            it.should("pop the last element pushed onto the stack", expect -> {
                Stack<Integer> stack = new Stack<>();
                stack.push(2);
                stack.push(1);
//                expect.that(stack.pop()).isEqualTo(2);
            });
        });
    }

    public static void describe(String name, Suite behavior) {
        Description description = new Description(name);
        behavior.specifySuite(description);
    }
}
