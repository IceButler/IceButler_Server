package com.example.icebutler_server.cart.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostFoodCartRequest {


    @ApiParam(value="음식",example = "0")
    @ApiModelProperty(example = "음식1")
    private int foodIdx;

    @ApiParam(value="아이디",example = "0")
    @ApiModelProperty(example = "안녕")
    private long userIdx;

}
