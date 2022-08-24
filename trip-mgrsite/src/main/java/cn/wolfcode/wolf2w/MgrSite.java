package cn.wolfcode.wolf2w;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // 开启定时任务
public class MgrSite {



//    @Bean
////    public MybatisPlusInterceptor mybatisPlusInterceptor() {
////
////        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
////
////        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
////
////        paginationInnerInterceptor.setOverflow(true); //合理化
////
////        interceptor.addInnerInterceptor(paginationInnerInterceptor);
////
////        return interceptor;
////
////    }


    public static void main(String[] args) {
        SpringApplication.run(MgrSite.class,args);
    }
}
