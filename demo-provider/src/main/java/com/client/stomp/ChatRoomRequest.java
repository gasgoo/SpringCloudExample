package com.client.stomp;

import lombok.Getter;
import lombok.Setter;

/**
 * 聊天室消息发送对象
 *
 * @Date 2020/7/2 10:42
 * @name ChatRoomRequest
 */

@Setter
@Getter
public class ChatRoomRequest {

    /*发送者名字*/
    private String name;
    /*聊天内容*/
    private String chatValue;
    /*单聊里接收者的ID*/
    private String userId;


}
