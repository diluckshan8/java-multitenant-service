package com.spring.multitenant.dto;

import static com.spring.multitenant.common.CommonConstants.DEFAULT_ERROR_CODE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseOutputDto extends BaseDto {

    private Integer status;

    public void setStatus(String strStatus) {
        try {
            this.status = Integer.parseInt(strStatus);
        } catch (NumberFormatException e) {
            this.status = DEFAULT_ERROR_CODE;
        }
    }

    public void setStatus(int status) {
        this.status = status;
    }


}