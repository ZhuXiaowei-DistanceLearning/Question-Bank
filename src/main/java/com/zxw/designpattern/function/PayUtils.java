package com.zxw.designpattern.function;

import com.google.common.collect.Maps;
import groovy.lang.Tuple2;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.Tuple;
import io.vavr.control.Option;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class PayUtils {

    public static final Map<PayType, Function1<Map<String, Object>, Tuple2<Object, Map<String, Object>>>>
            PAYMENT_RULES = Maps.newHashMap();

    private static final Function2<String, String, PayType> PAY_TYPE_FUNC = (scope, payType) -> PayType.valueOf(
            StringUtils.join(Arrays.asList(scope, payType), PaymentConstants.SPLIT));

    public static final Function3<String, String, Map<String, Object>, Tuple2<Object, Map<String, Object>>> PAY_FUNC =
            (scope, payType, params) -> {

                PayType paymentMethod;
                try {
                    paymentMethod = PAY_TYPE_FUNC.apply(scope, payType);
                    return PAYMENT_RULES.get(paymentMethod).apply(params);
                } catch (Exception e) {
                    throw new RuntimeException("Payment method could not be supported !!!");
                }
            };

    static {
        PAYMENT_RULES.put(PayType.DOMESTIC_WECHAT, PayFunc.DOMESTIC_WECHAT_FUNC);
        PAYMENT_RULES.put(PayType.OVERSEAS_EPAY, PayFunc.OVERSEAS_EPAY_FUNC);
    }

    /**
     * test
     *
     * @param args
     */
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, new Date().getMonth() - 1);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));//获取月份的最后一天
        System.out.println(calendar.getTime());
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list = new ArrayList<>();
        list.removeAll(list);
        Arrays.asList(1, 2, 3, 4, 5).stream().forEach(e -> {
            if (e == 3) {
                return;
            }
            System.out.println(e);
        });
        io.vavr.Tuple2<Integer, Integer> of = Tuple.of(1, 2);

        Function2<Integer, Integer, Integer> function2 = (v1, v2) -> v1 + v2;
        Integer apply = function2.apply(1, 2);
        String s = "asd\n123";
        System.out.println(s);
//        ExecutorService executorService = Executors.newFixedThreadPool(3);
//        for (int i = 0; i < 10; i++) {
//            executorService.execute(() -> {
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(Thread.currentThread().getName());
//                System.out.println(Thread.currentThread().getName());
//            });
//        }
        String scope = "DOMESTIC";
        String payType = "WECHAT";

        Map<String, Object> params = Maps.newHashMap();
        Tuple2<Object, Map<String, Object>> rt = PAY_FUNC.apply(scope, payType, params);
        System.out.println(rt.getFirst() + "--->" + rt.getSecond());

        scope = "OVERSEAS";
        payType = "EPAY";
        rt = PAY_FUNC.apply(scope, payType, params);
        System.out.println(rt.getFirst() + "--->" + rt.getSecond());
//        ThreadPoolExecutor tpe = ((ThreadPoolExecutor) executorService);
//        scope = "DOMESTIC";
//        payType = "ALIPAY";
//        rt = PAY_FUNC.apply(scope, payType, params);
//        System.out.println(rt.getFirst() + "--->" + rt.getSecond());
    }

    enum PayType {
        DOMESTIC_WECHAT, DOMESTIC_ALIPAY, DOMESTIC_UNIPAY_, OVERSEAS_GOOGLE, OVERSEAS_EPAY, OVERSEAS_FACEBOOK

    }

    enum PayStatus {
        SUCCESS, FAILURE
    }

    public static class PaymentConstants {
        public static final String SPLIT = "_";

    }

    public static class PayFunc {
        public static final Function1<Map<String, Object>, Tuple2<Object, Map<String, Object>>> OVERSEAS_EPAY_FUNC =
                params -> {
                    params.put("OVERSEAS_EPAY_FUNC", "AAAA");

                    return new groovy.lang.Tuple2<>(PayStatus.FAILURE, params);
                };
        public static final Function1<Map<String, Object>, Tuple2<Object, Map<String, Object>>> DOMESTIC_WECHAT_FUNC =
                params -> {
                    params.put("DOMESTIC_WECHAT_FUNC", "BBBB");

                    return new Tuple2<>(PayStatus.SUCCESS, params);
                };
    }

}