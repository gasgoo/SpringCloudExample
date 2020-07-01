package com.netty;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * 自定义序列号
 * @Date 2020/6/30 11:18
 * @name UserInfoSeriializable
 */

@Setter
@Getter
public class UserInfoSeriializable  implements Serializable {

    private Integer userId;

    private String name;

    private String address;

    /**
     * @Description  序列化
     * @Date 2020/6/30 11:20
     **/
    public byte[] codeC(){
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        byte[] nameV=this.name.getBytes();
        byte[] addressV=this.address.getBytes();
        buffer.putInt(this.userId.intValue());
        buffer.putInt(nameV.length);
        buffer.put(nameV);
        buffer.putInt(addressV.length);
        buffer.put(addressV);
        buffer.flip();
        byte[] value=new byte[buffer.remaining()];
        buffer.get(value);
        return value;
    }

    public static void main(String[] args) {
        UserInfoSeriializable user=new UserInfoSeriializable();
        user.setUserId(100);
        user.setName("张无忌");
        user.setAddress("昆仑山");
        byte[] bytes = user.codeC();
        System.out.println(new String(bytes));
    }
}
