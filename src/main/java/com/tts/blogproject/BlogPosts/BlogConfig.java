package com.tts.blogproject.BlogPosts;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.*;
    @Configuration
public class BlogConfig {

    @Bean
        public ArrayList<BlogPost> posts() {

        return new ArrayList<BlogPost>();
    }

}
