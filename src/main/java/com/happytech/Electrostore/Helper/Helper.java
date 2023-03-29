package com.happytech.Electrostore.Helper;

import com.happytech.Electrostore.payloads.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {
    public static<U,V> PageableResponse<V> getPageableResponse(Page<U> page,Class<V>type){
        List<U> entity = page.getContent();
        List<V>dtotoList=entity.stream().map(object->new ModelMapper().map(object,type)).collect(Collectors.toList());
        PageableResponse<V>response=new PageableResponse<>();
        response.setContent(dtotoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setLastPages(page.isLast());

        return response;

    }
}
