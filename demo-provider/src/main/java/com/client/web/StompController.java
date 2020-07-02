package com.client.web;

import com.client.stomp.ChatRoomRequest;
import com.client.stomp.ChatRoomResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 在线聊天室
 *
 * @Date 2020/7/2 10:52
 * @name StompController
 */

@Controller
@Slf4j
public class StompController {

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 消息群发，接受发送至自massRequest的请求
     * //SendTo 发送至 Broker 下的指定订阅路径mass ,
     * // Broker再根据getResponse发送消息到订阅了/mass/getResponse的用户处
     *
     * @Date 2020/7/2 10:58
     **/
    @MessageMapping("/massRequest")
    @SendTo("/mass/getResponse")
    public ChatRoomResponse mass(ChatRoomRequest request) {
        log.info("name = " + request.getName());
        log.info("chatValue = " + request.getChatValue());
        ChatRoomResponse response = new ChatRoomResponse();
        response.setName(request.getName());
        response.setCharValue(request.getChatValue());
        return response;
    }

    /**
     * @Description 单独聊天，接受发送至自aloneRequest的请求
     * @Date 2020/7/2 11:01
     **/
    @MessageMapping("/aloneRequest")
    public ChatRoomResponse alone(ChatRoomRequest chatRoomRequest) {
        log.info("SendToUser = " + chatRoomRequest.getUserId() + " FromName = " + chatRoomRequest.getName() + " ChatValue = " + chatRoomRequest.getChatValue());
        ChatRoomResponse response = new ChatRoomResponse();
        response.setName(chatRoomRequest.getName());
        response.setCharValue(chatRoomRequest.getChatValue());
        //会发送到订阅了 /user/{用户的id}/alone 的用户处
        this.template.convertAndSendToUser(chatRoomRequest.getUserId() + "", "/alone", response);
        return response;
    }


}
