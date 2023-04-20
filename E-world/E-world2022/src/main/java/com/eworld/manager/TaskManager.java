package com.eworld.manager;

import com.eworld.contstant.OrderStatus;
import com.eworld.dto.order.OrderDto;
import com.eworld.excel.order.PrintListOrder;
import com.eworld.service.OrderService;
import com.eworld.service.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class TaskManager {

    @Autowired
    private TwilioService twilioService;
    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 24 13 * * ?")
    public void sendEmailForCustomer(){
        twilioService.sendNotify();
    }
    public void ExcuteTasks(){
        Thread thread = new Thread(this::sendEmailForCustomer);
        thread.start();
    }

    @Scheduled(cron = "0 55 23 * * ?")
    public void exportToExcel()  {
      try {
          List<OrderDto> orderDtoList = orderService.findAllOrder(OrderStatus.SUCCESSFULLY);
          PrintListOrder excelListStaff = new PrintListOrder(orderDtoList);
          excelListStaff.export();
      }
      catch (IOException e){
          e.printStackTrace();
      }
    }
}
