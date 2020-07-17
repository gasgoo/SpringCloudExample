package com.server.web;

import com.server.service.GenRedPackService;
import com.server.service.TicketService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 2020/7/14 11:45
 * @name LockController
 */

@RestController
@Slf4j
public class LockController {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private GenRedPackService genRedPackService;


    @ApiOperation(value = "售票")
    @RequestMapping(value = "/sale", method = RequestMethod.GET)
    public int getTicket() {
        int count = ticketService.sale();
        log.info("====剩余ticket:{}", count);
        return count;
    }

    @RequestMapping(value = "/red", method = RequestMethod.GET)
    public String redpack() {
        log.info("抢红包了....");
        genRedPackService.redPack();
        return "redPack";
    }
}
