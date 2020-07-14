package com.server.domain;

import com.server.annotation.JsonField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author
 * @Date 2019/4/4 15:33
 */

@Getter
@Setter
@ToString
public class LockDTO {

    @JsonField
    private String name;
    @JsonField("skill")
    private String value;
    private String professional;

    private String company;

    public LockDTO(String name, String value) {
        this.name = name;
        this.value = value;
    }


}
