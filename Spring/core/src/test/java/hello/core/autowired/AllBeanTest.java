package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.dicount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class,DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        int fixDiscountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");
        int rateDiscountPrice = discountService.discount(member, 10000, "rateDiscountPolicy");
        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(fixDiscountPrice).isEqualTo(1000);
        assertThat(rateDiscountPrice).isEqualTo(2000);


    }

    static class DiscountService {

        private final Map<String, DiscountPolicy> policNMap;
        private final List<DiscountPolicy> policies;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policNMap, List<DiscountPolicy> policies) {
            this.policNMap = policNMap;
            this.policies = policies;
            System.out.println("policNMap = " + policNMap);
            System.out.println("policies = " + policies);
        }


        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policNMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }
    }

}
