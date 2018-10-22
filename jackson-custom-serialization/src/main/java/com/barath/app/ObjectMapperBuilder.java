package com.barath.app;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public interface ObjectMapperBuilder {



    static Builder with(SerializationFeature feature){
        return new DefaultObjectMapperBuilder().with(feature);
    }

    static Builder with(DeserializationFeature feature){
        return new DefaultObjectMapperBuilder().with(feature);
    }

    static ObjectMapper build(){
        return new DefaultObjectMapperBuilder().build();
    }



    interface Builder {
        Builder  with(SerializationFeature feature);
        Builder with(DeserializationFeature feature);
        ObjectMapper build();
    }



    public static class DefaultObjectMapperBuilder implements Builder{

        private static final ObjectMapper mapper = new ObjectMapper();

        public Builder  with(SerializationFeature feature){
            mapper.configure(feature,true);
            return  this;
        }

        public Builder with(DeserializationFeature feature){
            mapper.configure(feature , true);
            return this;
        }

        public ObjectMapper build(){
            return mapper;
        }

    }
}
